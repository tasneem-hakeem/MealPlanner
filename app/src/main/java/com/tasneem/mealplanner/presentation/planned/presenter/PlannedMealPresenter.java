package com.tasneem.mealplanner.presentation.planned.presenter;

import com.tasneem.mealplanner.presentation.planned.view.PlannedMealView;

public interface PlannedMealPresenter {
    void attachView(PlannedMealView view);

    void detachView();

    void loadMealsForDate(String date);

    void deleteMeal(String mealId);

    void checkUserLoggedIn();
}