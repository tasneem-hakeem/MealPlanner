package com.tasneem.mealplanner.presentation.profile.presenter;

import com.tasneem.mealplanner.presentation.profile.view.ProfileView;

public interface ProfilePresenter {
    void loadUserProfile();

    void onLogoutClicked();

    void attachView(ProfileView view);

    void detachView();
}
