package com.tasneem.mealplanner.presentation.planned.view;


import com.tasneem.mealplanner.data.datasource.model.Meal;

import java.util.List;

public interface PlannedMealView {
    void showLoading();
    void hideLoading();
    void showMeals(List<Meal> meals);
    void showEmptyState();
    void showError(String message);
    void showDeleteSuccess();
}