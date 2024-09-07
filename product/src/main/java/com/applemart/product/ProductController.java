package com.applemart.product;

import com.applemart.product.response.ApiResponse;
import com.applemart.product.response.PageResponse;
import com.applemart.product.utils.AppConstants;
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
            description = "Get all product REST API, can be used with param like page, size, sortBy, sortDir (asc, desc) for pagination and sort"
    )
    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<ProductDTO>>> getAllProducts(
            @RequestParam(value = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(value = "sort", required = false, defaultValue = AppConstants.DEFAULT_SORT_BY) String sort,
            @RequestParam(value = "dir", required = false, defaultValue = AppConstants.DEFAULT_SORT_DIRECTION) String dir
    ) {
        PageResponse<ProductDTO> products = productService.getAllProducts(page, size, sort, dir);

        ApiResponse<PageResponse<ProductDTO>> apiResponse = ApiResponse.<PageResponse<ProductDTO>>builder()
                .status(HttpStatus.OK.value())
                .message("OK")
                .data(products)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable("id") Integer id) {
        ProductDTO product = productService.getProductById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<ProductDTO> getProductBySlug(@RequestParam("slug") String slug) {
        ProductDTO product = productService.getProductBySlug(slug);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        return new ResponseEntity<>(productService.createProduct(productDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(
            @PathVariable("id") Integer id,
            @RequestBody @Valid ProductDTO productDTO
    ) {
        return new ResponseEntity<>(productService.updateProduct(id, productDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Integer id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>("Product is deleted successfully", HttpStatus.OK);
    }
}
