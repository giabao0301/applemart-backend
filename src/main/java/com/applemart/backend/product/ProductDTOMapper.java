package com.applemart.backend.product;

import com.applemart.backend.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductDTOMapper {
    private final ModelMapper modelMapper = new ModelMapper();
    private final CategoryRepository categoryRepository;

    public ProductDTO toDTO(Product product) {
        modelMapper
                .typeMap(Product.class, ProductDTO.class)
                .addMapping(src -> src.getCategory().getUrlKey(), ProductDTO::setCategory);
        return modelMapper.map(product, ProductDTO.class);
    }

    public Product toEntity(ProductDTO productDTO) {
        return modelMapper.map(productDTO, Product.class);
    }
}
