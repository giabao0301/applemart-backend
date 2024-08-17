package com.applemart.product;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(
        name = "Product",
        description = "REST APIs for Product"
)
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/products")
public class ProductController {

    private final ProductService productService;

    @Operation(
            summary = "Get all product REST API",
            description = "Get all product REST API, can be used with param like pageNo, pageSize, sortBy, sortDir (asc, desc) for pagination and sort"
    )
    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getAllProducts() {
        List<ProductDTO> products = productService.getAllProducts();

        ApiResponse<List<ProductDTO>> apiResponse = ApiResponse.<List<ProductDTO>>builder()
                .status(HttpStatus.OK.value())
                .message("OK")
                .data(products)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable("productId") Integer productId) {
        ProductDTO product = productService.getProduct(productId);
        return new ResponseEntity<>(product,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        return new ResponseEntity<>(productService.createProduct(productDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(
            @PathVariable Integer productId,
            @RequestBody @Valid ProductDTO productDTO
    ) {
        return new ResponseEntity<>(productService.updateProduct(productId, productDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer productId) {
        productService.deleteProduct(productId);
        return new ResponseEntity<>("Product is deleted successfully", HttpStatus.OK);
    }
}
