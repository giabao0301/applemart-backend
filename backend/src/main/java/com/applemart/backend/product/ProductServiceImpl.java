package com.applemart.backend.product;

import com.applemart.backend.exception.RequestValidationException;
import com.applemart.backend.utils.Slugify;
import com.applemart.backend.category.Category;
import com.applemart.backend.category.CategoryRepository;
import com.applemart.backend.exception.DuplicateResourceException;
import com.applemart.backend.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ProductDTOMapper productDTOMapper;


    @Override
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();

        return products
                .stream()
                .map(productDTOMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO getProduct(Integer id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product [%d] not found".formatted(id)));
        return productDTOMapper.toDTO(product);
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {

        Product newProduct = productDTOMapper.toEntity(productDTO);

        if (productRepository.existsByName(newProduct.getName())) {
            throw new DuplicateResourceException("Product already exists");
        }

        newProduct.setSlug(Slugify.slugify(newProduct.getName()));


        Category category = categoryRepository.findByUrlKey(productDTO.getCategory());
        if (category != null) {
            newProduct.setCategory(category);
        } else {
            throw new ResourceNotFoundException("Category [%s] not found".formatted(productDTO.getCategory()));
        }

        Product savedProduct = productRepository.save(newProduct);
        return productDTOMapper.toDTO(savedProduct);
    }

    @Override
    public ProductDTO updateProduct(Integer productId, ProductDTO productDTO) {
        Product requestedProduct = productDTOMapper.toEntity(productDTO);
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product [%d] not found".formatted(productDTO.getId())));

        boolean changes = false;

        if (requestedProduct.getName() != null && !requestedProduct.getName().equals(product.getName())) {
            product.setName(requestedProduct.getName());
            product.setSlug(Slugify.slugify(requestedProduct.getName()));
            changes = true;
        }

        if (requestedProduct.getDescription() != null && !requestedProduct.getDescription().equals(product.getDescription())) {
            product.setDescription(requestedProduct.getDescription());
            changes = true;
        }

        if(requestedProduct.getImageUrl() != null && !requestedProduct.getImageUrl().equals(product.getImageUrl())) {
            product.setImageUrl(requestedProduct.getImageUrl());
            changes = true;
        }


        Category category = categoryRepository.findByUrlKey(productDTO.getCategory());
        if (category != null) {
            if (category.getName().equals(product.getCategory().getName())) {
                product.setCategory(category);
                changes = true;
            }
        } else {
            throw new ResourceNotFoundException("Category [%s] not found".formatted(productDTO.getCategory()));
        }

        if (!changes) {
            throw new RequestValidationException("No data changes found");
        }

        Product updatedProduct = productRepository.save(product);

        return productDTOMapper.toDTO(updatedProduct);
    }

    @Override
    public void deleteProduct(Integer id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product [%d] not found".formatted(id)));
        productRepository.delete(product);
    }
}
