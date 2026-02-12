package com.tasneem.mealplanner.presentation.mealdetails.view;

import com.tasneem.mealplanner.data.datasource.meals.model.Meal;

public interface MealDetailsView {
    void showLoading();
    void hideLoading();
    void showMealDetails(Meal meal);
    void showError(String message);
    void showNoInternetError(String message);
    void navigateBack();
    void playVideo(String videoUrl);
    void showAddedToPlanMessage();
    void showAddToPlanError(String message);
    void showDatePickerDialog(Meal meal);
    void updateFavoriteIcon(boolean isFavorite);
    void showAddedToFavoritesMessage();
    void showRemovedFromFavoritesMessage();
    void showFavoriteError(String message);
}