package com.example.shopapp.service;

import com.example.shopapp.entity.Category;
import com.example.shopapp.entity.Restaurant;
import com.example.shopapp.response.CategoryResponse;
import com.example.shopapp.respository.CategoryRepository;
import com.example.shopapp.service.impl.CategoryService;
import com.example.shopapp.service.impl.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final RestaurantService restaurantService;


    @Override
    public Page<CategoryResponse> getAllCategories(PageRequest pageRequest) throws Exception {
        return categoryRepository.findAll(pageRequest).map(CategoryResponse::fromCategory);
    }

    @Override
    public List<Category> findCategoryByResId(Long id) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(id);
        return categoryRepository.findByRestaurantId(id);
    }

    @Override
    public Category findCategoryById(Long id) throws Exception {
        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
    }

    @Override
    public Category saveCategory(String category, Long userId) throws Exception {
        Restaurant restaurant = restaurantService.getRestaurantByUserId(userId);
        Category newCategory = new Category();
        newCategory.setName(category);
        newCategory.setRestaurant(restaurant);
        return categoryRepository.save(newCategory);
    }

    @Override
    public void deleteCategory(Long id) throws Exception {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
        categoryRepository.delete(category);
    }

    @Override
    public void updateCategory(Long id, String category) throws Exception {
        Category existingCategory = findCategoryById(id);
        existingCategory.setName(category);
        categoryRepository.save(existingCategory);
    }
}
