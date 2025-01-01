package com.applemart.order.clients.address;

import com.applemart.order.clients.product.ProductItemClient;
import com.applemart.order.clients.product.ProductItemDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressClient addressClient;

    public AddressDTO getAddressById(String userId, String id) {
        return addressClient.getAddressById(userId, id).getData();
    }

}
