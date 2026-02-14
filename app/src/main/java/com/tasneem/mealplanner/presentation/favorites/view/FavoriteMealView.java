package com.tasneem.mealplanner.presentation.favorites.view;

import com.tasneem.mealplanner.data.datasource.model.Meal;

import java.util.List;

public interface FavoriteMealView {
    void showFavoriteMeals(List<Meal> meals);

    void showError(String message);

    void showLoginLayout();

    void onRemoveFromFavClicked(String mealId);
}
