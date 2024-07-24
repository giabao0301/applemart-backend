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
            throw new DuplicateResourceException("Product [%s] already exists".formatted(newProduct.getName()));
        }

        newProduct.setSlug(Slugify.slugify(newProduct.getName()));

        Category category = categoryRepository.findByUrlKey(productDTO.getCategory())
                .orElseThrow(() -> new ResourceNotFoundException("Category [%s] not found".formatted(productDTO.getCategory())));

        newProduct.setCategory(category);

        Product savedProduct = productRepository.save(newProduct);
        return productDTOMapper.toDTO(savedProduct);
    }

    @Override
    public ProductDTO updateProduct(Integer productId, ProductDTO request) {
        Product productUpdate = productDTOMapper.toEntity(request);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id [%d] not found".formatted(request.getId())));

        boolean changed = false;

//        Kiểm tra từng thuộc tính xem có thay đổi hay không
        if (productUpdate.getName() != null && !productUpdate.getName().equals(product.getName())) {
            product.setName(productUpdate.getName());
            product.setSlug(Slugify.slugify(productUpdate.getName()));
            changed = true;
        }

        if (productUpdate.getDescription() != null && !productUpdate.getDescription().equals(product.getDescription())) {
            product.setDescription(productUpdate.getDescription());
            changed = true;
        }

        if (productUpdate.getImageUrl() != null && !productUpdate.getImageUrl().equals(product.getImageUrl())) {
            product.setImageUrl(productUpdate.getImageUrl());
            changed = true;
        }
//  end

        Category category = categoryRepository.findByUrlKey(request.getCategory())
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
