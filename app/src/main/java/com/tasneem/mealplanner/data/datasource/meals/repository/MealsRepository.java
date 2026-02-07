package com.tasneem.mealplanner.data.datasource.meals.repository;

import com.tasneem.mealplanner.data.datasource.meals.model.Category;
import com.tasneem.mealplanner.data.datasource.meals.model.Ingredient;
import com.tasneem.mealplanner.data.datasource.meals.model.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface MealsRepository {
    Single<Meal> getRandomMeal();

    Single<List<Meal>> getMealsByName(String name);

    Single<List<Meal>> getMealsByFirstLetter(char firstLetter);

    Single<Meal> getMealDetailsById(String id);

    Single<List<Meal>> getMealsByCategory(String category);

    Single<List<Meal>> getMealsByArea(String area);

    Single<List<Meal>> getMealsByMainIngredient(String ingredient);

    Single<List<Category>> getAllCategories();

    Single<List<Ingredient>> getAllIngredients();

    Single<List<String>> getCategoriesList();

    Single<List<String>> getAreasList();
}
