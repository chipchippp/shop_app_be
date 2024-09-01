package com.example.shopapp.controller;

import com.example.shopapp.dto.CategoryDTO;
import com.example.shopapp.response.ApiResponse;
import com.example.shopapp.entity.Category;
import com.example.shopapp.service.impl.CategoryService;
import com.example.shopapp.service.impl.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    private final CategoryService categoryService;
    private final UserService userService;

    @GetMapping
    public ApiResponse<?> getAllCategories(
            @RequestParam(name = "page") int page,
            @RequestParam(name = "limit") int limit
    ) throws Exception {
        return ApiResponse.builder()
                .message("Get All Orders with per page")
                .data(categoryService.getAllCategories(PageRequest.of(page - 1, limit)))
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(
            @PathVariable Long id
    ) {
        try {
            return ResponseEntity.ok(categoryService.findCategoryById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping
    public ResponseEntity<?> saveCategory(
            @Valid @RequestBody CategoryDTO categoryDTO, BindingResult result
    ) {
        if (result.hasErrors()) {
            Map<String, String> errors = result.getFieldErrors().stream()
                    .collect(Collectors.toMap(fieldError -> fieldError.getField(), fieldError -> fieldError.getDefaultMessage()));
            return ResponseEntity.badRequest().body(ApiResponse.builder()
                    .message("Error: " + errors.get("field"))
                    .data(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors))
                    .build());
        }
        return ResponseEntity.ok(ApiResponse.builder()
                .status(HttpStatus.CREATED.value())
                .message("Created Category Successfully")
                .data(categoryService.saveCategory(categoryDTO))
                .build());
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(
            @Valid @PathVariable Long id,
            BindingResult result,
            @RequestBody CategoryDTO categoryDTO,
            @RequestHeader("Authorization") String jwt

    ) throws Exception {
        if (result.hasErrors()) {
            Map<String, String> errors = result.getFieldErrors().stream()
                    .collect(Collectors.toMap(fieldError -> fieldError.getField(), fieldError -> fieldError.getDefaultMessage()));
            return ResponseEntity.badRequest().body(ApiResponse.builder()
                    .message("Error: " + errors.get("field"))
                    .data(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors))
                    .build());
        }
        categoryService.updateCategory(id, categoryDTO);
        return ResponseEntity.ok(ApiResponse.builder()
                .status(HttpStatus.CREATED.value())
                .message("Created Category Successfully")
                .data(categoryService.saveCategory(categoryDTO))
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(
            @PathVariable Long id
    ) {
        try {
            categoryService.deleteCategory(id);
            return ResponseEntity.ok("Category deleted");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
