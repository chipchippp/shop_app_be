package com.example.shopapp.service;

import com.example.shopapp.dto.CategoryDTO;
import com.example.shopapp.entity.Category;
import com.example.shopapp.mapper.CategoryMapper;
import com.example.shopapp.respository.CategoryRepository;
import com.example.shopapp.service.impl.CategoryService;
import com.example.shopapp.service.impl.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public Page<CategoryDTO> getAllCategories(Pageable pageable){
        Page<Category> categories = categoryRepository.findAll(pageable);
        return categories.map(CategoryMapper.INSTANCE::toCategoryDTO);
    }

    public Category findCategoryById(Long id)  {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty()){
            throw new RuntimeException("Category not found");
        }
        return category.get();
    }

    @Override
    public CategoryDTO saveCategory(CategoryDTO categoryDTO)  {
        if (categoryRepository.existsByCategoryName(categoryDTO.getName())){
            throw new RuntimeException("Category already exists with name: " + categoryDTO.getName());
        }
        Category category = CategoryMapper.INSTANCE.categoryDTOToCategory(categoryDTO);
        categoryRepository.save(category);
        return categoryDTO;

    }

    @Override
    public void updateCategory(Long id, CategoryDTO categoryDTO)  {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
        if (categoryRepository.existsByCategoryName(categoryDTO.getName()) && !categoryDTO.getName().equals(category.getName())) {
            throw new RuntimeException("Category already exists with name: " + categoryDTO.getName());
        }
        category.setName(categoryDTO.getName());
        categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long id)  {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
        categoryRepository.delete(category);
    }
}
