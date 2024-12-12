package com.applemart.product.productItem;


import com.applemart.product.ProductDTO;
import com.applemart.product.common.ApiResponse;
import com.applemart.product.common.PageResponse;
import com.applemart.product.utils.AppConstants;
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

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductItemDTO>> getProductItemById(@PathVariable("id") Integer id) {
        ApiResponse<ProductItemDTO> apiResponse = ApiResponse.<ProductItemDTO>builder()
                .status(HttpStatus.OK.value())
                .message("OK")
                .data(productItemService.getProductItemById(id))
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<ApiResponse<ProductItemDTO>> getProductItemBySlug(@RequestParam("slug") String slug) {
        ApiResponse<ProductItemDTO> apiResponse = ApiResponse.<ProductItemDTO>builder()
                .status(HttpStatus.OK.value())
                .message("OK")
                .data(productItemService.getProductItemBySlug(slug))
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductItemDTO> createProductItem(@RequestBody @Valid ProductItemDTO request) {
        return new ResponseEntity<>(productItemService.createProductItem(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductItemDTO>> updateProductItem(
            @PathVariable("id") Integer id,
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

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<PageResponse<ProductItemDTO>>> searchProduct(
            @RequestParam("slug") String slug,
            @RequestParam(value = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(value = "sort", required = false, defaultValue = AppConstants.DEFAULT_SORT_BY) String sort,
            @RequestParam(value = "dir", required = false, defaultValue = AppConstants.DEFAULT_SORT_DIRECTION) String dir,
            @RequestParam(value = "minPrice", required = false) Double minPrice,
            @RequestParam(value = "maxPrice", required = false) Double maxPrice
    ) {
        PageResponse<ProductItemDTO> product = productItemService.searchProductItem(slug, page, size, sort, dir, minPrice, maxPrice);
        ApiResponse<PageResponse<ProductItemDTO>> apiResponse = ApiResponse.<PageResponse<ProductItemDTO>>builder()
                .status(HttpStatus.OK.value())
                .message("OK")
                .data(product)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
