package com.applemart.product;

import com.applemart.product.category.Category;
import com.applemart.product.category.CategoryRepository;
import com.applemart.product.exception.DuplicateResourceException;
import com.applemart.product.exception.RequestValidationException;
import com.applemart.product.exception.ResourceNotFoundException;
import com.applemart.product.productAttribute.ProductAttribute;
import com.applemart.product.productImage.ProductImage;
import com.applemart.product.productItem.ProductItem;
import com.applemart.product.response.PageResponse;
import com.applemart.product.variation.Variation;
import com.applemart.product.variationOption.VariationOption;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.applemart.product.utils.Slugify.slugify;

@AllArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ProductDTOMapper productDTOMapper;

    @Override
    @Transactional
    public PageResponse<ProductDTO> getAllProducts(int page, int size, String sort, String dir) {
        Sort sortBy = dir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sort).ascending()
                : Sort.by(sort).descending();

        Pageable pageable = PageRequest.of(page, size, sortBy);

        Page<Product> pages = productRepository.findAll(pageable);

        List<ProductDTO> productDTOs = pages.getContent()
                .stream()
                .map(productDTOMapper::toDTO)
                .toList();

        return PageResponse.<ProductDTO>builder()
                .currentPage(page)
                .pageSize(pages.getSize())
                .totalPages(pages.getTotalPages())
                .totalElements(pages.getTotalElements())
                .content(productDTOs)
                .build();
    }

    @Override
    @Transactional
    public ProductDTO getProductById(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id [%d] not found".formatted(id)));
        return productDTOMapper.toDTO(product);
    }

    @Override
    @Transactional
    public ProductDTO getProductBySlug(String slug) {
        Product product = productRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Product with slug [%s] not found".formatted(slug)));
        return productDTOMapper.toDTO(product);
    }

    @Override
    @Transactional
    public ProductDTO createProduct(ProductDTO productDTO) {

        Product newProduct = productDTOMapper.toEntity(productDTO);

        if (productRepository.existsByName(newProduct.getName())) {
            throw new DuplicateResourceException("Product [%s] already exists".formatted(newProduct.getName()));
        }

        newProduct.setSlug(slugify(newProduct.getName()));

        Category category = categoryRepository.findByName(productDTO.getCategory())
                .orElseThrow(() -> new ResourceNotFoundException("Category [%s] not found".formatted(productDTO.getCategory())));

        newProduct.setCategory(category);

        for (Variation variation : newProduct.getVariations()) {
            variation.setProduct(newProduct);
            for (VariationOption option : variation.getOptions()) {
                option.setVariation(variation);
            }
        }

        for (ProductImage productImage : newProduct.getImages()) {
            productImage.setProduct(newProduct);
        }



        Product savedProduct = productRepository.save(newProduct);

        return productDTOMapper.toDTO(savedProduct);
    }

    @Override
    @Transactional
    public ProductDTO updateProduct(Integer productId, ProductDTO request) {
        Product productUpdate = productDTOMapper.toEntity(request);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id [%d] not found".formatted(request.getId())));

        boolean changed = false;

//        Kiểm tra từng thuộc tính xem có thay đổi hay không
        if (productUpdate.getName() != null && !productUpdate.getName().equals(product.getName())) {
            product.setName(productUpdate.getName());
            product.setSlug(slugify(productUpdate.getName()));
            changed = true;
        }

        if (productUpdate.getDescription() != null && !productUpdate.getDescription().equals(product.getDescription())) {
            product.setDescription(productUpdate.getDescription());
            changed = true;
        }

        if (productUpdate.getThumbnailUrl() != null && !productUpdate.getThumbnailUrl().equals(product.getThumbnailUrl())) {
            product.setThumbnailUrl(productUpdate.getThumbnailUrl());
            changed = true;
        }
//  end

        Category category = categoryRepository.findByName(request.getCategory())
                .orElseThrow(() -> new ResourceNotFoundException("Category [%s] not found".formatted(request.getCategory())));

        if (category != null && !category.getName().equals(product.getCategory().getName())) {
            product.setCategory(category);
            changed = true;
        }

//        Nếu không có gì thay đổi (giống với data cũ) thì báo lỗi
        if (!changed) {
            throw new RequestValidationException("No data changes found");
        }

        Product updatedProduct = productRepository.save(product);

        return productDTOMapper.toDTO(updatedProduct);
    }

    @Override
    public void deleteProduct(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product [%d] not found".formatted(id)));
        productRepository.delete(product);
    }
}
