package com.tasneem.mealplanner.presentation.splash.presenter;

import com.tasneem.mealplanner.presentation.splash.view.SplashView;

public interface SplashPresenter {
    void checkIfLoggedInAndNavigate();

    void attachView(SplashView view);

    void detachView();
}
