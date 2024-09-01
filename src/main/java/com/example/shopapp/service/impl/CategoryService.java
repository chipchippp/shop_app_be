package com.example.shopapp.service.impl;

import com.example.shopapp.dto.CategoryDTO;
import com.example.shopapp.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    Page<CategoryDTO> getAllCategories(Pageable pageable) ;
//    List<Category> findCategoryByResId(Long id) ;
    Category findCategoryById(Long id) ;
    CategoryDTO saveCategory(CategoryDTO categoryDTO) ;
    void updateCategory(Long id, CategoryDTO categoryDTO) ;
    void deleteCategory(Long id) ;
}
