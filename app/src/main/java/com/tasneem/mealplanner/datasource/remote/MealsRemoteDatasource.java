package com.tasneem.mealplanner.datasource.remote;

import androidx.annotation.NonNull;

import com.tasneem.mealplanner.datasource.remote.api.MealService;
import com.tasneem.mealplanner.datasource.remote.dto.areaslist.AreasDto;
import com.tasneem.mealplanner.datasource.remote.dto.areaslist.AreasListResponse;
import com.tasneem.mealplanner.datasource.remote.dto.categorieslist.CategoriesDto;
import com.tasneem.mealplanner.datasource.remote.dto.categorieslist.CategoriesListResponse;
import com.tasneem.mealplanner.datasource.remote.dto.category.CategoryDto;
import com.tasneem.mealplanner.datasource.remote.dto.category.CategoryResponse;
import com.tasneem.mealplanner.datasource.remote.dto.ingredient.IngredientDto;
import com.tasneem.mealplanner.datasource.remote.dto.ingredient.IngredientsResponse;
import com.tasneem.mealplanner.datasource.remote.dto.meal.MealDto;
import com.tasneem.mealplanner.datasource.remote.dto.meal.MealResponse;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealsRemoteDatasource {
    private final MealService mealService;

    public MealsRemoteDatasource(MealService mealService) {
        this.mealService = mealService;
    }

    public void getRandomMeal(ResultCallback<List<MealDto>> callback) {
        executeCall(
                mealService.getRandomMeal(),
                MealResponse::getMeals,
                callback
        );
    }

    public void getMealsByName(String name, ResultCallback<List<MealDto>> callback) {
        executeCall(
                mealService.getMealsByName(name),
                MealResponse::getMeals,
                callback
        );
    }

    public void getMealsByFirstLetter(char firstLetter, ResultCallback<List<MealDto>> callback) {
        executeCall(
                mealService.getMealsByFirstLetter(firstLetter),
                MealResponse::getMeals,
                callback
        );
    }

    public void getMealDetailsById(String id, ResultCallback<List<MealDto>> callback) {
        executeCall(
                mealService.getMealDetailsById(id),
                MealResponse::getMeals,
                callback
        );
    }

    public void getMealsByCategory(String category, ResultCallback<List<MealDto>> callback) {
        executeCall(
                mealService.getMealsByCategory(category),
                MealResponse::getMeals,
                callback
        );
    }

    public void getMealsByArea(String area, ResultCallback<List<MealDto>> callback) {
        executeCall(
                mealService.getMealsByArea(area),
                MealResponse::getMeals,
                callback
        );
    }

    public void getMealsByMainIngredient(String ingredient, ResultCallback<List<MealDto>> callback) {
        executeCall(
                mealService.getMealsByMainIngredient(ingredient),
                MealResponse::getMeals,
                callback
        );
    }

    public void getAllCategories(ResultCallback<List<CategoryDto>> callback) {
        executeCall(
                mealService.getAllCategories(),
                CategoryResponse::getCategories,
                callback
        );
    }

    public void getAllIngredients(ResultCallback<List<IngredientDto>> callback) {
        executeCall(
                mealService.getAllIngredients("list"),
                IngredientsResponse::getIngredients,
                callback
        );
    }

    public void getCategoriesList(ResultCallback<List<CategoriesDto>> callback) {
        executeCall(
                mealService.getCategoriesList("list"),
                CategoriesListResponse::getCategories,
                callback
        );
    }

    public void getAreasList(ResultCallback<List<AreasDto>> callback) {
        executeCall(
                mealService.getAreasList("list"),
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
                        callback.onError("Failed to parse response");
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
