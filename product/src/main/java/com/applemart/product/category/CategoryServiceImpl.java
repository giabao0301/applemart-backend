package com.applemart.product.category;

import com.applemart.product.Product;
import com.applemart.product.ProductRepository;
import com.applemart.product.exception.DuplicateResourceException;
import com.applemart.product.exception.RequestValidationException;
import com.applemart.product.exception.ResourceNotFoundException;
import com.applemart.product.productAttribute.ProductAttribute;
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
        if (variations.size() > variationsUpdateRequest.size()) {
            variations.removeIf(existingVariation ->
                    variationsUpdateRequest
                            .stream()
                            .noneMatch(variation -> variation.getName().equals(existingVariation.getName()))
            );
            changed = true;
        } else if (variations.size() < variationsUpdateRequest.size()) {
            for (Variation variationUpdate : variationsUpdateRequest) {
                boolean isNewOption = variations
                        .stream()
                        .noneMatch(option -> option.getName().equals(variationUpdate.getName()));

                if (isNewOption) {
                    variationUpdate.setCategory(category);

                    for (VariationOption optionUpdate : variationUpdate.getOptions()) {
                        optionUpdate.setVariation(variationUpdate);
                    }

                    variations.add(variationUpdate);
                    changed = true;
                }


            }
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
                if (options.size() > optionsUpdateRequest.size()) {
                    options.removeIf(existingOption ->
                            optionsUpdateRequest
                                    .stream()
                                    .noneMatch(option -> option.getValue().equals(existingOption.getValue()) && (existingOption.getImageUrl() == null || option.getImageUrl().equals(existingOption.getImageUrl())))
                    );
                    changed = true;
                }

                if (options.size() < optionsUpdateRequest.size()) {
                    for (VariationOption optionUpdate : optionsUpdateRequest) {
                        boolean isNewOption = options
                                .stream()
                                .noneMatch(option -> option.getValue().equals(optionUpdate.getValue()) && (optionUpdate.getImageUrl() == null || option.getImageUrl().equals(optionUpdate.getImageUrl())));

                        if (isNewOption) {
                            optionUpdate.setVariation(variation);
                            options.add(optionUpdate);
                            changed = true;
                        }
                    }
                } else {
                    for (int j = 0; j < optionsUpdateRequest.size(); j++) {
                        VariationOption option = options.get(j);
                        VariationOption optionUpdateRequest = optionsUpdateRequest.get(j);

                        if (optionUpdateRequest.getImageUrl() != null && !optionUpdateRequest.getImageUrl().equals(option.getImageUrl())) {
                            option.setImageUrl(optionUpdateRequest.getImageUrl());
                            changed = true;
                        }

                        if (optionUpdateRequest.getValue() != null && !optionUpdateRequest.getValue().equals(option.getValue())) {
                            option.setValue(optionUpdateRequest.getValue());
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
    public void deleteCategoryById(Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        List<Product> products = productRepository.findByCategory(category);
        for (Product product : products) {
            category.removeProduct(product);
            productRepository.save(product);
        }

        categoryRepository.deleteCategoryById(id);
    }
}
