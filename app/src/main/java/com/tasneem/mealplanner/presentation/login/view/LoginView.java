package com.tasneem.mealplanner.presentation.login.view;

import com.tasneem.mealplanner.data.datasource.auth.model.User;

public interface LoginView {
    void showLoading();
    void hideLoading();
    void showError(String message);
    void showSuccess(String message);
    void navigateToSignUp();
    void navigateToHome(User user);
    void launchGoogleSignIn();
    String getEmail();
    String getPassword();
    void clearInputFields();
    void showEmailError(String error);
    void showPasswordError(String error);
}