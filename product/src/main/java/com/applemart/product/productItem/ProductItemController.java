package com.applemart.product.productItem;


import com.applemart.product.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@AllArgsConstructor
@RestController
@RequestMapping("api/v1/productItems")
public class ProductItemController {
    private ProductItemService productItemService;

    @GetMapping("/{slug}")
    public ResponseEntity<ApiResponse<ProductItemDTO>> getAllProductItems(@PathVariable String slug) {
        ApiResponse<ProductItemDTO> apiResponse = ApiResponse.<ProductItemDTO>builder()
                .status(HttpStatus.OK.value())
                .message("OK")
                .data(productItemService.getProductBySlug(slug))
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
    public ResponseEntity<String> deleteProductItem(@PathVariable Integer id) {
        productItemService.deleteProductItem(id);
        return new ResponseEntity<>("Product item [%d] is deleted successfully".formatted(id), HttpStatus.OK);
    }
}
