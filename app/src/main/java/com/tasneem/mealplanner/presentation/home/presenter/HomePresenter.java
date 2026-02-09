package com.tasneem.mealplanner.presentation.home.presenter;

import com.tasneem.mealplanner.presentation.home.view.HomeView;

public interface HomePresenter {
    void attachView(HomeView view);
    void detachView();
    void onViewStarted();
}
