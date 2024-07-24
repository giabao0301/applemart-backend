package com.applemart.backend.category;

import com.applemart.backend.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/${api-prefix.path}/categories")
public class CategoryController {
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
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

    @DeleteMapping("/{urlKey}")
    public ResponseEntity<?> deleteCategory(@PathVariable String urlKey) {
        categoryService.deleteCategoryByUrlKey(urlKey);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryDTO>> updateCategory(@PathVariable Integer id, @RequestBody @Valid CategoryDTO request) {
        ApiResponse<CategoryDTO> apiResponse = ApiResponse.<CategoryDTO>builder()
                .status(HttpStatus.OK.value())
                .message("Category updated successfully")
                .data(categoryService.updateCategory(id, request))
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
