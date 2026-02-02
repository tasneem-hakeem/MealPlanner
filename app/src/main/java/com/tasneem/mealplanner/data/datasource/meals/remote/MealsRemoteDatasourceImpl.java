package com.tasneem.mealplanner.data.datasource.meals.remote;

import androidx.annotation.NonNull;

import com.tasneem.mealplanner.data.datasource.meals.remote.service.MealService;
import com.tasneem.mealplanner.data.datasource.meals.remote.dto.areaslist.AreasDto;
import com.tasneem.mealplanner.data.datasource.meals.remote.dto.areaslist.AreasListResponse;
import com.tasneem.mealplanner.data.datasource.meals.remote.dto.categorieslist.CategoriesDto;
import com.tasneem.mealplanner.data.datasource.meals.remote.dto.categorieslist.CategoriesListResponse;
import com.tasneem.mealplanner.data.datasource.meals.remote.dto.category.CategoryDto;
import com.tasneem.mealplanner.data.datasource.meals.remote.dto.category.CategoryResponse;
import com.tasneem.mealplanner.data.datasource.meals.remote.dto.ingredient.IngredientDto;
import com.tasneem.mealplanner.data.datasource.meals.remote.dto.ingredient.IngredientsResponse;
import com.tasneem.mealplanner.data.datasource.meals.remote.dto.meal.MealDto;
import com.tasneem.mealplanner.data.datasource.meals.remote.dto.meal.MealResponse;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealsRemoteDatasourceImpl implements MealsRemoteDatasource {
    private final MealService mealService;

    private static final String LIST = "list";


    public MealsRemoteDatasourceImpl(MealService mealService) {
        this.mealService = mealService;
    }

    @Override
    public void getRandomMeal(ResultCallback<List<MealDto>> callback) {
        executeCall(
                mealService.getRandomMeal(),
                MealResponse::getMeals,
                callback
        );
    }

    @Override
    public void getMealsByName(String name, ResultCallback<List<MealDto>> callback) {
        executeCall(
                mealService.getMealsByName(name),
                MealResponse::getMeals,
                callback
        );
    }

    @Override
    public void getMealsByFirstLetter(char firstLetter, ResultCallback<List<MealDto>> callback) {
        executeCall(
                mealService.getMealsByFirstLetter(firstLetter),
                MealResponse::getMeals,
                callback
        );
    }

    @Override
    public void getMealDetailsById(String id, ResultCallback<List<MealDto>> callback) {
        executeCall(
                mealService.getMealDetailsById(id),
                MealResponse::getMeals,
                callback
        );
    }

    @Override
    public void getMealsByCategory(String category, ResultCallback<List<MealDto>> callback) {
        executeCall(
                mealService.getMealsByCategory(category),
                MealResponse::getMeals,
                callback
        );
    }

    @Override
    public void getMealsByArea(String area, ResultCallback<List<MealDto>> callback) {
        executeCall(
                mealService.getMealsByArea(area),
                MealResponse::getMeals,
                callback
        );
    }

    @Override
    public void getMealsByMainIngredient(String ingredient, ResultCallback<List<MealDto>> callback) {
        executeCall(
                mealService.getMealsByMainIngredient(ingredient),
                MealResponse::getMeals,
                callback
        );
    }

    @Override
    public void getAllCategories(ResultCallback<List<CategoryDto>> callback) {
        executeCall(
                mealService.getAllCategories(),
                CategoryResponse::getCategories,
                callback
        );
    }

    @Override
    public void getAllIngredients(ResultCallback<List<IngredientDto>> callback) {
        executeCall(
                mealService.getAllIngredients(LIST),
                IngredientsResponse::getIngredients,
                callback
        );
    }

    @Override
    public void getCategoriesList(ResultCallback<List<CategoriesDto>> callback) {
        executeCall(
                mealService.getCategoriesList(LIST),
                CategoriesListResponse::getCategories,
                callback
        );
    }

    @Override
    public void getAreasList(ResultCallback<List<AreasDto>> callback) {
        executeCall(
                mealService.getAreasList(LIST),
                AreasListResponse::getAreas,
                callback
        );
    }

    private <R, T> void executeCall(
            Call<R> call,
            ResponseMapper<R, T> mapper,
            ResultCallback<T> callback
    ) {
        call.enqueue(new Callback<>() {

            @Override
            public void onResponse(@NonNull Call<R> call, @NonNull Response<R> response) {

                if (response.isSuccessful() && response.body() != null) {
                    try {
                        T data = mapper.map(response.body());
                        callback.onSuccess(data);
                    } catch (Exception e) {
                        callback.onError(
                                e.getMessage() != null ? e.getMessage() : "Failed to parse response"
                        );
                    }
                } else {
                    callback.onError("Empty response from server");
                }
            }

            @Override
            public void onFailure(@NonNull Call<R> call, @NonNull Throwable t) {
                if (t instanceof IOException) {
                    callback.onError("Network error. Please check your connection.");
                } else {
                    callback.onError("Unexpected error occurred");
                }
            }
        });
    }
}