package com.tasneem.mealplanner.presentation.register.presenter;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.tasneem.mealplanner.R;
import com.tasneem.mealplanner.data.datasource.auth.model.AuthResult;
import com.tasneem.mealplanner.data.datasource.auth.repository.AuthenticationRepository;
import com.tasneem.mealplanner.data.datasource.auth.repository.AuthenticationRepositoryImpl;
import com.tasneem.mealplanner.presentation.common.UserInputValidator;
import com.tasneem.mealplanner.presentation.common.ValidationResult;
import com.tasneem.mealplanner.presentation.register.view.RegisterView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RegisterPresenterImpl implements RegisterPresenter{
    private RegisterView view;
    private final Context context;
    private final AuthenticationRepository authRepository;
    private final CompositeDisposable compositeDisposable;

    private static final String TAG = "RegisterPresenter";

    public RegisterPresenterImpl(Application application) {
        this.context = application.getApplicationContext();
        this.authRepository = new AuthenticationRepositoryImpl();
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void attachView(RegisterView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
        compositeDisposable.clear();
    }

    @Override
    public void onSignUpClicked() {
        if (view == null) return;

        String email = view.getEmail();
        String password = view.getPassword();
        String name = view.getFullName();
        boolean isValid = validateInputs(email, password);
        if (!isValid) return;

        view.showLoading();
        Disposable disposable = authRepository.signUp(email, password, name)
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

    private void handleAuthResult(AuthResult result) {
        if (view == null) return;

        view.hideLoading();

        if (result.isSuccess()) {
            view.showSuccess(context.getString(R.string.registration_successful));
            Log.d(TAG, result.getUser().toString());
            view.clearInputFields();
            view.navigateToLogin();
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
