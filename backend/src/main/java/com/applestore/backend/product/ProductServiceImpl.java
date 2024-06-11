package com.applestore.backend.product;

import com.applestore.backend.exception.DuplicateResourceException;
import com.applestore.backend.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    private ProductDTOMapper productDTOMapper;

    @Override
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAllProducts();

        return products
                .stream()
                .map(productDTOMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO getProductById(int id) {
        Product product = productRepository.findProductById(id).orElseThrow(()-> new ResourceNotFoundException("Product [%s] not found".formatted(id)));
        return productDTOMapper.toDTO(product);
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product newProduct = productDTOMapper.toEntity(productDTO);

        if (productRepository.existsByName(newProduct.getName())) {
            throw new DuplicateResourceException("product already exists");
        }

        return productDTOMapper.toDTO(newProduct);
    }
}
