package com.applemart.order.payment;

import com.applemart.order.common.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "payment", url = "http://localhost:8083")
public interface PaymentClient {
    @GetMapping("/api/v1/payments/vn-pay")
    ApiResponse<VNPayResponse> payVNPay(@RequestParam("amount") String amount,
                                        @RequestParam("bankCode") String bankCode,
                                        @RequestParam("orderId") String orderId);
}
