package com.applemart.product.category;

import com.applemart.product.common.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/categories")
public class CategoryController {
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryDTO>>> getAllCategories() {
        List<CategoryDTO> categories = categoryService.getAllCategories();
        ApiResponse<List<CategoryDTO>> apiResponse = ApiResponse.<List<CategoryDTO>>builder()
                .status(HttpStatus.OK.value())
                .message("OK")
                .data(categories)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryDTO>> createCategory(@RequestBody @Valid CategoryDTO request) {
        ApiResponse<CategoryDTO> apiResponse = ApiResponse.<CategoryDTO>builder()
                .status(HttpStatus.CREATED.value())
                .message("Category created successfully")
                .data(categoryService.createCategory(request))
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable("id") Integer id) {
        categoryService.deleteCategoryById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryDTO>> updateCategory(@PathVariable("id") Integer id, @RequestBody @Valid CategoryDTO request) {
        ApiResponse<CategoryDTO> apiResponse = ApiResponse.<CategoryDTO>builder()
                .status(HttpStatus.OK.value())
                .message("Category updated successfully")
                .data(categoryService.updateCategory(id, request))
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
