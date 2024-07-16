package com.applemart.backend.product.productItem;

import com.applemart.backend.product.productConfiguration.ProductConfiguration;
import com.applemart.backend.product.productConfiguration.ProductConfigurationDTO;
import com.applemart.backend.product.productConfiguration.ProductConfigurationId;
import com.applemart.backend.product.productImage.ProductImageRepository;
import com.applemart.backend.product.variationOption.VariationOptionRepository;
import com.applemart.backend.utils.Slugify;
import com.applemart.backend.exception.DuplicateResourceException;
import com.applemart.backend.exception.ResourceNotFoundException;
import com.applemart.backend.product.Product;
import com.applemart.backend.product.ProductRepository;
import com.applemart.backend.product.productConfiguration.ProductConfigurationRepository;
import com.applemart.backend.product.productImage.ProductImage;
import com.applemart.backend.product.productImage.ProductImageDTO;
import com.applemart.backend.product.variationOption.VariationOption;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ProductItemServiceImpl implements ProductItemService {
    private final ProductRepository productRepository;
    private final ProductItemRepository productItemRepository;
    private final ProductItemDTOMapper productItemDTOMapper;
    private final ProductImageRepository productImageRepository;
    private final VariationOptionRepository variationOptionRepository;
    private final ProductConfigurationRepository productConfigurationRepository;

    @Override
    public ProductItemDTO createProductItem(ProductItemDTO productItemDTO) {
        ProductItem newProductItem = productItemDTOMapper.toEntity(productItemDTO);

        if (productItemRepository.existsBySku(newProductItem.getSku())) {
            throw new DuplicateResourceException("Product item already exists");
        }

//        Tìm product bằng tên rồi gán cho product item
        Product product = productRepository.findByName(productItemDTO.getProductName());
        if (product != null) {
            newProductItem.setProduct(product);
        } else {
            throw new ResourceNotFoundException("Product [%s] not found".formatted(productItemDTO.getProductName()));
        }

//        Tự tạo slug từ tên của sản phẩm
        newProductItem.setSlug(Slugify.slugify(newProductItem.getSku()));

        ProductItem savedProductItem = productItemRepository.save(newProductItem);

//        Lưu những hình ảnh của sản phẩm
        List<ProductImageDTO> images = productItemDTO.getImages();
        if (images != null) {
            for (ProductImageDTO productImage : images) {
                ProductImage newProductImage = new ProductImage();
                newProductImage.setImageUrl(productImage.getImageUrl());

                newProductImage.setProductItem(newProductItem);
                productImageRepository.save(newProductImage);
            }
        }

        List<ProductConfigurationDTO> configurationDTOs = productItemDTO.getConfigurations();
        if (configurationDTOs != null) {
            for (ProductConfigurationDTO productConfigurationDTO : configurationDTOs) {

                String variationName = productConfigurationDTO.getVariationOption().getName();
                String value = productConfigurationDTO.getVariationOption().getValue();
                VariationOption variationOption = variationOptionRepository.findByVariationName(variationName, value);

                if (variationOption == null) {
                    throw new ResourceNotFoundException("Variation Option not found");
                }

                ProductConfiguration productConfiguration = new ProductConfiguration();
                ProductConfigurationId productConfigurationId = new ProductConfigurationId();

                productConfigurationId.setProductItemId(newProductItem.getId());
                productConfigurationId.setVariationOptionId(variationOption.getId());

                productConfiguration.setId(productConfigurationId);
                productConfiguration.setVariationOption(variationOption);

                productConfiguration.setProductItem(newProductItem);

                productConfigurationRepository.save(productConfiguration);
            }
        }

        return productItemDTOMapper.toDTO(savedProductItem);
    }


    @Override
    public void deleteProductItem(Integer id) {
        ProductItem productItem = productItemRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product with id [%d] not found".formatted(id)));
        productItemRepository.delete(productItem);
    }
}
