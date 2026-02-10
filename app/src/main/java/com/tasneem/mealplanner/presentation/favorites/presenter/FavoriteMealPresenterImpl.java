package com.tasneem.mealplanner.presentation.favorites.presenter;

import android.app.Application;
import android.content.Context;

import com.tasneem.mealplanner.R;
import com.tasneem.mealplanner.data.datasource.meals.repository.MealsRepository;
import com.tasneem.mealplanner.data.datasource.meals.repository.MealsRepositoryImpl;
import com.tasneem.mealplanner.presentation.favorites.view.FavoriteMealView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavoriteMealPresenterImpl implements FavoriteMealPresenter {
    private FavoriteMealView view;
    private final MealsRepository repository;
    private final Context context;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public FavoriteMealPresenterImpl(Application application) {
        this.repository = new MealsRepositoryImpl(application);
        this.context = application.getApplicationContext();
    }

    @Override
    public void attachView(FavoriteMealView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
        disposables.clear();
    }

    @Override
    public void onViewStarted() {
        if (view == null) return;

        getFavoriteMeals();
    }

    private void getFavoriteMeals() {
        Disposable disposable = repository.getAllFavorites()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        meals -> view.showFavoriteMeals(meals),
                        throwable -> view.showError(context.getString(R.string.there_are_no_meals_in_favorites) + throwable.getMessage())
                );
        disposables.add(disposable);
    }

    @Override
    public void removeFromFavorite(String mealId) {
        Disposable disposable = repository.deleteFavoriteById(mealId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> view.onRemoveFromFavClicked(mealId),
                        throwable -> view.showError(context.getString(R.string.remove_from_favorites_failed) + throwable.getMessage())
                );
        disposables.add(disposable);
    }
}
