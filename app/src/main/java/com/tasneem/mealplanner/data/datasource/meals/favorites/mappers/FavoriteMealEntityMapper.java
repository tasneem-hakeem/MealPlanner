package com.tasneem.mealplanner.data.datasource.meals.favorites.mappers;

import com.tasneem.mealplanner.data.datasource.meals.model.Meal;
import com.tasneem.mealplanner.data.db.entity.FavoriteMeal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FavoriteMealEntityMapper {

    private FavoriteMealEntityMapper() {
    }

    public static Meal from(FavoriteMeal favoriteMeal) {
        if (favoriteMeal == null) return null;

        return new Meal(
                favoriteMeal.getId(),
                favoriteMeal.getName(),
                favoriteMeal.getCategory(),
                favoriteMeal.getOriginCountry(),
                favoriteMeal.getSteps(),
                favoriteMeal.getImageUrl()
        );
    }

    public static FavoriteMeal to(Meal meal) {
        if (meal == null) return null;

        return new FavoriteMeal(
                meal.getId(),
                meal.getName(),
                meal.getCategory(),
                meal.getOriginCountry(),
                meal.getSteps(),
                meal.getImageUrl()
        );
    }

    public static List<Meal> fromList(List<FavoriteMeal> favoriteMeals) {
        if (favoriteMeals == null || favoriteMeals.isEmpty()) {
            return Collections.emptyList();
        }

        List<Meal> meals = new ArrayList<>();

        for (FavoriteMeal favMeal : favoriteMeals) {
            Meal meal = from(favMeal);
            if (meal != null) {
                meals.add(meal);
            }
        }

        return meals;
    }
}
