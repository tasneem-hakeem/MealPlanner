package com.tasneem.mealplanner.presentation.search.presenter;

import com.tasneem.mealplanner.presentation.search.view.SearchView;

public interface SearchPresenter {

    void attachView(SearchView view);

    void detachView();

    void loadInitialData();

    void searchMealsByName(String query);

    void searchMealsByCategory(String categoryName);

    void searchMealsByIngredient(String ingredientName);

    void searchMealsByArea(String areaName);

    void onCategoryClicked(String categoryName);

    void onIngredientClicked(String ingredientName);

    void onAreaClicked(String areaName);

    void onMealClicked(String mealId);

    void clearSearch();
}