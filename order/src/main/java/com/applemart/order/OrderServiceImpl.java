package com.applemart.order;

import com.applemart.order.clients.address.AddressDTO;
import com.applemart.order.clients.address.AddressService;
import com.applemart.order.clients.cart.CartItem;
import com.applemart.order.clients.cart.CartService;
import com.applemart.order.clients.product.ProductItemDTO;
import com.applemart.order.clients.product.ProductItemService;
import com.applemart.order.common.PageResponse;
import com.applemart.order.exception.RequestValidationException;
import com.applemart.order.exception.ResourceNotFoundException;
import com.applemart.order.orderLine.OrderLine;
import com.applemart.order.payment.*;
import com.applemart.order.shipping.ShippingMethod;
import com.applemart.order.shipping.ShippingMethodRepository;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderDTOMapper orderDTOMapper;
    private final AddressService addressService;
    private final ShippingMethodRepository shippingMethodRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final CartService cartService;
    private final OrderRepository orderRepository;
    private final PaymentClient paymentClient;
    private final ProductItemService productItemService;

    private boolean isAuthorized(String userId) {
        String authenticatedUserId = SecurityContextHolder.getContext().getAuthentication().getName();

        return !userId.equals(authenticatedUserId) && !userId.equals("1");
    }

    @Override
    @Transactional
    public OrderCreationResponse createOrder(OrderCreationRequest orderCreationRequest) {
        Integer userId = orderCreationRequest.getUserId();

        if (isAuthorized(String.valueOf(userId))) {
            throw new AccessDeniedException("You are not authorized to make this order");
        }

        Integer addressId = orderCreationRequest.getAddressId();

        AddressDTO addressDTO = addressService.getAddressById(String.valueOf(addressId));

        if (!addressDTO.getIsDeliveryAddress()) {
            throw new BadRequestException("Address is not delivery address");
        }

        Order newOrder = orderDTOMapper.toEntity(orderCreationRequest);

        Double totalAmount = 0.0;

        List<OrderLine> orderLines = newOrder.getOrderLines();

        List<CartItem> cartItems = cartService.getProductItemsFromCart(String.valueOf(userId));

        List<Integer> productItemIds = orderLines.stream().map(OrderLine::getProductItemId).toList();

        List<ProductItemDTO> productItems =
                cartItems
                        .stream()
                        .map(CartItem::getProductItem)
                        .filter(productItemDTO -> productItemIds.contains(productItemDTO.getId()))
                        .toList();

        if (productItems.isEmpty()) {
            throw new ResourceNotFoundException("Product items not found in cart");
        }

        for (OrderLine orderLine : orderLines) {
            for (ProductItemDTO productItemDTO : productItems) {
                if (productItemDTO.getId().equals(orderLine.getProductItemId())) {
                    if (orderLine.getQuantity() > productItemDTO.getQuantityInStock()) {
                        throw new RequestValidationException("Requested quantity exceeds maximum quantity");
                    }
                    orderLine.setPrice(productItemDTO.getPrice() * orderLine.getQuantity());
                    orderLine.setOrder(newOrder);
                }
            }
        }

        totalAmount = orderLines.stream().map(OrderLine::getPrice).reduce(0.0, Double::sum);

        newOrder.setUserId(userId);
        newOrder.setOrderDate(LocalDateTime.now());

        ShippingMethod shippingMethod = shippingMethodRepository.findByName(orderCreationRequest.getShippingMethod())
                .orElseThrow(() -> new ResourceNotFoundException("Shipping method with name [%s] not found".formatted(orderCreationRequest.getShippingMethod())));

        newOrder.setShippingMethod(shippingMethod);
        totalAmount += shippingMethod.getPrice();

        PaymentMethod paymentMethod = paymentMethodRepository.findByName(orderCreationRequest.getPaymentMethod())
                .orElseThrow(() -> new ResourceNotFoundException("Payment method with name [%s] not found".formatted(orderCreationRequest.getPaymentMethod())));

        newOrder.setPaymentMethod(paymentMethod);

        newOrder.setTotalAmount(totalAmount);

        OrderCreationResponse orderCreationResponse = new OrderCreationResponse();

        Order orderResult = null;

        if (paymentMethod.getName().equals("cod")) {
            newOrder.setPaymentStatus("Đang chờ");
            newOrder.setOrderStatus("Chờ xác nhận");
            orderCreationResponse.setResult("Order created successfully");

            orderResult = orderRepository.save(newOrder);
            cartService.deleteProductsInCart(String.valueOf(userId), productItemIds);
        } else if (paymentMethod.getName().equals("vnpay")) {
            newOrder.setOrderStatus("Chờ xác nhận");
            newOrder.setPaymentStatus("Đang chờ");
            orderCreationResponse.setResult("Order created successfully");

            orderResult = orderRepository.save(newOrder);
            cartService.deleteProductsInCart(String.valueOf(userId), productItemIds);

            Double amount = newOrder.getTotalAmount();
            String bankCode = newOrder.getPaymentMethod().getProvider();
            Integer orderId = orderResult.getId();

            VNPayResponse vnPayResponse = paymentClient.payVNPay(String.format("%.0f", amount), bankCode, String.valueOf(orderId)).getData();
            orderCreationResponse.setVnpayLink(vnPayResponse.paymentUrl);

        } else {
            orderCreationResponse.setResult("No payment method selected");
        }

        orderCreationResponse.setOrder(orderDTOMapper.toDTO(orderResult));

        return orderCreationResponse;
    }

    @Override
    @Transactional
    public PageResponse<OrderDTO> getAllOrders(int page, int size, String sort, String dir) {
        Sort sortBy = dir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sort).ascending()
                : Sort.by(sort).descending();

        Pageable pageable = PageRequest.of(page, size, sortBy);

        Page<Order> pages = orderRepository.findAll(pageable);

        List<OrderDTO> orderDTOs = pages.getContent()
                .stream()
                .map(orderDTOMapper::toDTO)
                .toList();

        return PageResponse.<OrderDTO>builder()
                .currentPage(page)
                .pageSize(pages.getSize())
                .totalPages(pages.getTotalPages())
                .totalElements(pages.getTotalElements())
                .content(orderDTOs)
                .build();
    }

    @Override
    @Transactional
    public OrderDTO getOrderById(Integer id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order with id [%d] not found".formatted(id)));

        return orderDTOMapper.toDTO(order);
    }

    @Override
    @Transactional
    public List<OrderDTO> getOrderByUserId(Integer userId) {

        if (isAuthorized(String.valueOf(userId))) {
            throw new AccessDeniedException("You are not authorized to see this order");
        }

        List<Order> orders = orderRepository.findByUserId(userId);

        orders.sort(Comparator.comparing(Order::getOrderDate).reversed());

        return orders.stream().map(orderDTOMapper::toDTO).toList();
    }

    @Override
    @Transactional
    public OrderDTO updateOrderById(Integer id, OrderUpdateRequest request) {

        Order orderUpdateRequest = orderDTOMapper.toEntity(request);

        PaymentMethod paymentMethod = paymentMethodRepository.findByName(request.getPaymentMethod())
                .orElseThrow(() -> new ResourceNotFoundException("Payment method with name [%s] not found".formatted(request.getPaymentMethod())));

        ShippingMethod shippingMethod = shippingMethodRepository.findByName(request.getShippingMethod())
                .orElseThrow(() -> new ResourceNotFoundException("Shipping method with name [%s] not found".formatted(request.getPaymentMethod())));

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order with id [%d] not found".formatted(id)));

        boolean changed = false;

        Double totalAmount = 0.0;

        if (orderUpdateRequest.getUserId() != null && !orderUpdateRequest.getUserId().equals(order.getUserId())) {
            order.setUserId(orderUpdateRequest.getUserId());
            changed = true;
        }

        if (orderUpdateRequest.getAddressId() != null && !orderUpdateRequest.getAddressId().equals(order.getAddressId())) {
            order.setAddressId(orderUpdateRequest.getAddressId());
            changed = true;
        }

        if (!paymentMethod.getName().equals(order.getPaymentMethod().getName())) {
            order.setPaymentMethod(paymentMethod);
            changed = true;
        }

        if (!shippingMethod.getName().equals(order.getShippingMethod().getName())) {
            order.setShippingMethod(shippingMethod);
            changed = true;
        }

        if (orderUpdateRequest.getOrderDate() != null && !orderUpdateRequest.getOrderDate().equals(order.getOrderDate())) {
            order.setOrderDate(orderUpdateRequest.getOrderDate());
            changed = true;
        }

        if (orderUpdateRequest.getOrderStatus() != null && !orderUpdateRequest.getOrderStatus().equals(order.getOrderStatus())) {
            order.setOrderStatus(orderUpdateRequest.getOrderStatus());
            changed = true;
        }

        if (orderUpdateRequest.getPaymentStatus() != null && !orderUpdateRequest.getPaymentStatus().equals(order.getPaymentStatus())) {
            order.setPaymentStatus(orderUpdateRequest.getPaymentStatus());
            changed = true;
        }

        List<OrderLine> orderLinesUpdateRequest = orderUpdateRequest.getOrderLines();
        List<OrderLine> orderLines = order.getOrderLines();


        if (orderLines.size() > orderLinesUpdateRequest.size()) {
            orderLines.removeIf(existingOrderLine ->
                    orderLinesUpdateRequest
                            .stream()
                            .noneMatch(orderLine -> orderLine.getProductItemId().equals(existingOrderLine.getProductItemId())
                                    || orderLine.getQuantity().equals(existingOrderLine.getQuantity()))
            );
            changed = true;
        }

        if (orderLines.size() < orderLinesUpdateRequest.size()) {
            for (OrderLine orderLineUpdate : orderLinesUpdateRequest) {
                boolean isNewOrderLine = orderLines
                        .stream()
                        .noneMatch(orderLine -> orderLine.getProductItemId().equals(orderLineUpdate.getProductItemId())
                                || orderLine.getQuantity().equals(orderLineUpdate.getQuantity()));

                if (isNewOrderLine) {
                    orderLineUpdate.setOrder(order);
                    orderLines.add(orderLineUpdate);
                    changed = true;
                }
            }
        }


        if (orderLines.size() == orderLinesUpdateRequest.size()) {
            for (int i = 0; i < orderLines.size(); i++) {
                OrderLine orderLine = orderLines.get(i);
                OrderLine orderLineUpdate = orderLinesUpdateRequest.get(i);

                if (orderLineUpdate != null && !orderLine.getProductItemId().equals(orderLineUpdate.getProductItemId())) {
                    orderLine.setProductItemId(orderLineUpdate.getProductItemId());
                    changed = true;
                }

                if (orderLineUpdate != null && !orderLine.getQuantity().equals(orderLineUpdate.getQuantity())) {
                    orderLine.setQuantity(orderLineUpdate.getQuantity());
                    changed = true;
                }
            }
        }

        for (OrderLine orderLine : orderLinesUpdateRequest) {
            ProductItemDTO productItemDTO = productItemService.getProductItemById(orderLine.getProductItemId());
            orderLine.setPrice(productItemDTO.getPrice() * orderLine.getQuantity());
        }


        totalAmount += orderLinesUpdateRequest.stream().map(OrderLine::getPrice).reduce(0.0, Double::sum);
        totalAmount += shippingMethod.getPrice();

        order.setTotalAmount(totalAmount);

        if (!changed) {
            throw new RequestValidationException("No data changes found");
        }

        return orderDTOMapper.toDTO(order);
    }

    @Override
    @Transactional
    public OrderDTO updateOrderInfo(OrderUpdateRequestForUser request) {

        if (isAuthorized(String.valueOf(request.getUserId()))) {
            throw new AccessDeniedException("You are not authorized to update this order");
        }

        Order order = orderRepository.findById(request.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Order with id [%d] not found".formatted(request.getId())));

        List<Order> orders = orderRepository.findByUserId(request.getUserId());

        if (!orders.contains(order)) {
            throw new ResourceNotFoundException("Order with id [%d] not found".formatted(request.getId()));
        }

        Order orderUpdateRequest = orderDTOMapper.toEntity(request);

        PaymentMethod paymentMethod = paymentMethodRepository.findByName(request.getPaymentMethod())
                .orElseThrow(() -> new ResourceNotFoundException("Payment method with name [%s] not found".formatted(request.getPaymentMethod())));

        ShippingMethod shippingMethod = shippingMethodRepository.findByName(request.getShippingMethod())
                .orElseThrow(() -> new ResourceNotFoundException("Shipping method with name [%s] not found".formatted(request.getPaymentMethod())));

        boolean changed = false;

        Double totalAmount = 0.0;

        if (orderUpdateRequest.getUserId() != null && !orderUpdateRequest.getUserId().equals(order.getUserId())) {
            order.setUserId(orderUpdateRequest.getUserId());
            changed = true;
        }

        if (orderUpdateRequest.getAddressId() != null && !orderUpdateRequest.getAddressId().equals(order.getAddressId())) {
            order.setAddressId(orderUpdateRequest.getAddressId());
            changed = true;
        }

        if (!paymentMethod.getName().equals(order.getPaymentMethod().getName())) {
            order.setPaymentMethod(paymentMethod);
            changed = true;
        }

        if (!shippingMethod.getName().equals(order.getShippingMethod().getName())) {
            order.setShippingMethod(shippingMethod);
            changed = true;
        }

        List<OrderLine> orderLinesUpdateRequest = orderUpdateRequest.getOrderLines();
        List<OrderLine> orderLines = order.getOrderLines();


        if (orderLines.size() > orderLinesUpdateRequest.size()) {
            orderLines.removeIf(existingOrderLine ->
                    orderLinesUpdateRequest
                            .stream()
                            .noneMatch(orderLine -> orderLine.getProductItemId().equals(existingOrderLine.getProductItemId())
                                    || orderLine.getQuantity().equals(existingOrderLine.getQuantity()))
            );
            changed = true;
        }

        if (orderLines.size() < orderLinesUpdateRequest.size()) {
            for (OrderLine orderLineUpdate : orderLinesUpdateRequest) {
                boolean isNewOrderLine = orderLines
                        .stream()
                        .noneMatch(orderLine -> orderLine.getProductItemId().equals(orderLineUpdate.getProductItemId())
                                || orderLine.getQuantity().equals(orderLineUpdate.getQuantity()));

                if (isNewOrderLine) {
                    orderLineUpdate.setOrder(order);
                    orderLines.add(orderLineUpdate);
                    changed = true;
                }
            }
        }

        if (orderLines.size() == orderLinesUpdateRequest.size()) {
            for (int i = 0; i < orderLines.size(); i++) {
                OrderLine orderLine = orderLines.get(i);
                OrderLine orderLineUpdate = orderLinesUpdateRequest.get(i);

                if (orderLineUpdate != null && !orderLine.getProductItemId().equals(orderLineUpdate.getProductItemId())) {
                    orderLine.setProductItemId(orderLineUpdate.getProductItemId());
                    changed = true;
                }

                if (orderLineUpdate != null && !orderLine.getQuantity().equals(orderLineUpdate.getQuantity())) {
                    orderLine.setQuantity(orderLineUpdate.getQuantity());
                    changed = true;
                }
            }
        }

        for (OrderLine orderLine : orderLinesUpdateRequest) {
            ProductItemDTO productItemDTO = productItemService.getProductItemById(orderLine.getProductItemId());
            orderLine.setPrice(productItemDTO.getPrice() * orderLine.getQuantity());
        }

        totalAmount += orderLinesUpdateRequest.stream().map(OrderLine::getPrice).reduce(0.0, Double::sum);
        totalAmount += shippingMethod.getPrice();

        order.setTotalAmount(totalAmount);

        if (!changed) {
            throw new RequestValidationException("No data changes found");
        }

        return orderDTOMapper.toDTO(order);
    }

    @Override
    public void cancelOrder(OrderCancellationRequest request) {
        if (isAuthorized(String.valueOf(request.getUserId()))) {
            throw new AccessDeniedException("You are not authorized to cancel this order");
        }

        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order with id [%d] not found".formatted(request.getOrderId())));

        if (order.getOrderStatus().equals("Đã hủy")) {
            throw new ResourceNotFoundException("Order with id [%d] has been cancelled".formatted(request.getOrderId()));
        }

        if (!order.getOrderStatus().equals("Chờ xác nhận")) {
            throw new RequestValidationException("Order cancellation failed, order with id [%d] has been packaging or delivering!".formatted(request.getOrderId()));
        }

        order.setOrderStatus("Đã hủy");
        orderRepository.save(order);
    }

    @Override
    public void deleteOrderById(Integer id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order with id [%d] not found".formatted(id)));

        orderRepository.delete(order);
    }
}
