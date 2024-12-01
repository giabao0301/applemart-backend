package com.applemart.order.clients.product;

import com.applemart.order.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class ProductItemClientFallback implements ProductItemClient {

    @Override
    public ApiResponse<ProductItemDTO> getProductItemById(Integer id) {
        ProductItemDTO defaultProductItem = ProductItemDTO.builder()
                .id(-1)
                .productName("Default Product")
                .name("Default Name")
                .quantityInStock(0)
                .price(0.0)
                .imageUrl("https://cdn.icon-icons.com/icons2/1678/PNG/512/wondicon-ui-free-parcel_111208.png")
                .slug("default-product")
                .attributes(new ArrayList<>())
                .configurations(new ArrayList<>())
                .build();

        return ApiResponse.<ProductItemDTO>builder()
                .status(HttpStatus.OK.value())
                .message("OK")
                .data(defaultProductItem)
                .build();
    }
}
