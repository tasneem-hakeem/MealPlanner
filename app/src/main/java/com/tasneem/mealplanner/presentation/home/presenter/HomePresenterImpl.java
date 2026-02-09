package com.tasneem.mealplanner.presentation.home.presenter;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.tasneem.mealplanner.data.datasource.auth.repository.AuthenticationRepository;
import com.tasneem.mealplanner.data.datasource.auth.repository.AuthenticationRepositoryImpl;
import com.tasneem.mealplanner.data.datasource.meals.model.Meal;
import com.tasneem.mealplanner.data.datasource.meals.repository.MealsRepository;
import com.tasneem.mealplanner.data.datasource.meals.repository.MealsRepositoryImpl;
import com.tasneem.mealplanner.presentation.home.view.HomeView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomePresenterImpl implements HomePresenter {
    private HomeView view;
    private final MealsRepository mealsRepository;
    private final AuthenticationRepository authRepository;
    private final Context context;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public HomePresenterImpl(Application application) {
        this.mealsRepository = new MealsRepositoryImpl();
        this.authRepository = new AuthenticationRepositoryImpl();
        this.context = application.getApplicationContext();
    }

    @Override
    public void attachView(HomeView view) {
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

        getMealOfTheDay();
        getHealthyMeals();
        getBreakfastSuggestions();
        getUserName();
    }

    private void getMealOfTheDay() {
        view.showMealOfTheDayLoading();

        Disposable disposable = mealsRepository.getRandomMeal()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(meal -> {
                            view.hideMealOfTheDayLoading();
                            view.showMealOfTheDay(meal);
                        },
                        throwable -> {
                            view.hideMealOfTheDayLoading();
                            view.showError(throwable.getMessage());
                        }
                );
        disposables.add(disposable);
    }

    private void getHealthyMeals() {
        view.showHealthyMealsLoading();

        Single<List<Meal>> vegetarianMeals = mealsRepository.getMealsByCategory("Vegetarian");
        Single<List<Meal>> veganMeals = mealsRepository.getMealsByCategory("Vegan");
        Single<List<Meal>> seafoodMeals = mealsRepository.getMealsByCategory("Seafood");

        Disposable disposable = Single.zip(
                        vegetarianMeals,
                        veganMeals,
                        seafoodMeals,
                        (veg, vegan, seafood) -> {
                            List<Meal> allHealthyMeals = new ArrayList<>();
                            allHealthyMeals.addAll(seafood);
                            allHealthyMeals.addAll(veg);
                            allHealthyMeals.addAll(vegan);

                            Log.d("HomePresenter", String.valueOf(allHealthyMeals.size()));

                            return allHealthyMeals;
                        }
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        meals -> {
                            view.hideHealthyMealsLoading();
                            view.showHealthyMeals(meals);
                        },
                        throwable -> {
                            view.hideHealthyMealsLoading();
                            view.showError(throwable.getMessage());
                        }
                );

        disposables.add(disposable);
    }

    private void getBreakfastSuggestions() {
        view.showBreakfastSuggestionsLoading();
        Disposable disposable = mealsRepository.getMealsByCategory("Breakfast")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(meals -> {
                            view.hideBreakfastSuggestionsLoading();
                            view.showBreakfastSuggestions(meals);
                        },
                        throwable -> {
                            view.hideBreakfastSuggestionsLoading();
                            view.showError(throwable.getMessage());
                        });
        disposables.add(disposable);
    }

    private void getUserName() {
        // TODO: handle if not logged in to send placeholder name to the view
        Disposable disposable = authRepository.getCurrentUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> view.showUserName(user.getDisplayName()),
                        throwable -> view.showError(throwable.getMessage())
                );
        disposables.add(disposable);
    }
}
