package com.applemart.product.variationOption;

import com.applemart.product.Product;
import com.applemart.product.ProductRepository;
import com.applemart.product.productConfiguration.ProductConfiguration;
import com.applemart.product.productItem.ProductItem;
import com.applemart.product.productItem.ProductItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VariationOptionServiceImpl implements VariationOptionService {

    private final VariationOptionRepository variationOptionRepository;
    private final ProductItemRepository productItemRepository;
    private final ProductRepository productRepository;
    private final VariationOptionDTOMapper variationOptionDTOMapper;

    @Override
    @Transactional
    public List<VariationOptionDTO> getVariationOptionsByProductId(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product with id [%d] not found".formatted(id)));

        List<ProductItem> productItems = productItemRepository.findProductItemsByProductId(product.getId());

        List<VariationOption> options = new ArrayList<>();

        for (ProductItem productItem : productItems) {
           List<ProductConfiguration> configurations = productItem.getConfigurations();
           for (ProductConfiguration configuration : configurations) {
               options.add(configuration.getVariationOption());
           }
        }

        return options.stream()
                .map(variationOptionDTOMapper::toDTO)
                .toList();
    }
}
