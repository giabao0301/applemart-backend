package com.applemart.backend.category;

import com.applemart.backend.exception.DuplicateResourceException;
import com.applemart.backend.exception.RequestValidationException;
import com.applemart.backend.exception.ResourceNotFoundException;
import com.applemart.backend.product.Product;
import com.applemart.backend.product.ProductRepository;
import com.applemart.backend.product.variation.Variation;
import com.applemart.backend.product.variation.VariationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private final ProductRepository productRepository;
    private final VariationRepository variationRepository;
    private CategoryRepository categoryRepository;
    private CategoryDTOMapper categoryDTOMapper;

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories
                .stream()
                .map(category -> categoryDTOMapper.toDTO(category))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO request) {
        Category category = categoryDTOMapper.toEntity(request);

        if (categoryRepository.existsByUrlKeyAndName(category.getUrlKey(), category.getName())) {
             throw new DuplicateResourceException("Category already exists");
        }

        category = categoryRepository.save(category);
        return categoryDTOMapper.toDTO(category);
    }

    @Override
    public CategoryDTO updateCategory(Integer id, CategoryDTO request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        Category categoryToUpdate = categoryDTOMapper.toEntity(request);

        boolean changed = false;

        if (categoryToUpdate.getName() != null && !categoryToUpdate.getName().equals(category.getName())) {
            category.setName(categoryToUpdate.getName());
            changed = true;
        }

        if (categoryToUpdate.getUrlKey() != null && !categoryToUpdate.getUrlKey().equals(category.getUrlKey())) {
            category.setUrlKey(categoryToUpdate.getUrlKey());
            changed = true;
        }

        if (categoryToUpdate.getImageUrl() != null && !categoryToUpdate.getImageUrl().equals(category.getImageUrl())) {
            category.setImageUrl(categoryToUpdate.getImageUrl());
            changed = true;
        }

        if (!changed) {
            throw new RequestValidationException("No data changes found");
        }

        category = categoryRepository.save(category);
        return categoryDTOMapper.toDTO(category);
    }

    @Override
    public void deleteCategoryByUrlKey(String urlKey) {
        Category category = categoryRepository.findByUrlKey(urlKey)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        Category uncategorized = categoryRepository.findByUrlKey("uncategorized")
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        List<Product> products = productRepository.findByCategory(category);
        for (Product product : products) {
            category.deleteProduct(product);
            product.setCategory(uncategorized);
            productRepository.save(product);
        }

        categoryRepository.delete(category);
    }
}
