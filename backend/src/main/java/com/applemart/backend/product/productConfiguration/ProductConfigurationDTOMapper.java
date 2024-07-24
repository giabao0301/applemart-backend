package com.applemart.backend.product.productConfiguration;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductConfigurationDTOMapper {
    private final ModelMapper modelMapper = new ModelMapper();
    public ProductConfigurationDTO toDTO(ProductConfiguration productConfiguration) {
        return modelMapper.map(productConfiguration, ProductConfigurationDTO.class);
    }

    public ProductConfiguration toEntity(ProductConfigurationDTO productConfigurationDTO) {
        return modelMapper.map(productConfigurationDTO, ProductConfiguration.class);
    }
}
