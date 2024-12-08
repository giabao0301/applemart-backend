package com.applemart.product;

import com.applemart.product.category.Category;
import com.applemart.product.category.CategoryRepository;
import com.applemart.product.exception.DuplicateResourceException;
import com.applemart.product.exception.RequestValidationException;
import com.applemart.product.exception.ResourceNotFoundException;
import com.applemart.product.productImage.ProductImage;
import com.applemart.product.common.PageResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.applemart.product.utils.SlugConverter.slugify;


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
                .orElseThrow(() -> new ResourceNotFoundException("Product with name [%s] not found".formatted(slug)));
        return productDTOMapper.toDTO(product);
    }

    @Override
    @Transactional
    public List<ProductDTO> getProductByCategory(String categoryUrlKey) {

        Category category = categoryRepository.findByUrlKey(categoryUrlKey)
                .orElseThrow(() -> new ResourceNotFoundException("Category [%s] not found".formatted(categoryUrlKey)));

        List<Product> products = productRepository.findByCategory(category);

        return products
                .stream()
                .map(productDTOMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional
    public ProductDTO createProduct(ProductDTO productDTO) {

        Product newProduct = productDTOMapper.toEntity(productDTO);

        if (productRepository.existsByName(newProduct.getName())) {
            throw new DuplicateResourceException("Product [%s] already exists".formatted(newProduct.getName()));
        }

        newProduct.setSlug(slugify(newProduct.getName()));

        Category category = categoryRepository.findByUrlKey(productDTO.getCategory())
                .orElseThrow(() -> new ResourceNotFoundException("Category [%s] not found".formatted(productDTO.getCategory())));

        newProduct.setCategory(category);

        for (ProductImage image : newProduct.getImages()) {
            image.setProduct(newProduct);
        }

        Product savedProduct = productRepository.save(newProduct);

        return productDTOMapper.toDTO(savedProduct);
    }

    @Override
    @Transactional
    public ProductDTO updateProduct(Integer id, ProductDTO request) {
        Product productUpdateRequest = productDTOMapper.toEntity(request);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id [%d] not found".formatted(id)));

        boolean changed = false;

//        Kiểm tra từng thuộc tính xem có thay đổi hay không
        if (productUpdateRequest.getName() != null && !productUpdateRequest.getName().equals(product.getName())) {
            product.setName(productUpdateRequest.getName());
            product.setSlug(slugify(productUpdateRequest.getName()));
            changed = true;
        }

        if (productUpdateRequest.getLowestPrice() != null && !productUpdateRequest.getLowestPrice().equals(product.getLowestPrice())) {
            product.setLowestPrice(productUpdateRequest.getLowestPrice());
            changed = true;
        }

        if (productUpdateRequest.getDescription() != null && !productUpdateRequest.getDescription().equals(product.getDescription())) {
            product.setDescription(productUpdateRequest.getDescription());
            changed = true;
        }

        if (productUpdateRequest.getThumbnailUrl() != null && !productUpdateRequest.getThumbnailUrl().equals(product.getThumbnailUrl())) {
            product.setThumbnailUrl(productUpdateRequest.getThumbnailUrl());
            changed = true;
        }
//  end

        Category category = categoryRepository.findByUrlKey(request.getCategory())
                .orElseThrow(() -> new ResourceNotFoundException("Category [%s] not found".formatted(request.getCategory())));

        if (category != null && !category.getName().equals(product.getCategory().getName())) {
            product.setCategory(category);
            changed = true;
        }

        List<ProductImage> imagesUpdateRequest = productUpdateRequest.getImages();
        List<ProductImage> images = product.getImages();


        if (images.size() > imagesUpdateRequest.size()) {
            images.removeIf(existingImage ->
                    imagesUpdateRequest
                            .stream()
                            .noneMatch(image -> image.getUrl().equals(existingImage.getUrl()))
            );
            changed = true;
        }

        if (images.size() < imagesUpdateRequest.size()) {
            for (ProductImage imageUpdate : imagesUpdateRequest) {
                boolean isNewImage = images
                        .stream()
                        .noneMatch(image -> image.getUrl().equals(imageUpdate.getUrl()));

                if (isNewImage) {
                    imageUpdate.setProduct(product);
                    images.add(imageUpdate);
                    changed = true;
                }
            }
        }

        if (images.size() == imagesUpdateRequest.size()) {
            for (int i = 0; i < images.size(); i++) {
                ProductImage image = images.get(i);
                ProductImage imageUpdate = imagesUpdateRequest.get(i);

                if (imageUpdate != null && !image.getUrl().equals(imageUpdate.getUrl())) {
                    image.setUrl(imageUpdate.getUrl());
                    changed = true;
                }
            }
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
