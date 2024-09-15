package com.applemart.product.category;

import com.applemart.product.Product;
import com.applemart.product.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CategoryDTOMapper {
    private final ModelMapper modelMapper = new ModelMapper();

    public CategoryDTO toDTO(Category category) {

        modelMapper
                .typeMap(Category.class, CategoryDTO.class)
                .addMapping(src -> src.getParentCategory().getName(), CategoryDTO::setParentCategory);

        return modelMapper.map(category, CategoryDTO.class);
    }

    public Category toEntity(CategoryDTO categoryDTO) {
        return modelMapper.map(categoryDTO, Category.class);
    }
}
