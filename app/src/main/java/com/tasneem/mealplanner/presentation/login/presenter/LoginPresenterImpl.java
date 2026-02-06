package com.tasneem.mealplanner.presentation.login.presenter;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.tasneem.mealplanner.R;
import com.tasneem.mealplanner.data.datasource.auth.model.AuthResult;
import com.tasneem.mealplanner.data.datasource.auth.repository.AuthenticationRepository;
import com.tasneem.mealplanner.data.datasource.auth.repository.AuthenticationRepositoryImpl;
import com.tasneem.mealplanner.presentation.login.view.LoginView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginPresenterImpl implements LoginPresenter {
    private LoginView view;
    private final Context context;
    private final AuthenticationRepository authRepository;
    private final CompositeDisposable compositeDisposable;

    private static final String TAG = "LoginPresenter";

    public LoginPresenterImpl(Application application) {
        this.context = application.getApplicationContext();
        this.authRepository = new AuthenticationRepositoryImpl();
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void attachView(LoginView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
        compositeDisposable.clear();
    }

    @Override
    public void onLoginClicked() {
        if (view == null) return;

        String email = view.getEmail();
        String password = view.getPassword();

        if (!validateInputs(email, password)) return;

        view.showLoading();
        Disposable disposable = authRepository.signIn(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::handleAuthResult,
                        this::handleError
                );

        compositeDisposable.add(disposable);
    }

    @Override
    public void onSignUpClicked() {
        if (view == null) return;

        String email = view.getEmail();
        String password = view.getPassword();

        if (!validateInputs(email, password)) return;

        view.showLoading();
        Disposable disposable = authRepository.signUp(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::handleAuthResult,
                        this::handleError
                );

        compositeDisposable.add(disposable);
    }

    @Override
    public void checkUserLoggedIn() {
        if (view == null) return;

        Disposable disposable = authRepository.isUserSignedIn()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
            isSignedIn -> {
                        if (isSignedIn) {
                            getCurrentUserAndNavigate();
                        }
                    },
                    throwable -> {
                        view.showError(throwable.getMessage());
                        Log.e(TAG, "Error checking user logged in", throwable);
                    }

                );
        compositeDisposable.add(disposable);
    }

    private void getCurrentUserAndNavigate() {
        Disposable disposable = authRepository.getCurrentUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> {
                            if (user != null) {
                                view.navigateToHome(user);
                            }
                        },
                        throwable -> {
                            view.showError(throwable.getMessage());
                            Log.e(TAG, "Error getting current user", throwable);
                        });
        compositeDisposable.add(disposable);
    }

    private boolean validateInputs(String email, String password) {
        boolean isValid = true;

        if (email == null || email.trim().isEmpty()) {
            view.showEmailError(context.getString(R.string.email_cannot_be_empty));
            isValid = false;
        }

        if (password == null || password.trim().isEmpty()) {
            view.showPasswordError(context.getString(R.string.password_cannot_be_empty));
            isValid = false;
        } else if (password.length() < 6) {
            view.showPasswordError(context.getString(R.string.password_must_be_at_least_6_characters));
            isValid = false;
        }

        return isValid;
    }

    private void handleAuthResult(AuthResult result) {
        if (view == null) return;

        view.hideLoading();

        if (result.isSuccess()) {
            view.showSuccess("Login successful");
            view.clearInputFields();
            view.navigateToHome(result.getUser());
        } else {
            view.showError(result.getErrorMessage());
        }
    }

    private void handleError(Throwable throwable) {
        if (view == null) return;

        view.hideLoading();
        view.showError(throwable.getMessage());
        Log.e(TAG, "Authentication error", throwable);
    }
}
