package com.tasneem.mealplanner.data.datasource.meals.mapper;

import com.tasneem.mealplanner.data.datasource.meals.model.Ingredient;
import com.tasneem.mealplanner.data.datasource.meals.remote.dto.ingredient.IngredientDto;
import com.tasneem.mealplanner.data.datasource.meals.remote.dto.meal.MealIngredientDto;

import java.util.ArrayList;
import java.util.List;

public class IngredientMapper {
    private IngredientMapper() {}

    // from meal response
    public static Ingredient from(MealIngredientDto dto) {
        if (dto == null || dto.getName() == null) return null;

        return new Ingredient(
                null,
                dto.getName(),
                dto.getMeasure(),
                null,
                null,
                null
        );
    }

    public static List<Ingredient> fromMealIngredients(List<MealIngredientDto> dtos) {
        List<Ingredient> ingredients = new ArrayList<>();

        if (dtos == null) return ingredients;

        for (MealIngredientDto dto : dtos) {
            Ingredient ingredient = from(dto);
            if (ingredient != null) ingredients.add(ingredient);
        }

        return ingredients;
    }

    // from ingredient response
    public static Ingredient from(IngredientDto dto) {
        if (dto == null) return null;

        return new Ingredient(
                dto.getId(),
                dto.getName(),
                null,
                dto.getDescription(),
                dto.getImageUrl(),
                dto.getType()
        );
    }

    public static List<Ingredient> fromIngredients(List<IngredientDto> dtos) {
        List<Ingredient> ingredients = new ArrayList<>();

        if (dtos == null) return ingredients;

        for (IngredientDto dto : dtos) {
            Ingredient ingredient = from(dto);
            if (ingredient != null) ingredients.add(ingredient);
        }

        return ingredients;
    }
}
