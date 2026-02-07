package com.tasneem.mealplanner.data.datasource.meals.remote;

import com.tasneem.mealplanner.data.datasource.meals.remote.dto.areaslist.AreasListResponse;
import com.tasneem.mealplanner.data.datasource.meals.remote.dto.categorieslist.CategoriesListResponse;
import com.tasneem.mealplanner.data.datasource.meals.remote.dto.category.CategoryResponse;
import com.tasneem.mealplanner.data.datasource.meals.remote.dto.ingredient.IngredientsResponse;
import com.tasneem.mealplanner.data.datasource.meals.remote.dto.meal.MealResponse;

import io.reactivex.rxjava3.core.Single;

public interface MealsRemoteDatasource {

    Single<MealResponse> getRandomMeal();

    Single<MealResponse> getMealsByName(String name);

    Single<MealResponse> getMealsByFirstLetter(char firstLetter);

    Single<MealResponse> getMealDetailsById(String id);

    Single<MealResponse> getMealsByCategory(String category);

    Single<MealResponse> getMealsByArea(String area);

    Single<MealResponse> getMealsByMainIngredient(String ingredient);

    Single<CategoryResponse> getAllCategories();

    Single<IngredientsResponse> getAllIngredients();

    Single<CategoriesListResponse> getCategoriesList();

    Single<AreasListResponse> getAreasList();
}