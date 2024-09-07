package com.applemart.product.productItem;


import com.applemart.product.ProductDTO;
import com.applemart.product.response.ApiResponse;
import com.applemart.product.response.PageResponse;
import com.applemart.product.utils.AppConstants;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@AllArgsConstructor
@RestController
@RequestMapping("api/v1/productItems")
public class ProductItemController {
    private ProductItemService productItemService;

    @GetMapping("/{slug}")
    public ResponseEntity<ApiResponse<List<ProductItemDTO>>> getProductItemsBySlug(@PathVariable("slug") String slug) {
        ApiResponse<List<ProductItemDTO>> apiResponse = ApiResponse.<List<ProductItemDTO>>builder()
                .status(HttpStatus.OK.value())
                .message("OK")
                .data(productItemService.getProductItemsBySlug(slug))
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductItemDTO> createProductItem(@RequestBody @Valid ProductItemDTO request) {
        return new ResponseEntity<>(productItemService.createProductItem(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductItemDTO>> updateProductItem(
            @PathVariable Integer id,
            @RequestBody @Valid ProductItemDTO request
    ) {
        ApiResponse<ProductItemDTO> apiResponse = ApiResponse.<ProductItemDTO>builder()
                .status(HttpStatus.OK.value())
                .message("Product item updated successfully")
                .data(productItemService.updateProductItem(id, request))
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductItem(@PathVariable("id") Integer id) {
        productItemService.deleteProductItem(id);
        return new ResponseEntity<>("Product item [%d] is deleted successfully".formatted(id), HttpStatus.OK);
    }
}
