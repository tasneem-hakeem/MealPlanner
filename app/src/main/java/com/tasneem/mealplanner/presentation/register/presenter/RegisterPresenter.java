package com.tasneem.mealplanner.presentation.register.presenter;

import com.tasneem.mealplanner.presentation.register.view.RegisterView;

public interface RegisterPresenter {
    void attachView(RegisterView view);
    void detachView();
    void onSignUpClicked();
}
