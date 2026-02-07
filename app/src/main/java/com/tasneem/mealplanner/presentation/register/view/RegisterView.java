package com.tasneem.mealplanner.presentation.register.view;

public interface RegisterView {
    void showLoading();
    void hideLoading();
    void showError(String message);
    void showSuccess(String message);
    void navigateToLogin();
    String getEmail();
    String getPassword();
    String getFullName();
    void clearInputFields();
    void showEmailError(String error);
    void showPasswordError(String error);
}