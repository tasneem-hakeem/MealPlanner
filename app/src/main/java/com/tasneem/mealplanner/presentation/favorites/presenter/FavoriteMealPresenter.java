package com.tasneem.mealplanner.presentation.favorites.presenter;

import com.tasneem.mealplanner.presentation.favorites.view.FavoriteMealView;

public interface FavoriteMealPresenter {
    void attachView(FavoriteMealView view);
    void detachView();
    void onViewStarted();
    void removeFromFavorite(String mealId);
}