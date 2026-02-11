package com.tasneem.mealplanner.presentation.search.presenter;

public interface SearchPresenter {

    void loadInitialData();

    void searchMealsByName(String query);

    void onCategoryClicked(String categoryName);


    void onIngredientClicked(String ingredientName);

    void onMealClicked(String mealId);

    void clearSearch();

    void onDestroy();
}