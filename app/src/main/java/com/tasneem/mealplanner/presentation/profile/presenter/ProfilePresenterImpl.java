package com.tasneem.mealplanner.presentation.profile.presenter;

import android.app.Application;

import com.tasneem.mealplanner.data.datasource.auth.repository.AuthenticationRepository;
import com.tasneem.mealplanner.data.datasource.auth.repository.AuthenticationRepositoryImpl;
import com.tasneem.mealplanner.presentation.profile.view.ProfileView;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class ProfilePresenterImpl implements ProfilePresenter {

    private ProfileView view;
    private AuthenticationRepository authRepository;
    private CompositeDisposable disposables = new CompositeDisposable();

    public ProfilePresenterImpl(Application application) {
        authRepository = new AuthenticationRepositoryImpl();
    }

    @Override
    public void loadUserProfile() {
        if(view == null) return;
        disposables.add(
                authRepository.getCurrentUser()
                        .subscribe(user -> {
                            if (user != null) {
                                view.showUserData(user);
                            }
                        }, throwable -> view.showError(throwable.getMessage()))
        );
    }

    @Override
    public void onLogoutClicked() {
        if(view == null) return;
        disposables.add(
                authRepository.signOut()
                        .subscribe(
                                () -> view.navigateToLogin(),
                                throwable -> view.showError(throwable.getMessage())
                        )
        );
    }

    @Override
    public void attachView(ProfileView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        disposables.clear();
        view = null;
    }
}
