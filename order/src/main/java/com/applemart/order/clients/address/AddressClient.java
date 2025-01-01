package com.applemart.order.clients.address;

import com.applemart.order.common.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "address", url = "http://localhost:8080")
public interface AddressClient {

    @GetMapping("/api/v1/users/{userId}/addresses/{id}")
    ApiResponse<AddressDTO> getAddressById(@PathVariable("userId") String userId, @PathVariable("id") String id);
}
