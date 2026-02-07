package com.tasneem.mealplanner.data.datasource.meals.mapper;

import com.tasneem.mealplanner.data.datasource.meals.model.Meal;
import com.tasneem.mealplanner.data.datasource.meals.model.Ingredient;
import com.tasneem.mealplanner.data.datasource.meals.remote.dto.meal.MealDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MealsMapper {

    private MealsMapper() {}

    public static Meal from(MealDto dto) {
        if (dto == null) return null;

        List<Ingredient> ingredients =
                IngredientMapper.fromMealIngredients(dto.getIngredients());

        return new Meal(
                dto.getId(),
                dto.getName(),
                dto.getCategory(),
                dto.getOriginCountry(),
                dto.getSteps(),
                dto.getImageUrl(),
                dto.getVideoUrl(),
                dto.getSourceUrl(),
                dto.getLastEditTime(),
                ingredients
        );
    }

    public static List<Meal> fromList(List<MealDto> dtos) {
        if (dtos == null || dtos.isEmpty()) {
            return Collections.emptyList();
        }

        List<Meal> meals = new ArrayList<>();

        for (MealDto dto : dtos) {
            Meal meal = from(dto);
            if (meal != null) {
                meals.add(meal);
            }
        }

        return meals;
    }
}
