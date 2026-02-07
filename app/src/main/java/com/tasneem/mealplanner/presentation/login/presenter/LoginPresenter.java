package com.tasneem.mealplanner.presentation.login.presenter;

import com.tasneem.mealplanner.presentation.login.view.LoginView;

public interface LoginPresenter {
    void attachView(LoginView view);
    void detachView();
    void onLoginClicked();
    void onGoogleSignInClicked();
    void onGoogleSignInResult(String idToken);
    void checkUserLoggedIn();
}