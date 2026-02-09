package com.tasneem.mealplanner.presentation.mealdetails.presenter;

import com.tasneem.mealplanner.presentation.mealdetails.view.MealDetailsView;

public interface MealDetailsPresenter {
    void attachView(MealDetailsView view);
    void detachView();
    void onViewStarted();
    void onFavoriteClicked(Boolean isFavorite);
    void onVideoClicked();
    void onAddToPlanClicked();
}
