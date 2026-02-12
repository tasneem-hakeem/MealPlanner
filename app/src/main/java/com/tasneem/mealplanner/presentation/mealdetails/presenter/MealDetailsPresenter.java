package com.tasneem.mealplanner.presentation.mealdetails.presenter;

import com.tasneem.mealplanner.data.datasource.meals.model.Meal;
import com.tasneem.mealplanner.presentation.mealdetails.view.MealDetailsView;

public interface MealDetailsPresenter {
    void attachView(MealDetailsView view);
    void detachView();
    void onViewStarted(String mealId);
    void onAddToPlanClicked(Meal meal, String selectedDate);
    void onFavoriteClicked(Meal meal);
    void checkIfMealIsFavorite(String mealId);
}
