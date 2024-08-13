package com.applemart.product.productImage;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductImageDTOMapper {
    private final ModelMapper modelMapper = new ModelMapper();

    public ProductImageDTO toDTO(ProductImage productImage) {
        return modelMapper.map(productImage, ProductImageDTO.class);
    }
    public ProductImage toEntity(ProductImageDTO productImageDTO) {
        return modelMapper.map(productImageDTO, ProductImage.class);
    }
}
