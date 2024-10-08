package com.applemart.product.variationOption;

import com.applemart.product.Product;
import com.applemart.product.ProductDTO;
import com.applemart.product.productItem.ProductItem;
import com.applemart.product.productItem.ProductItemDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class VariationOptionDTOMapper {
    private final ModelMapper modelMapper = new ModelMapper();

    public VariationOptionDTO toDTO(VariationOption variationOption) {
        modelMapper
                .typeMap(VariationOption.class, VariationOptionDTO.class)
                .addMapping(src -> src.getVariation().getName(), VariationOptionDTO::setName);

        return modelMapper.map(variationOption, VariationOptionDTO.class);
    }

    public VariationOption toEntity(VariationOptionDTO variationOptionDTO) {
        return modelMapper.map(variationOptionDTO, VariationOption.class);
    }
}
