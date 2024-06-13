package com.applestore.backend.productItem;

import com.applestore.backend.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ProductItemServiceImpl implements ProductItemService {
    private ProductItemRepository productItemRepository;
    private ProductItemDTOMapper productItemDTOMapper;

    @Override
    public ProductItemDTO getProductItemBySlug(String slug) {
        ProductItem productItem = productItemRepository.findBySlug(slug).orElseThrow(() -> new ResourceNotFoundException("product [%s] not found".formatted(slug)));

        return productItemDTOMapper.toDTO(productItem);
    }
}
