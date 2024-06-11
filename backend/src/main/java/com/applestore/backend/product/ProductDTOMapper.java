package com.applestore.backend.product;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductDTOMapper {
    private final ModelMapper modelMapper = new ModelMapper();

    public ProductDTO toDTO(Product product) {
        modelMapper
                .typeMap(Product.class, ProductDTO.class)
                .addMapping(src -> src.getCategory().getId(), ProductDTO::setCategory);
        return modelMapper.map(product, ProductDTO.class);
    }

    public Product toEntity(ProductDTO productDTO) {
        return modelMapper.map(productDTO, Product.class);
    }
}
