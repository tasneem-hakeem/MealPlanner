package com.tasneem.mealplanner.data.datasource.meals.remote;

import com.tasneem.mealplanner.data.api.RetrofitClient;
import com.tasneem.mealplanner.data.datasource.meals.remote.dto.areaslist.AreasListResponse;
import com.tasneem.mealplanner.data.datasource.meals.remote.dto.categorieslist.CategoriesListResponse;
import com.tasneem.mealplanner.data.datasource.meals.remote.dto.category.CategoryResponse;
import com.tasneem.mealplanner.data.datasource.meals.remote.dto.ingredient.IngredientsResponse;
import com.tasneem.mealplanner.data.datasource.meals.remote.dto.meal.MealResponse;
import com.tasneem.mealplanner.data.datasource.meals.remote.service.MealService;

import io.reactivex.rxjava3.core.Single;

public class MealsRemoteDatasourceImpl implements MealsRemoteDatasource {

    private final MealService mealService;
    private static final String LIST = "list";

    public MealsRemoteDatasourceImpl() {
        this.mealService = RetrofitClient.getService();
    }

    @Override
    public Single<MealResponse> getRandomMeal() {
        return mealService.getRandomMeal();
    }

    @Override
    public Single<MealResponse> getMealsByName(String name) {
        return mealService.getMealsByName(name);
    }

    @Override
    public Single<MealResponse> getMealsByFirstLetter(char firstLetter) {
        return mealService.getMealsByFirstLetter(firstLetter);
    }

    @Override
    public Single<MealResponse> getMealDetailsById(String id) {
        return mealService.getMealDetailsById(id);
    }

    @Override
    public Single<MealResponse> getMealsByCategory(String category) {
        return mealService.getMealsByCategory(category);
    }

    @Override
    public Single<MealResponse> getMealsByArea(String area) {
        return mealService.getMealsByArea(area);
    }

    @Override
    public Single<MealResponse> getMealsByMainIngredient(String ingredient) {
        return mealService.getMealsByMainIngredient(ingredient);
    }

    @Override
    public Single<CategoryResponse> getAllCategories() {
        return mealService.getAllCategories();
    }

    @Override
    public Single<IngredientsResponse> getAllIngredients() {
        return mealService.getAllIngredients(LIST);
    }

    @Override
    public Single<CategoriesListResponse> getCategoriesList() {
        return mealService.getCategoriesList(LIST);
    }

    @Override
    public Single<AreasListResponse> getAreasList() {
        return mealService.getAreasList(LIST);
    }
}