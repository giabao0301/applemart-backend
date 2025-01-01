package com.applemart.order;

import com.applemart.order.common.PageResponse;

import java.util.List;

public interface OrderService {
    OrderCreationResponse createOrder(OrderCreationRequest orderCreationRequest);
    PageResponse<OrderDTO> getAllOrders (int page, int size, String sort, String dir);
    OrderDTO getOrderById(Integer id);
    List<OrderDTO> getOrderByUserId(Integer userId);
    OrderDTO updateOrderById(Integer id, OrderUpdateRequest orderUpdateRequest);
    OrderDTO updateOrderInfo(OrderUpdateRequestForUser orderUpdateRequest);
    void cancelOrder(OrderCancellationRequest orderCancellationRequest);
    void deleteOrderById(Integer orderCancellationRequest);
    OrderStatsDTO getOrderStats();
}
