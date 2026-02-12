package com.tasneem.mealplanner.presentation.search.presenter;

import com.tasneem.mealplanner.R;
import com.tasneem.mealplanner.data.datasource.meals.model.Area;
import com.tasneem.mealplanner.data.datasource.meals.model.Ingredient;
import com.tasneem.mealplanner.data.datasource.meals.repository.MealsRepository;
import com.tasneem.mealplanner.presentation.search.view.SearchView;
import com.tasneem.mealplanner.presentation.utils.GetFlagsUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchPresenterImpl implements SearchPresenter {

    private SearchView view;
    private final MealsRepository repository;
    private final CompositeDisposable disposables;

    public SearchPresenterImpl(MealsRepository repository) {
        this.repository = repository;
        this.disposables = new CompositeDisposable();
    }

    @Override
    public void attachView(SearchView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
        disposables.clear();
    }

    @Override
    public void loadInitialData() {
        loadTrendingIngredients();
        loadCategories();
        loadAreas();
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
                                    if (view != null) {
                                        view.showTrendingIngredients(trending);
                                    }
                                },
                                error -> {
                                    if (view != null) {
                                        view.showError(
                                                R.string.error_load_trending_ingredients,
                                                error.getMessage()
                                        );
                                    }
                                }
                        )
        );
    }

    private void loadCategories() {
        disposables.add(
                repository.getAllCategories()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                categories -> {
                                    if (view != null) {
                                        view.showCategories(categories);
                                    }
                                },
                                error -> {
                                    if (view != null) {
                                        view.showError(
                                                R.string.error_load_categories,
                                                error.getMessage()
                                        );
                                    }
                                }
                        )
        );
    }

    private void loadAreas() {
        disposables.add(
                repository.getAreasList()
                        .subscribeOn(Schedulers.io())
                        .map(this::convertToAreasList)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                areas -> {
                                    if (view != null) {
                                        view.showAreas(areas);
                                    }
                                },
                                error -> {
                                    if (view != null) {
                                        view.showError(
                                                R.string.error_load_areas,
                                                error.getMessage()
                                        );
                                    }
                                }
                        )
        );
    }

    private List<Area> convertToAreasList(List<String> areaNames) {
        List<Area> areas = new ArrayList<>();
        for (String areaName : areaNames) {
            String flagUrl = GetFlagsUtil.getFlagUrl(areaName);
            areas.add(new Area(areaName, flagUrl));
        }
        return areas;
    }

    @Override
    public void searchMealsByName(String query) {
        if (query == null || query.trim().isEmpty()) {
            clearSearch();
            return;
        }

        if (view != null) {
            view.showLoading();
        }

        disposables.add(
                repository.getMealsByName(query.trim())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doFinally(() -> {
                            if (view != null) {
                                view.hideLoading();
                            }
                        })
                        .subscribe(
                                meals -> {
                                    if (view != null) {
                                        if (meals == null || meals.isEmpty()) {
                                            view.showEmptyState();
                                        } else {
                                            view.showSearchResults(meals);
                                        }
                                    }
                                },
                                error -> {
                                    if (view != null) {
                                        view.showError(
                                                R.string.error_search_failed,
                                                error.getMessage()
                                        );
                                    }
                                }
                        )
        );
    }

    @Override
    public void searchMealsByCategory(String categoryName) {
        if (categoryName == null || categoryName.trim().isEmpty()) {
            return;
        }

        if (view != null) {
            view.showLoading();
        }

        disposables.add(
                repository.getMealsByCategory(categoryName.trim())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doFinally(() -> {
                            if (view != null) {
                                view.hideLoading();
                            }
                        })
                        .subscribe(
                                meals -> {
                                    if (view != null) {
                                        if (meals == null || meals.isEmpty()) {
                                            view.showEmptyState();
                                        } else {
                                            view.showSearchResults(meals);
                                        }
                                    }
                                },
                                error -> {
                                    if (view != null) {
                                        view.showError(
                                                R.string.error_load_meals_category,
                                                error.getMessage()
                                        );
                                    }
                                }
                        )
        );
    }

    @Override
    public void searchMealsByIngredient(String ingredientName) {
        if (ingredientName == null || ingredientName.trim().isEmpty()) {
            return;
        }

        if (view != null) {
            view.showLoading();
        }

        disposables.add(
                repository.getMealsByMainIngredient(ingredientName.trim())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doFinally(() -> {
                            if (view != null) {
                                view.hideLoading();
                            }
                        })
                        .subscribe(
                                meals -> {
                                    if (view != null) {
                                        if (meals == null || meals.isEmpty()) {
                                            view.showEmptyState();
                                        } else {
                                            view.showSearchResults(meals);
                                        }
                                    }
                                },
                                error -> {
                                    if (view != null) {
                                        view.showError(
                                                R.string.error_load_meals_ingredient,
                                                error.getMessage()
                                        );
                                    }
                                }
                        )
        );
    }

    @Override
    public void searchMealsByArea(String areaName) {
        if (areaName == null || areaName.trim().isEmpty()) {
            return;
        }

        if (view != null) {
            view.showLoading();
        }

        disposables.add(
                repository.getMealsByArea(areaName.trim())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doFinally(() -> {
                            if (view != null) {
                                view.hideLoading();
                            }
                        })
                        .subscribe(
                                meals -> {
                                    if (view != null) {
                                        if (meals == null || meals.isEmpty()) {
                                            view.showEmptyState();
                                        } else {
                                            view.showSearchResults(meals);
                                        }
                                    }
                                },
                                error -> {
                                    if (view != null) {
                                        view.showError(
                                                R.string.error_load_meals_area,
                                                error.getMessage()
                                        );
                                    }
                                }
                        )
        );
    }

    @Override
    public void onCategoryClicked(String categoryName) {
        if (categoryName != null && !categoryName.isEmpty()) {
            searchMealsByCategory(categoryName);
        }
    }

    @Override
    public void onIngredientClicked(String ingredientName) {
        if (ingredientName != null && !ingredientName.isEmpty()) {
            searchMealsByIngredient(ingredientName);
        }
    }

    @Override
    public void onAreaClicked(String areaName) {
        if (areaName != null && !areaName.isEmpty()) {
            searchMealsByArea(areaName);
        }
    }

    @Override
    public void onMealClicked(String mealId) {
        if (mealId != null && !mealId.isEmpty() && view != null) {
            view.navigateToMealDetails(mealId);
        }
    }

    @Override
    public void clearSearch() {
        loadInitialData();
    }
}