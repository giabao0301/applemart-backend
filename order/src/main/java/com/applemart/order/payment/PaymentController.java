package com.applemart.order.payment;

import com.applemart.order.OrderRepository;
import com.applemart.order.common.ApiResponse;
import com.applemart.order.shipping.ShippingMethodDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    private final PaymentMethodRepository paymentMethodRepository;
    private final PaymentMethodDTOMapper paymentMethodDTOMapper;
    private final OrderRepository orderRepository;

    @GetMapping
    public ResponseEntity<ApiResponse<List<PaymentMethodDTO>>> getPayments() {
        List<PaymentMethodDTO> paymentMethods = paymentMethodRepository.findAll().stream()
                .map(paymentMethodDTOMapper::toDTO)
                .toList();

        ApiResponse<List<PaymentMethodDTO>> apiResponse = ApiResponse.<List<PaymentMethodDTO>>builder()
                .status(HttpStatus.OK.value())
                .message("OK")
                .data(paymentMethods)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

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
    public ResponseEntity<ApiResponse<VNPayResponse>> payCallbackHandler(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String status = request.getParameter("vnp_ResponseCode");
        String orderId = request.getParameter("vnp_TxnRef");

        ApiResponse<VNPayResponse> apiResponse;
        if (status.equals("00")) {

            orderRepository.updateOrderStatusById(orderId, "Hoàn thành");

            response.sendRedirect("http://localhost:3000/user/purchase");

            apiResponse = ApiResponse.<VNPayResponse>builder()
                    .status(HttpStatus.OK.value())
                    .message("OK")
                    .data(new VNPayResponse("00", "Success", ""))
                    .build();

            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } else {
            response.sendRedirect("http://localhost:3000/user/purchase");

            apiResponse = ApiResponse.<VNPayResponse>builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message("FAILED")
                    .build();
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }
    }
}
