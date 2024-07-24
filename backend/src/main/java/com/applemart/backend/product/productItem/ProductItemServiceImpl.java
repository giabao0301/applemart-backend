package com.applemart.backend.product.productItem;

import com.applemart.backend.exception.RequestValidationException;
import com.applemart.backend.product.productConfiguration.*;
import com.applemart.backend.product.variationOption.VariationOptionRepository;
import com.applemart.backend.exception.DuplicateResourceException;
import com.applemart.backend.exception.ResourceNotFoundException;
import com.applemart.backend.product.Product;
import com.applemart.backend.product.ProductRepository;
import com.applemart.backend.product.productImage.ProductImage;
import com.applemart.backend.product.variationOption.VariationOption;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.applemart.backend.utils.Slugify.slugify;

@AllArgsConstructor
@Service
public class ProductItemServiceImpl implements ProductItemService {
    private final ProductRepository productRepository;
    private final ProductItemRepository productItemRepository;
    private final ProductItemDTOMapper productItemDTOMapper;
    private final VariationOptionRepository variationOptionRepository;
    private final ProductConfigurationRepository productConfigurationRepository;

    @Override
    @Transactional
    public ProductItemDTO getProductBySlug(String slug) {
        ProductItem productItem = productItemRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Product item not found"));
        return productItemDTOMapper.toDTO(productItem);
    }

    @Override
    @Transactional
    public List<ProductItemDTO> getProductItemByProductName(String productName) {
        return productItemRepository.findBySlug(productName)
                .stream()
                .map(productItemDTOMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional
    public ProductItemDTO createProductItem(ProductItemDTO request) {
        ProductItem newProductItem = productItemDTOMapper.toEntity(request);

//        Tìm product bằng tên rồi gán cho product item
        Product product = productRepository.findByName(request.getProductName())
                .orElseThrow(() -> new ResourceNotFoundException("Product [%s] not found".formatted(request.getProductName())));

        newProductItem.setProduct(product);

        StringBuilder sku = new StringBuilder(product.getName());

//        Lưu những hình ảnh của sản phẩm
        List<ProductImage> images = newProductItem.getImages();
        if (images != null) {
            for (ProductImage image : images) {
                image.setProductItem(newProductItem);
            }
        }

        List<ProductConfiguration> configurations = newProductItem.getConfigurations();
        if (configurations != null) {
            for (ProductConfiguration configuration : configurations) {

                String optionName = configuration.getVariationOption().getVariation().getName();
                String optionValue = configuration.getVariationOption().getValue();

                VariationOption variationOption = variationOptionRepository.findByVariationNameAndValue(optionName, optionValue)
                        .orElseThrow(() -> new ResourceNotFoundException("Variation option not found"));

                configuration.setId(new ProductConfigurationId(newProductItem.getId(), variationOption.getId()));
                configuration.setVariationOption(variationOption);
                configuration.setProductItem(newProductItem);

                if (optionName.equals("ssd")) {
                    sku.append("/").append(optionValue);
                } else {
                    sku.append(" ").append(optionValue);
                }
            }
        }

        if (productItemRepository.existsBySku(sku.toString())) {
            throw new DuplicateResourceException("Product item [%s] already exists".formatted(sku));
        }

//      Tạo sku từ tên sản phẩm và các option
        newProductItem.setSku(sku.toString());

//      Tự tạo slug từ tên của sku
        newProductItem.setSlug(slugify(newProductItem.getSku()));

        ProductItem savedProductItem = productItemRepository.save(newProductItem);

        return productItemDTOMapper.toDTO(savedProductItem);
    }

    @Override
    @Transactional
    public ProductItemDTO updateProductItem(Integer id, ProductItemDTO request) {
//        ProductItem được request để update
        ProductItem productItemUpdateRequest = productItemDTOMapper.toEntity(request);

//        Product Item ở trong database
        ProductItem productItem = productItemRepository.getReferenceById(id);

        //        Product của productItem
        Product product = productRepository.findByName(request.getProductName())
                .orElseThrow(() -> new ResourceNotFoundException("Product [%s] not found".formatted(request.getProductName())));

        productItem.setProduct(product);

        StringBuilder sku = new StringBuilder(product.getName());

        boolean changed = false;

        if (productItemUpdateRequest.getQuantity() != null && !productItemUpdateRequest.getQuantity().equals(productItem.getQuantity())) {
            productItem.setQuantity(productItemUpdateRequest.getQuantity());
            changed = true;
        }

        if (productItemUpdateRequest.getPrice() != null && !productItemUpdateRequest.getPrice().equals(productItem.getPrice())) {
            productItem.setPrice(productItemUpdateRequest.getPrice());
            changed = true;
        }

//        Kiểm tra xem hình ảnh sản phẩm có bị thay đổi không, nếu có thì thay đổi và lưu vào database
        List<ProductImage> images = productItem.getImages();
        List<ProductImage> imagesUpdateRequest = productItemUpdateRequest.getImages();

        if (images != null) {
            if (images.size() != imagesUpdateRequest.size()) {
                productItem.setImages(imagesUpdateRequest);
                changed = true;
            }

            for (int i = 0; i < images.size(); i++) {
                ProductImage image = images.get(i);
                ProductImage imageUpdateRequest = imagesUpdateRequest.get(i);

                if (!imageUpdateRequest.getImageUrl().equals(image.getImageUrl())) {
                    image.setImageUrl(imageUpdateRequest.getImageUrl());
                    changed = true;
                }
            }
        }
//        end

//        Kiểm tra xem configuration sản phẩm có bị thay đổi không, nếu có thì thay đổi và lưu vào database
        List<ProductConfiguration> configurations = productConfigurationRepository.findByProductItem(productItem);
        List<ProductConfiguration> configurationsUpdateRequest = productItemUpdateRequest.getConfigurations();

        if (configurations != null) {
            if (configurations.size() != configurationsUpdateRequest.size()) {
                productItem.setConfigurations(configurationsUpdateRequest);
                changed = true;
            }

            for (int i = 0; i < configurations.size(); i++) {
                ProductConfiguration configuration = configurations.get(i);
                ProductConfiguration configurationUpdateRequest = configurationsUpdateRequest.get(i);

                String optionName = configurationUpdateRequest.getVariationOption().getVariation().getName();
                String optionValue = configurationUpdateRequest.getVariationOption().getValue();
                VariationOption option = variationOptionRepository.findByVariationNameAndValue(optionName, optionValue)
                                .orElseThrow(() -> new ResourceNotFoundException("Option [%s: %s] not found".formatted(optionName, optionValue)));

                if (!productConfigurationRepository.existsByProductItemAndVariationOption(productItem, option)) {
                    productConfigurationRepository.updateProductConfigurationById(configuration.getId(), option);
                    changed = true;
                }

                if (optionName.equals("ssd")) {
                    sku.append("/").append(optionValue);
                } else {
                    sku.append(" ").append(optionValue);
                }
            }
        }

        if (!sku.toString().equals(productItem.getSku())) {
            productItem.setSku(sku.toString());
            productItem.setSlug(slugify(sku.toString()));
        }

//        Không có data nào thay đổi
        if (!changed) {
            throw new RequestValidationException("No data changes found");
        }

        return productItemDTOMapper.toDTO(productItemRepository.save(productItem));
    }

    @Override
    public void deleteProductItem(Integer id) {
        ProductItem productItem = productItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id [%d] not found".formatted(id)));
        productItemRepository.delete(productItem);
    }
}
