package com.applemart.order;

import com.applemart.order.common.ApiResponse;
import com.applemart.order.common.PageResponse;
import com.applemart.order.utils.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderCreationResponse> createOrder(@RequestBody OrderCreationRequest orderCreationRequest) {
        return new ResponseEntity<>(orderService.createOrder(orderCreationRequest), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<OrderDTO>>> getAllOrders(
            @RequestParam(value = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(value = "sort", required = false, defaultValue = AppConstants.DEFAULT_SORT_BY) String sort,
            @RequestParam(value = "dir", required = false, defaultValue = AppConstants.DEFAULT_SORT_DIRECTION) String dir
    ) {
        PageResponse<OrderDTO> orders = orderService.getAllOrders(page, size, sort, dir);

        ApiResponse<PageResponse<OrderDTO>> apiResponse = ApiResponse.<PageResponse<OrderDTO>>builder()
                .status(HttpStatus.OK.value())
                .message("OK")
                .data(orders)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderDTO>> getOrderById(@PathVariable("id") Integer id) {
        OrderDTO order = orderService.getOrderById(id);

        ApiResponse<OrderDTO> apiResponse = ApiResponse.<OrderDTO>builder()
                .status(HttpStatus.OK.value())
                .message("OK")
                .data(order)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<OrderDTO>>> getOrderByUserId(@RequestParam("userId") Integer userId) {
        List<OrderDTO> orders = orderService.getOrderByUserId(userId);

        ApiResponse<List<OrderDTO>> apiResponse = ApiResponse.<List<OrderDTO>>builder()
                .status(HttpStatus.OK.value())
                .message("OK")
                .data(orders)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderDTO>> updateOrderById(@PathVariable("id") Integer id,
                                                                 @RequestBody OrderUpdateRequest orderUpdateRequest) {
        OrderDTO updatedOrder = orderService.updateOrderById(id, orderUpdateRequest);

        ApiResponse<OrderDTO> apiResponse = ApiResponse.<OrderDTO>builder()
                .status(HttpStatus.OK.value())
                .message("OK")
                .data(updatedOrder)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/info")
    public ResponseEntity<ApiResponse<OrderDTO>> updateOrderById(@RequestBody OrderUpdateRequestForUser orderUpdateRequestForUser) {
        OrderDTO updatedOrder = orderService.updateOrderInfo(orderUpdateRequestForUser);

        ApiResponse<OrderDTO> apiResponse = ApiResponse.<OrderDTO>builder()
                .status(HttpStatus.OK.value())
                .message("OK")
                .data(updatedOrder)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> cancelOrderById(@RequestBody OrderCancellationRequest orderCancellationRequest) {
        orderService.cancelOrder(orderCancellationRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderById(@PathVariable("id") Integer id) {
        orderService.deleteOrderById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
