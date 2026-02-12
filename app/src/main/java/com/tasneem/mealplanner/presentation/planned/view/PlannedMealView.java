package com.tasneem.mealplanner.presentation.planned.view;


import com.tasneem.mealplanner.data.datasource.meals.model.Meal;

import java.util.List;

public interface PlannedMealView {

    void showPlannedMeals(List<Meal> meals);

    void showLoading();

    void hideLoading();

    void showError(String message);

    void showEmptyState();

    void hideEmptyState();

    void updateSelectedDate(String dateText);

    void updateTotalCalories(int calories);

    void showMealDeleted();

    void navigateToAddMeal(String selectedDate);

    void updateCalendarSelection(int position);
}