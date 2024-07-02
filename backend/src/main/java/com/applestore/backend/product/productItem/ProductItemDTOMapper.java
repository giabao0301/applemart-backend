package com.applestore.backend.product.productItem;

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
                .addMappings(mapper -> {
                    mapper.map(src -> src.getProduct().getName(), ProductItemDTO::setProductName);
                });
        return modelMapper.map(productItem, ProductItemDTO.class);
    }

    public ProductItem toEntity(ProductItemDTO productItemDTO) {
        modelMapper
                .typeMap(ProductItemDTO.class, ProductItem.class)
                .addMappings(mapper -> {
                   mapper.skip(ProductItem::setImages);
                   mapper.skip(ProductItem::setConfigurations);
                });

        return modelMapper.map(productItemDTO, ProductItem.class);
    }
}
