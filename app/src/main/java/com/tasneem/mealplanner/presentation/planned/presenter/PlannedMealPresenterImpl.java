package com.tasneem.mealplanner.presentation.planned.presenter;
import android.util.Log;

import com.tasneem.mealplanner.data.datasource.meals.model.Meal;
import com.tasneem.mealplanner.data.datasource.meals.repository.MealsRepository;
import com.tasneem.mealplanner.presentation.planned.view.PlannedMealView;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PlannedMealPresenterImpl implements PlannedMealPresenter {

    private static final String TAG = "PlannedMealPresenter";

    private final PlannedMealView view;
    private final MealsRepository repository;
    private final CompositeDisposable compositeDisposable;

    public PlannedMealPresenterImpl(PlannedMealView view, MealsRepository repository) {
        this.view = view;
        this.repository = repository;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void loadMealsForDate(String date) {
        Log.d(TAG, "loadMealsForDate: " + date);
        view.showLoading();

        compositeDisposable.add(
                repository.getPlannedMealsByDate(date)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                meals -> {
                                    Log.d(TAG, "Meals loaded for " + date + ": " + meals.size());

                                    for (Meal meal : meals) {
                                        Log.d(TAG, "  - " + meal.getName() + " (Date: " + meal.getDate() + ")");
                                    }

                                    view.hideLoading();

                                    if (meals.isEmpty()) {
                                        view.showEmptyState();
                                    } else {
                                        view.showMeals(meals);
                                    }
                                },
                                error -> {
                                    Log.e(TAG, "Error loading meals: " + error.getMessage(), error);
                                    view.hideLoading();
                                    view.showError("Failed to load meals: " + error.getMessage());
                                }
                        )
        );
    }

    @Override
    public void deleteMeal(String mealId) {
        Log.d(TAG, "deleteMeal: " + mealId);
        compositeDisposable.add(
                repository.deletePlannedById(mealId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> {
                                    Log.d(TAG, "Meal deleted successfully");
                                    view.showDeleteSuccess();
                                },
                                error -> {
                                    Log.e(TAG, "Error deleting meal: " + error.getMessage(), error);
                                    view.showError("Failed to delete meal: " + error.getMessage());
                                }
                        )
        );
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy - clearing disposables");
        compositeDisposable.clear();
    }
}