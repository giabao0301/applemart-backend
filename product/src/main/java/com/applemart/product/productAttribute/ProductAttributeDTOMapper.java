package com.applemart.product.productAttribute;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductAttributeDTOMapper {
    private final ModelMapper modelMapper = new ModelMapper();
    public ProductAttributeDTO toDTO(ProductAttribute productConfiguration) {
        return modelMapper.map(productConfiguration, ProductAttributeDTO.class);
    }

    public ProductAttribute toEntity(ProductAttributeDTO productConfigurationDTO) {
        return modelMapper.map(productConfigurationDTO, ProductAttribute.class);
    }
}
