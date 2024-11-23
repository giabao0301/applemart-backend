package com.applemart.product.productItem;

import com.applemart.product.Product;
import com.applemart.product.ProductRepository;
import com.applemart.product.exception.DuplicateResourceException;
import com.applemart.product.exception.RequestValidationException;
import com.applemart.product.exception.ResourceNotFoundException;
import com.applemart.product.productAttribute.ProductAttribute;
import com.applemart.product.productConfiguration.*;
import com.applemart.product.variationOption.VariationOption;
import com.applemart.product.variationOption.VariationOptionRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.applemart.product.utils.SlugConverter.slugify;


@AllArgsConstructor
@Service
public class ProductItemServiceImpl implements ProductItemService {
    private static final Logger log = LoggerFactory.getLogger(ProductItemServiceImpl.class);
    private final ProductRepository productRepository;
    private final ProductItemRepository productItemRepository;
    private final ProductItemDTOMapper productItemDTOMapper;
    private final VariationOptionRepository variationOptionRepository;
    private final ProductConfigurationRepository productConfigurationRepository;
    private final ProductConfigurationDTOMapper productConfigurationDTOMapper;

    @Override
    @Transactional
    public ProductItemDTO getProductItemBySlug(String slug) {
        ProductItem productItem = productItemRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Product item with slug [%s] not found".formatted(slug)));

        List<ProductConfigurationDTO> configurations = productItem.getConfigurations().stream()
                .map(productConfigurationDTOMapper::toDTO)
                .toList();

        ProductItemDTO productItemDTO = productItemDTOMapper.toDTO(productItem);
        productItemDTO.setConfigurations(configurations);

        return productItemDTO;
    }

    @Override
    @Transactional
    public ProductItemDTO getProductItemById(Integer id) {
        ProductItem productItem = productItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product item with id [%d] not found".formatted(id)));

        List<ProductConfigurationDTO> configurations = productItem.getConfigurations().stream()
                .map(productConfigurationDTOMapper::toDTO)
                .toList();

        ProductItemDTO productItemDTO = productItemDTOMapper.toDTO(productItem);
        productItemDTO.setConfigurations(configurations);

        return productItemDTO;
    }

    @Override
    @Transactional
    public List<ProductItemDTO> getProductItemsByProductId(Integer productId) {
        List<ProductItem> productItems = productItemRepository.findByProductId(productId);

        List<ProductItemDTO> productItemDTOs = productItems
                .stream()
                .map(productItemDTOMapper::toDTO)
                .toList();

        for (int i = 0; i < productItemDTOs.size(); i++) {
            List<ProductConfigurationDTO> productConfigurationDTOs = productItems.get(i).getConfigurations()
                    .stream()
                    .map(productConfigurationDTOMapper::toDTO)
                    .toList();

            productItemDTOs.get(i).setConfigurations(productConfigurationDTOs);
        }

        return productItemDTOs;
    }

    @Override
    @Transactional
    public ProductItemDTO createProductItem(ProductItemDTO request) {
        ProductItem newProductItem = productItemDTOMapper.toEntity(request);

//        Tìm product bằng tên rồi gán cho product item
        Product product = productRepository.findByName(request.getProductName())
                .orElseThrow(() -> new ResourceNotFoundException("Product [%s] not found".formatted(request.getProductName())));

        newProductItem.setProduct(product);

        StringBuilder name = new StringBuilder(product.getName());

        for (ProductConfiguration configuration : newProductItem.getConfigurations()) {

            String optionName = configuration.getVariationOption().getVariation().getName();
            String optionValue = configuration.getVariationOption().getValue();
            Integer categoryId = product.getCategory().getId();

            VariationOption option = variationOptionRepository.findByCategoryIdAndVariationNameAndValue(categoryId, optionName, optionValue)
                    .orElseThrow(() -> new ResourceNotFoundException("Variation [%s: %s] option not found".formatted(optionName, optionValue)));

            configuration.setId(new ProductConfigurationId(newProductItem.getId(), option.getId()));
            configuration.setVariationOption(option);
            configuration.setProductItem(newProductItem);

            if (optionName.equals("Ổ cứng") || optionName.equals("Dung lượng lưu trữ")) {
                name.append("/").append(optionValue);
            } else {
                name.append(" ").append(optionValue);
            }
        }

        if (productItemRepository.existsByName(name.toString())) {
            throw new DuplicateResourceException("Product item [%s] already exists".formatted(name));
        }

//      Tạo name từ tên sản phẩm và các option
        newProductItem.setName(name.toString());

//      Tự tạo slug từ tên của name
        newProductItem.setSlug(slugify(newProductItem.getName()));

        for (ProductAttribute productAttribute : newProductItem.getAttributes()) {
            productAttribute.setProductItem(newProductItem);
        }

        return productItemDTOMapper.toDTO(productItemRepository.save(newProductItem));
    }

    @Override
    @Transactional
    public ProductItemDTO updateProductItem(Integer id, ProductItemDTO request) {
//        ProductItem được request để update
        ProductItem productItemUpdateRequest = productItemDTOMapper.toEntity(request);

//        Product Item ở trong database
        ProductItem productItem = productItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product item with id [%d] not found".formatted(id)));

        //        Product của productItem
        Product product = productRepository.findByName(request.getProductName())
                .orElseThrow(() -> new ResourceNotFoundException("Product [%s] not found".formatted(request.getProductName())));

        productItem.setProduct(product);


        boolean changed = false;

        if (productItemUpdateRequest.getQuantityInStock() != null && !productItemUpdateRequest.getQuantityInStock().equals(productItem.getQuantityInStock())) {
            productItem.setQuantityInStock(productItemUpdateRequest.getQuantityInStock());
            changed = true;
        }

        if (productItemUpdateRequest.getPrice() != null && !productItemUpdateRequest.getPrice().equals(productItem.getPrice())) {
            productItem.setPrice(productItemUpdateRequest.getPrice());
            changed = true;
        }

        if (productItemUpdateRequest.getImageUrl() != null && !productItemUpdateRequest.getImageUrl().equals(productItem.getImageUrl())) {
            productItem.setImageUrl(productItemUpdateRequest.getImageUrl());
            changed = true;
        }

//        Kiểm tra xem configuration sản phẩm có bị thay đổi không, nếu có thì thay đổi và lưu vào database
        List<ProductConfiguration> configurationsUpdateRequest = productItemUpdateRequest.getConfigurations();
        List<ProductConfiguration> configurations = productItem.getConfigurations();

        StringBuilder name = new StringBuilder(product.getName());

        if (configurations.size() > configurationsUpdateRequest.size()) {
            configurations.removeIf(existingConfig ->
                    configurationsUpdateRequest
                            .stream()
                            .noneMatch(config ->
                                    config.getVariationOption().getVariation().getName().equals(existingConfig.getVariationOption().getVariation().getName())
                                            && config.getVariationOption().getValue().equals(existingConfig.getVariationOption().getValue())
                            )
            );
            changed = true;
        }

        if (configurations.size() < configurationsUpdateRequest.size()) {
            for (ProductConfiguration configurationUpdate : configurationsUpdateRequest) {
                boolean isNewConfig = configurations
                        .stream()
                        .noneMatch(config ->
                                config.getVariationOption().getVariation().getName().equals(configurationUpdate.getVariationOption().getVariation().getName())
                                        && config.getVariationOption().getValue().equals(configurationUpdate.getVariationOption().getValue())
                        );
                if (isNewConfig) {
                    configurationUpdate.setProductItem(productItem);
                    String optionName = configurationUpdate.getVariationOption().getVariation().getName();
                    String optionValue = configurationUpdate.getVariationOption().getValue();
                    Integer categoryId = product.getCategory().getId();
                    VariationOption option = variationOptionRepository.findByCategoryIdAndVariationNameAndValue(categoryId, optionName, optionValue)
                            .orElseThrow(() -> new ResourceNotFoundException("Option [%s: %s] not found".formatted(optionName, optionValue)));

                    configurationUpdate.setId(new ProductConfigurationId(productItem.getId(), option.getId()));
                    configurationUpdate.setVariationOption(option);
                    configurations.add(configurationUpdate);
                    changed = true;
                }
            }
        }


        if (configurations.size() == configurationsUpdateRequest.size()) {
            for (int i = 0; i < configurations.size(); i++) {
                ProductConfiguration configuration = configurations.get(i);
                ProductConfiguration configurationUpdateRequest = configurationsUpdateRequest.get(i);

                String optionName = configurationUpdateRequest.getVariationOption().getVariation().getName();
                String optionValue = configurationUpdateRequest.getVariationOption().getValue();
                Integer categoryId = product.getCategory().getId();

                VariationOption option = variationOptionRepository.findByCategoryIdAndVariationNameAndValue(categoryId, optionName, optionValue)
                        .orElseThrow(() -> new ResourceNotFoundException("Option [%s: %s] not found".formatted(optionName, optionValue)));

                if (!productConfigurationRepository.existsByProductItemAndVariationOption(productItem, option)) {
                    productConfigurationRepository.updateProductConfigurationById(configuration.getId(), option);
                    changed = true;
                }

                if (optionName.equals("Ổ cứng") || optionName.equals("Dung lượng lưu trữ")) {
                    name.append("/").append(optionValue);
                } else {
                    name.append(" ").append(optionValue);
                }
            }
        }

        if (!name.toString().equals(productItem.getName())) {
            productItem.setName(name.toString());
            productItem.setSlug(slugify(name.toString()));
        }

        List<ProductAttribute> attributesUpdateRequest = productItemUpdateRequest.getAttributes();
        List<ProductAttribute> attributes = productItem.getAttributes();

        if (attributes.size() > attributesUpdateRequest.size()) {
            attributes.removeIf(existingAttribute ->
                    attributesUpdateRequest
                            .stream()
                            .noneMatch(attribute -> attribute.getValue().equals(existingAttribute.getValue()))
            );
            changed = true;
        }

        if (attributes.size() < attributesUpdateRequest.size()) {
            for (ProductAttribute attributeUpdate : attributesUpdateRequest) {
                boolean isNewAttribute = attributes
                        .stream()
                        .noneMatch(attribute -> attribute.getValue().equals(attributeUpdate.getValue()));

                if (isNewAttribute) {
                    attributeUpdate.setProductItem(productItem);
                    attributes.add(attributeUpdate);
                    changed = true;
                }
            }
        } else {
            for (int i = 0; i < attributes.size(); i++) {
                ProductAttribute attribute = attributes.get(i);
                ProductAttribute attributeUpdateRequest = attributesUpdateRequest.get(i);

                if (attributeUpdateRequest.getValue() != null && !attributeUpdateRequest.getValue().equals(attribute.getValue())) {
                    attribute.setValue(attributeUpdateRequest.getValue());
                    changed = true;
                }
            }
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
