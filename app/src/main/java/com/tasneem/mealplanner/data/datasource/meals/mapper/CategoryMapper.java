package com.tasneem.mealplanner.data.datasource.meals.mapper;

import com.tasneem.mealplanner.data.datasource.model.Category;
import com.tasneem.mealplanner.data.datasource.meals.remote.dto.category.CategoryDto;

import java.util.ArrayList;
import java.util.List;

public class CategoryMapper {

    private CategoryMapper() {}

    public static Category from(CategoryDto dto) {
        if (dto == null) return null;

        return new Category(
                dto.getId(),
                dto.getName(),
                dto.getImageUrl(),
                dto.getDescription()
        );
    }

    public static List<Category> fromList(List<CategoryDto> dtos) {
        List<Category> categories = new ArrayList<>();

        if (dtos == null) return categories;

        for (CategoryDto dto : dtos) {
            Category category = from(dto);
            if (category != null) {
                categories.add(category);
            }
        }

        return categories;
    }
}
