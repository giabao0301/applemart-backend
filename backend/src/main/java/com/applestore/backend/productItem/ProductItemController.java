package com.applestore.backend.productItem;


import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping("${api-prefix.path}")
public class ProductItemController {
    private  ProductItemService productItemService;

    @GetMapping("/product/{slug}")
    public ResponseEntity<ProductItemDTO> getAllProducts(@PathVariable String slug) {
        ProductItemDTO productItem = productItemService.getProductItemBySlug(slug);
        return new ResponseEntity<>(productItem, HttpStatus.OK);
    }
}
