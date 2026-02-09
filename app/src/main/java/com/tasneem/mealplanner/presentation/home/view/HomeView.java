package com.tasneem.mealplanner.presentation.home.view;

import com.tasneem.mealplanner.data.datasource.meals.model.Meal;

import java.util.List;

public interface HomeView {
    void showMealOfTheDayLoading();
    void hideMealOfTheDayLoading();

    void showHealthyMealsLoading();
    void hideHealthyMealsLoading();

    void showBreakfastSuggestionsLoading();
    void hideBreakfastSuggestionsLoading();

    void showUserName(String name);

    void showMealOfTheDay(Meal meal);

    void showHealthyMeals(List<Meal> meals);
    void showBreakfastSuggestions(List<Meal> meals);

    void onAddToPlannerClicked();

    void showError(String message);
}
