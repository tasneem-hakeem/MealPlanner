package com.tasneem.mealplanner.presentation.splash.presenter;

import com.tasneem.mealplanner.data.datasource.auth.repository.AuthenticationRepository;
import com.tasneem.mealplanner.data.datasource.auth.repository.AuthenticationRepositoryImpl;
import com.tasneem.mealplanner.presentation.splash.view.SplashView;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SplashPresenterImpl implements SplashPresenter {
    private SplashView view;
    private CompositeDisposable disposables;
    private final AuthenticationRepository auth;

    public SplashPresenterImpl() {
        disposables = new CompositeDisposable();
        auth = new AuthenticationRepositoryImpl();
    }

    @Override
    public void checkIfLoggedInAndNavigate() {
        if (view == null) return;

        Disposable disposable = auth.isUserSignedIn()
                .delay(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        isSignedIn -> {
                            if (isSignedIn) {
                                view.navigateToHome();
                            } else {
                                view.navigateToLogin();
                            }
                        },
                        throwable -> view.showError(throwable.getMessage())

                );
        disposables.add(disposable);
    }

    @Override
    public void attachView(SplashView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }
}
