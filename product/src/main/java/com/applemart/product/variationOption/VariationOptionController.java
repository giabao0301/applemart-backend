package com.applemart.product.variationOption;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(
        name = "VariationOption",
        description = "REST APIs for VariationOption"
)
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/variationOptions")
public class VariationOptionController {

    private final VariationOptionService variationOptionService;

    @GetMapping
    public ResponseEntity<List<VariationOptionDTO>> getAll(@RequestParam("productId") Integer id) {
        return new ResponseEntity<>(variationOptionService.getVariationOptionsByProductId(id), HttpStatus.OK);
    }
}
