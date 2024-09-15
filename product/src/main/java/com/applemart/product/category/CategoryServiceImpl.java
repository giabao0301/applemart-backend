package com.applemart.product.category;

import com.applemart.product.Product;
import com.applemart.product.ProductRepository;
import com.applemart.product.exception.DuplicateResourceException;
import com.applemart.product.exception.RequestValidationException;
import com.applemart.product.exception.ResourceNotFoundException;
import com.applemart.product.variation.Variation;
import com.applemart.product.variation.VariationRepository;
import com.applemart.product.variationOption.VariationOption;
import com.applemart.product.variationOption.VariationOptionRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.applemart.product.utils.SlugConverter.slugify;


@AllArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryDTOMapper categoryDTOMapper;
    private final VariationRepository variationRepository;
    private final VariationOptionRepository variationOptionRepository;

    @Override
    @Transactional
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories
                .stream()
                .map(categoryDTOMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional
    public CategoryDTO createCategory(CategoryDTO request) {
        Category category = categoryDTOMapper.toEntity(request);

        category.setUrlKey(slugify(request.getName()));

        if (categoryRepository.existsByUrlKeyAndName(category.getUrlKey(), category.getName())) {
            throw new DuplicateResourceException("Category [%s] already exists".formatted(category.getName()));
        }

        if (request.getParentCategory() != null) {
            Category parentCategory = categoryRepository.findByName(request.getParentCategory())
                    .orElseThrow(() -> new ResourceNotFoundException("Parent category [%s] not found".formatted(request.getParentCategory())));

            category.setParentCategory(parentCategory);
        }

        for (Variation variation : category.getVariations()) {
            variation.setCategory(category);
            for (VariationOption option : variation.getOptions()) {
                option.setVariation(variation);
            }
        }

        return categoryDTOMapper.toDTO(categoryRepository.save(category));
    }

    @Override
    @Transactional
    public CategoryDTO updateCategory(Integer id, CategoryDTO request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id [%d] not found".formatted(id)));

        Category categoryUpdateRequest = categoryDTOMapper.toEntity(request);

        boolean changed = false;

        if (categoryUpdateRequest.getName() != null && !categoryUpdateRequest.getName().equals(category.getName())) {
            category.setName(categoryUpdateRequest.getName());
            changed = true;
        }

        if (categoryUpdateRequest.getUrlKey() != null && !categoryUpdateRequest.getUrlKey().equals(category.getUrlKey())) {
            category.setUrlKey(categoryUpdateRequest.getUrlKey());
            changed = true;
        }

        if (categoryUpdateRequest.getThumbnailUrl() != null && !categoryUpdateRequest.getThumbnailUrl().equals(category.getThumbnailUrl())) {
            category.setThumbnailUrl(categoryUpdateRequest.getThumbnailUrl());
            changed = true;
        }

        List<Variation> variations = category.getVariations();
        List<Variation> variationsUpdateRequest = categoryUpdateRequest.getVariations();

//      So sánh 2 variation và từng option trong variation
        if (variations.size() != variationsUpdateRequest.size()) {
            category.getVariations().clear();
            variationRepository.deleteAllByCategoryId(category.getId());
            for (Variation variation : variationsUpdateRequest) {
                category.addVariation(variation);
                for (VariationOption option : variation.getOptions()) {
                    option.setVariation(variation);
                }
            }
            changed = true;
        } else {
            for (int i = 0; i < variations.size(); i++) {
                Variation variation = variations.get(i);
                Variation variationUpdateRequest = variationsUpdateRequest.get(i);

                if (variationUpdateRequest.getName() != null && !variationUpdateRequest.getName().equals(variation.getName())) {
                    variation.setName(variationUpdateRequest.getName());
                    changed = true;
                }

                List<VariationOption> options = variation.getOptions();
                List<VariationOption> optionsUpdateRequest = variationUpdateRequest.getOptions();
                if (options.size() != optionsUpdateRequest.size()) {
                    variation.getOptions().clear();
                    variationOptionRepository.deleteAllByVariationId(variation.getId());
                    for (VariationOption option : optionsUpdateRequest) {
                        variation.addVariationOption(option);
                    }
                    changed = true;
                } else {
                    for (int j = 0; j < options.size(); j++) {
                        VariationOption option = options.get(j);
                        VariationOption optionUpdateRequest = optionsUpdateRequest.get(j);
                        if (option.getValue() != null && !option.getValue().equals(optionUpdateRequest.getValue())) {
                            option.setValue(optionUpdateRequest.getValue());
                            changed = true;
                        }

                        if (option.getImageUrl() != null && !option.getImageUrl().equals(optionUpdateRequest.getImageUrl())) {
                            option.setImageUrl(optionUpdateRequest.getImageUrl());
                            changed = true;
                        }
                    }
                }
            }
        }


        if (!changed) {
            throw new RequestValidationException("No data changes found");
        }

        return categoryDTOMapper.toDTO(categoryRepository.save(category));
    }

    @Override
    public void deleteCategoryByUrlKey(String urlKey) {
        Category category = categoryRepository.findByUrlKey(urlKey)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        Category uncategorized = categoryRepository.findByUrlKey("uncategorized")
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        List<Product> products = productRepository.findByCategory(category);
        for (Product product : products) {
            category.removeProduct(product);
            product.setCategory(uncategorized);
            productRepository.save(product);
        }

        categoryRepository.delete(category);
    }
}
