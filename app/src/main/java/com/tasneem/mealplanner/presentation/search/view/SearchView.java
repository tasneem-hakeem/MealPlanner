package com.tasneem.mealplanner.presentation.search.view;

import com.tasneem.mealplanner.data.datasource.meals.model.Category;
import com.tasneem.mealplanner.data.datasource.meals.model.Ingredient;
import com.tasneem.mealplanner.data.datasource.meals.model.Meal;

import java.util.List;

public interface SearchView {

    void showSearchResults(List<Meal> meals);

    void showTrendingIngredients(List<Ingredient> ingredients);

    void showCategories(List<Category> categories);

    void showEmptyState();

    void showLoading();

    void hideLoading();

    void showError(String message);

    void navigateToMealDetails(String mealId);

    void navigateToCategoryMeals(String categoryName);

    void navigateToIngredientMeals(String ingredientName);
}