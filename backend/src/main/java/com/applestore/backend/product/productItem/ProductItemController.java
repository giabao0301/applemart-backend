package com.applestore.backend.product.productItem;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@AllArgsConstructor
@RestController
@RequestMapping("${api-prefix.path}/productItems")
public class ProductItemController {
    private  ProductItemService productItemService;

    @PostMapping
    public ResponseEntity<ProductItemDTO> createProductItem(@RequestBody ProductItemDTO productItemDTO) {
        return new ResponseEntity<>(productItemService.createProductItem(productItemDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/{productItemId}")
    public ResponseEntity<String> deleteProductItem(@PathVariable Integer productItemId) {
        productItemService.deleteProductItemById(productItemId);
        return new ResponseEntity<>("Product item [%d] is deleted successfully".formatted(productItemId), HttpStatus.OK);
    }
}
