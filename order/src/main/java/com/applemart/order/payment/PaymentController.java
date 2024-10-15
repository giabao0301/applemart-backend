package com.applemart.order.payment;

import com.applemart.order.OrderRepository;
import com.applemart.order.common.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    private final OrderRepository orderRepository;

    @GetMapping("/vn-pay")
    public ResponseEntity<ApiResponse<VNPayResponse>> pay(HttpServletRequest request) {
        ApiResponse<VNPayResponse> apiResponse = ApiResponse.<VNPayResponse>builder()
                .status(HttpStatus.OK.value())
                .message("OK")
                .data(paymentService.createVnPayPayment(request))
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/vn-pay-callback")
    public ResponseEntity<ApiResponse<VNPayResponse>> payCallbackHandler(HttpServletRequest request) {
        String status = request.getParameter("vnp_ResponseCode");
        String orderId = request.getParameter("vnp_TxnRef");

        ApiResponse<VNPayResponse> apiResponse;
        if (status.equals("00")) {

            orderRepository.updateOrderStatusById(orderId, "Hoàn thành");

            apiResponse = ApiResponse.<VNPayResponse>builder()
                    .status(HttpStatus.OK.value())
                    .message("OK")
                    .data(new VNPayResponse("00", "Success", ""))
                    .build();

            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } else {
            apiResponse = ApiResponse.<VNPayResponse>builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message("FAILED")
                    .build();
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }
    }
}
