package com.tasneem.mealplanner.presentation.profile.view;

import com.tasneem.mealplanner.data.datasource.auth.model.User;

public interface ProfileView {
    void showUserData(User user);
    void showError(String message);
    void navigateToLogin();
}
