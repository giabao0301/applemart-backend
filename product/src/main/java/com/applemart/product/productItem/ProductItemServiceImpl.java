package com.applemart.product.productItem;

import com.applemart.product.Product;
import com.applemart.product.ProductDTO;
import com.applemart.product.ProductRepository;
import com.applemart.product.exception.DuplicateResourceException;
import com.applemart.product.exception.RequestValidationException;
import com.applemart.product.exception.ResourceNotFoundException;
import com.applemart.product.productAttribute.ProductAttribute;
import com.applemart.product.productConfiguration.*;
import com.applemart.product.productImage.ProductImage;
import com.applemart.product.response.PageResponse;
import com.applemart.product.variation.Variation;
import com.applemart.product.variationOption.VariationOption;
import com.applemart.product.variationOption.VariationOptionRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.applemart.product.utils.Slugify.slugify;


@AllArgsConstructor
@Service
public class ProductItemServiceImpl implements ProductItemService {
    private final ProductRepository productRepository;
    private final ProductItemRepository productItemRepository;
    private final ProductItemDTOMapper productItemDTOMapper;
    private final VariationOptionRepository variationOptionRepository;
    private final ProductConfigurationRepository productConfigurationRepository;
    private final ProductConfigurationDTOMapper productConfigurationDTOMapper;

    @Override
    @Transactional
    public List<ProductItemDTO> getProductItemsBySlug(String slug) {
        List<ProductItem> productItems = productItemRepository.findBySlugStartsWith(slug);

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

        StringBuilder sku = new StringBuilder(product.getName());

        for (ProductConfiguration configuration : newProductItem.getConfigurations()) {

            String optionName = configuration.getVariationOption().getVariation().getName();
            String optionValue = configuration.getVariationOption().getValue();


            VariationOption variationOption = variationOptionRepository.findByProductIdAndVariationNameAndValue(product.getId(), optionName, optionValue)
                    .orElseThrow(() -> new ResourceNotFoundException("Variation [%s: %s] option not found".formatted(optionName, optionValue)));

            configuration.setId(new ProductConfigurationId(newProductItem.getId(), variationOption.getId()));
            configuration.setVariationOption(variationOption);
            configuration.setProductItem(newProductItem);

            if (optionName.equals("Ổ cứng") || optionName.equals("Dung lượng lưu trữ")) {
                sku.append("/").append(optionValue);
            } else {
                sku.append(" ").append(optionValue);
            }
        }

        if (productItemRepository.existsBySku(sku.toString())) {
            throw new DuplicateResourceException("Product item [%s] already exists".formatted(sku));
        }

//      Tạo sku từ tên sản phẩm và các option
        newProductItem.setSku(sku.toString());

//      Tự tạo slug từ tên của sku
        newProductItem.setSlug(slugify(newProductItem.getSku()));

        for (ProductAttribute productAttribute: newProductItem.getAttributes()) {
            productAttribute.setProductItem(newProductItem);
        }

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
                VariationOption option = variationOptionRepository.findByProductIdAndVariationNameAndValue(product.getId(), optionName, optionValue)
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
