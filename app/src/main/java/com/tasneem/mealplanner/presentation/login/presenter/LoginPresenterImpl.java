package com.tasneem.mealplanner.presentation.login.presenter;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.tasneem.mealplanner.R;
import com.tasneem.mealplanner.data.datasource.auth.model.AuthResult;
import com.tasneem.mealplanner.data.datasource.auth.repository.AuthenticationRepository;
import com.tasneem.mealplanner.data.datasource.auth.repository.AuthenticationRepositoryImpl;
import com.tasneem.mealplanner.presentation.common.UserInputValidator;
import com.tasneem.mealplanner.presentation.common.ValidationResult;
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

        boolean isValid = validateInputs(email, password);
        if (!isValid) return;

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

    private boolean validateInputs(String email, String password) {
        boolean isValid = true;

        ValidationResult emailResult = UserInputValidator.validateEmail(email);
        if (!emailResult.isValid()) {
            view.showEmailError(context.getString(emailResult.getErrorStringResource()));
            isValid = false;
        }

        ValidationResult passwordResult = UserInputValidator.validatePassword(password);
        if (!passwordResult.isValid()) {
            view.showPasswordError(context.getString(passwordResult.getErrorStringResource()));
            isValid = false;
        }

        return isValid;
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

    private void handleAuthResult(AuthResult result) {
        if (view == null) return;

        view.hideLoading();

        if (result.isSuccess()) {
            view.showSuccess(context.getString(R.string.login_successful));
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
