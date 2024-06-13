package com.applestore.backend.product;

import com.applestore.backend.category.Category;
import com.applestore.backend.category.CategoryRepository;
import com.applestore.backend.exception.DuplicateResourceException;
import com.applestore.backend.exception.ResourceNotFoundException;
import com.applestore.backend.variation.VariationRepository;
import com.applestore.backend.utils.Slugify;
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
    public ProductDTO getProductById(int id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product [%d] not found".formatted(id)));
        return productDTOMapper.toDTO(product);
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {

        Product newProduct = productDTOMapper.toEntity(productDTO);

        if (productRepository.existsByName(newProduct.getName())) {
            throw new DuplicateResourceException("product already exists");
        }

        newProduct.setSlug(Slugify.slugify(newProduct.getName()));


        Category category = categoryRepository.findByUrlKey(productDTO.getCategory());
        if (category != null) {
            newProduct.setCategory(category);
        } else {
            throw new ResourceNotFoundException("Category [%s] not found".formatted(productDTO.getCategory()));
        }

        Product productResponse = productRepository.save(newProduct);
        return productDTOMapper.toDTO(productResponse);
    }

    @Override
    public ProductDTO updateProduct(int productId, ProductDTO productDTO) {
        Product requestedProduct = productDTOMapper.toEntity(productDTO);
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product [%d] not found".formatted(productDTO.getId())));

        product.setName(requestedProduct.getName());
        product.setDescription(requestedProduct.getDescription());
        product.setImageUrl(requestedProduct.getImageUrl());

        product.setSlug(Slugify.slugify(requestedProduct.getName()));

        Category category = categoryRepository.findByUrlKey(productDTO.getCategory());
        if (category != null) {
            product.setCategory(category);
        } else {
            throw new ResourceNotFoundException("Category [%s] not found".formatted(productDTO.getCategory()));
        }

        Product updatedProduct = productRepository.save(product);

        return productDTOMapper.toDTO(updatedProduct);
    }

    @Override
    public void deleteProductById(int id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product [%d] not found".formatted(id)));
        productRepository.delete(product);
    }
}
