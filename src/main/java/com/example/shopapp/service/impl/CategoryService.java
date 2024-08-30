package com.example.shopapp.service.impl;

import com.example.shopapp.entity.Category;
import com.example.shopapp.response.CategoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface CategoryService {
    Page<CategoryResponse> getAllCategories(PageRequest pageRequest) throws Exception;
    List<Category> findCategoryByResId(Long id) throws Exception;
    Category findCategoryById(Long id) throws Exception;
    Category saveCategory(String category, Long userId) throws Exception;
    void deleteCategory(Long id) throws Exception;
    void updateCategory(Long id, String category) throws Exception;
}
