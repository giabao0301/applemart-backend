package com.applemart.product.productItem;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ProductItemDTOMapper {
    private final ModelMapper modelMapper = new ModelMapper();

    public ProductItemDTO toDTO(ProductItem productItem) {
        modelMapper
                .typeMap(ProductItem.class, ProductItemDTO.class)
                .addMapping(src -> src.getProduct().getName(), ProductItemDTO::setProductName)
                .addMapping(src -> src.getProduct().getCategory().getUrlKey(), ProductItemDTO::setCategory)
                .addMapping(src -> src.getProduct().getReleaseYear(), ProductItemDTO::setReleaseYear);

        return modelMapper.map(productItem, ProductItemDTO.class);
    }

    public ProductItem toEntity(ProductItemDTO productItemDTO) {
        return modelMapper.map(productItemDTO, ProductItem.class);
    }
}
