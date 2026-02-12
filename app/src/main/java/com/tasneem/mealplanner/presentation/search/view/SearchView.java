package com.tasneem.mealplanner.presentation.search.view;

import androidx.annotation.StringRes;

import com.tasneem.mealplanner.data.datasource.model.Area;
import com.tasneem.mealplanner.data.datasource.model.Category;
import com.tasneem.mealplanner.data.datasource.model.Ingredient;
import com.tasneem.mealplanner.data.datasource.model.Meal;

import java.util.List;

public interface SearchView {

    void showSearchResults(List<Meal> meals);

    void showTrendingIngredients(List<Ingredient> ingredients);

    void showCategories(List<Category> categories);

    void showAreas(List<Area> areas);

    void showEmptyState();

    void showLoading();

    void hideLoading();

    void showError(@StringRes int messageResId, String errorDetails);

    void navigateToMealDetails(String mealId);
}