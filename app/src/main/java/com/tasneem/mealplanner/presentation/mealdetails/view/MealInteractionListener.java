package com.tasneem.mealplanner.presentation.mealdetails.view;

import com.tasneem.mealplanner.data.datasource.meals.model.Meal;

public interface MealInteractionListener {
    void FavoriteIconClicked(Meal meal);
}
