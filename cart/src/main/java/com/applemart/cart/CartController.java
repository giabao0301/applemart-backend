package com.applemart.cart;

import com.applemart.cart.clients.product.ProductItemDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Cart",
        description = "REST APIs for Cart"
)
@RestController
@RequestMapping("api/v1/carts")
@RequiredArgsConstructor
public class CartController {
    private final CartRedisService cartRedisService;

    @GetMapping("{userId}")
    public ResponseEntity<ApiResponse<List<ProductItemDTO>>> getProductsFromCartByUserId(@PathVariable("userId") String userId) {
        ApiResponse<List<ProductItemDTO>> apiResponse = ApiResponse.<List<ProductItemDTO>>builder()
                .status(HttpStatus.OK.value())
                .message("OK")
                .data(cartRedisService.getProductsFromCartByUserId(userId))
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);

    }

    @PostMapping("/{userId}")
    public ResponseEntity<String> addProductToCart(@PathVariable("userId") String userId, @RequestBody CartItemRequest cartItemRequest) {
        cartRedisService.addProductToCart(userId, cartItemRequest);
        return new ResponseEntity<>("Add to cart successfully!", HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<String> updateProductInCart(@PathVariable("userId") String userId, @RequestBody CartItemRequest cartItemRequest) {
        cartRedisService.updateProductInCart(userId, cartItemRequest);
        return new ResponseEntity<>("Update cart successfully!", HttpStatus.CREATED);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteProductsInCart(@PathVariable("userId") String userId, @RequestBody CartItemDeletionRequest request){
        this.cartRedisService.deleteProductsInCart(userId, request);
        return new ResponseEntity<>("Deleted product in cart successfully!", HttpStatus.OK);
    }

    @DeleteMapping("/all/{userId}")
    public ResponseEntity<String> deleteAllProductsInCart(@PathVariable("userId") String userId) {
        this.cartRedisService.deleteAllProductsInCart(userId);
        return new ResponseEntity<>("Deleted all products in cart successfully!", HttpStatus.OK);
    }
}
