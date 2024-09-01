package com.example.shopapp.mapper;

import com.example.shopapp.dto.CategoryDTO;
import com.example.shopapp.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);
    CategoryDTO toCategoryDTO(Category category);
    Category categoryDTOToCategory(CategoryDTO categoryDTO);
}