package com.tasneem.mealplanner.presentation.mealdetails.presenter;

import android.app.Application;
import android.content.Context;

import com.tasneem.mealplanner.R;
import com.tasneem.mealplanner.data.datasource.meals.repository.MealsRepository;
import com.tasneem.mealplanner.data.datasource.meals.repository.MealsRepositoryImpl;
import com.tasneem.mealplanner.presentation.mealdetails.view.MealDetailsView;

import java.io.IOException;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealDetailsPresenterImpl implements MealDetailsPresenter {
    private MealDetailsView view;
    private final MealsRepository repository;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final Context context;

    public MealDetailsPresenterImpl(Application application) {
        repository = new MealsRepositoryImpl();
        this.context = application.getApplicationContext();
    }


    @Override
    public void attachView(MealDetailsView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
        disposables.clear();
    }

    @Override
    public void onViewStarted(String mealId) {
        if (view == null) return;

        loadMealDetails(mealId);
    }

    private void loadMealDetails(String mealId) {
        view.showLoading();
        Disposable disposable = repository.getMealDetailsById(mealId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        meal -> {
                            view.hideLoading();
                            view.showMealDetails(meal);
                        },
                        throwable -> {
                            view.hideLoading();
                            if (throwable instanceof IOException) {
                                view.showNoInternetError(context.getString(R.string.no_internet_connection));
                            } else {
                                view.showError(context.getString(R.string.something_went_wrong));
                            }
                        }
                );
        disposables.add(disposable);
    }

    @Override
    public void onFavoriteClicked(Boolean isFavorite) {
        // TODO: add to fav
        view.updateFavoriteStatus(isFavorite);
    }

    @Override
    public void onAddToPlanClicked() {
        // TODO: handle att to plan
    }
}
