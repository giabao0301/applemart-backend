package com.applemart.product;

import com.applemart.product.productItem.ProductItemDTO;
import com.applemart.product.productItem.ProductItemService;
import com.applemart.product.common.ApiResponse;
import com.applemart.product.common.PageResponse;
import com.applemart.product.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
    private final ProductService productService;
    private final ProductItemService productItemService;

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
        log.info("Get all products REST API");
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

    @GetMapping("/slug/{slug}")
    public ResponseEntity<ProductDTO> getProductBySlug(@PathVariable("slug") String slug) {
        ProductDTO product = productService.getProductBySlug(slug);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getProductByCategory(@PathVariable("category") String category) {
        ApiResponse<List<ProductDTO>> apiResponse = ApiResponse.<List<ProductDTO>>builder()
                .status(HttpStatus.OK.value())
                .message("OK")
                .data(productService.getProductByCategory(category))
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}/productItems")
    public ResponseEntity<ApiResponse<List<ProductItemDTO>>> getProductItemByProductId(@PathVariable("id") Integer id) {
        ApiResponse<List<ProductItemDTO>> apiResponse = ApiResponse.<List<ProductItemDTO>>builder()
                .status(HttpStatus.OK.value())
                .message("OK")
                .data(productItemService.getProductItemsByProductId(id))
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/productItems")
    public ResponseEntity<ApiResponse<List<ProductItemDTO>>> getProductItemByProductSlug(@RequestParam("slug") String slug) {
        ApiResponse<List<ProductItemDTO>> apiResponse = ApiResponse.<List<ProductItemDTO>>builder()
                .status(HttpStatus.OK.value())
                .message("OK")
                .data(productItemService.getProductItemsByProductSlug(slug))
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
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
        return new ResponseEntity<>("Product is deleted successfully", HttpStatus.ACCEPTED);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<PageResponse<ProductDTO>>> searchProduct(
            @RequestParam("name") String name,
            @RequestParam(value = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(value = "sort", required = false, defaultValue = AppConstants.DEFAULT_SORT_BY) String sort,
            @RequestParam(value = "dir", required = false, defaultValue = AppConstants.DEFAULT_SORT_DIRECTION) String dir
    ) {
        PageResponse<ProductDTO> product = productService.searchProduct(name, page, size, sort, dir);
        ApiResponse<PageResponse<ProductDTO>> apiResponse = ApiResponse.<PageResponse<ProductDTO>>builder()
                .status(HttpStatus.OK.value())
                .message("OK")
                .data(product)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/suggestions")
    public ResponseEntity<List<String>> getSuggestions(@RequestParam("query") String query) {
        List<String> suggestions = productService.getSuggestions(query);
        return ResponseEntity.ok(suggestions);
    }
}
