package com.tasneem.mealplanner.data.datasource.plannedmeals.mappers;

import com.tasneem.mealplanner.data.datasource.model.Meal;
import com.tasneem.mealplanner.data.db.entity.PlannedMeal;

import java.util.List;
import java.util.stream.Collectors;

public class PlannedMealEntityMapper {

    public static Meal from(PlannedMeal entity) {
        return new Meal(
                entity.getId(),
                entity.getName(),
                entity.getCategory(),
                entity.getOriginCountry(),
                entity.getSteps(),
                entity.getImageUrl(),
                entity.getDate()
        );
    }

    public static PlannedMeal to(Meal meal) {
        return new PlannedMeal(
                meal.getId(),
                meal.getName(),
                meal.getCategory(),
                meal.getOriginCountry(),
                meal.getSteps(),
                meal.getImageUrl(),
                meal.getDate()
        );
    }

    public static List<Meal> fromList(List<PlannedMeal> entities) {
        return entities.stream()
                .map(PlannedMealEntityMapper::from)
                .collect(Collectors.toList());
    }

    public static List<PlannedMeal> toList(List<Meal> meals) {
        return meals.stream()
                .map(PlannedMealEntityMapper::to)
                .collect(Collectors.toList());
    }
}