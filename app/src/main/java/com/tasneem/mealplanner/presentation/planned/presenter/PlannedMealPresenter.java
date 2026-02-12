package com.tasneem.mealplanner.presentation.planned.presenter;

public interface PlannedMealPresenter {

    void loadMealsForDate(String date);

    void deleteMeal(String mealId);

    void onDestroy();

}