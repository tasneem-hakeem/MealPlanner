package com.tasneem.mealplanner.presentation.search.presenter;

import android.util.Log;

import com.tasneem.mealplanner.data.datasource.meals.model.Ingredient;
import com.tasneem.mealplanner.data.datasource.meals.repository.MealsRepository;
import com.tasneem.mealplanner.presentation.search.view.SearchView;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchPresenterImpl implements SearchPresenter {

    private final SearchView view;
    private final MealsRepository repository;
    private final CompositeDisposable disposables;

    public SearchPresenterImpl(SearchView view, MealsRepository repository) {
        this.view = view;
        this.repository = repository;
        this.disposables = new CompositeDisposable();
    }

    @Override
    public void loadInitialData() {
        loadTrendingIngredients();
        loadCategories();
    }

    private void loadTrendingIngredients() {
        disposables.add(
                repository.getAllIngredients()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                ingredients -> {
                                    List<Ingredient> trending = ingredients.size() > 10
                                            ? ingredients.subList(0, 10)
                                            : ingredients;
                                    view.showTrendingIngredients(trending);
                                },
                                error -> view.showError("Failed to load trending ingredients: " + error.getMessage())
                        )
        );
    }

    private void loadCategories() {
        disposables.add(
                repository.getAllCategories()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                view::showCategories,
                                error -> view.showError("Failed to load categories: " + error.getMessage())
                        )
        );
    }

    @Override
    public void searchMealsByName(String query) {
        if (query == null || query.trim().isEmpty()) {
            clearSearch();
            return;
        }

        view.showLoading();

        disposables.add(
                repository.getMealsByName(query.trim())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                meals -> {
                                    view.hideLoading();
                                    if (meals == null || meals.isEmpty()) {
                                        view.showEmptyState();
                                    } else {
                                        view.showSearchResults(meals);
                                    }
                                },
                                error -> {
                                    view.hideLoading();
                                    view.showError("Search failed: " + error.getMessage());
                                }
                        )
        );
    }


    @Override
    public void onCategoryClicked(String categoryName) {
        if (categoryName != null && !categoryName.isEmpty()) {
            view.navigateToCategoryMeals(categoryName);
        }
    }

    @Override
    public void onIngredientClicked(String ingredientName) {
        if (ingredientName != null && !ingredientName.isEmpty()) {
            view.navigateToIngredientMeals(ingredientName);
        }
    }

    @Override
    public void onMealClicked(String mealId) {
        if (mealId == null) return;
        view.navigateToMealDetails(mealId);
    }

    @Override
    public void clearSearch() {
        loadInitialData();
    }

    @Override
    public void onDestroy() {
        disposables.clear();
    }
}