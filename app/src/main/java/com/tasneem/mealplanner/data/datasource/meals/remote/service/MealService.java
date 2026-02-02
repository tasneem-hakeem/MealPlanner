package com.tasneem.mealplanner.data.datasource.meals.remote.service;

import com.tasneem.mealplanner.data.datasource.meals.remote.dto.areaslist.AreasListResponse;
import com.tasneem.mealplanner.data.datasource.meals.remote.dto.categorieslist.CategoriesListResponse;
import com.tasneem.mealplanner.data.datasource.meals.remote.dto.category.CategoryResponse;
import com.tasneem.mealplanner.data.datasource.meals.remote.dto.ingredient.IngredientsResponse;
import com.tasneem.mealplanner.data.datasource.meals.remote.dto.meal.MealResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealService {
    @GET("random.php")
    Call<MealResponse> getRandomMeal();

    @GET("search.php")
    Call<MealResponse> getMealsByName(@Query("s") String name);

    @GET("search.php")
    Call<MealResponse> getMealsByFirstLetter(@Query("f") char firstLetter);

    @GET("lookup.php")
    Call<MealResponse> getMealDetailsById(@Query("i") String id);

    @GET("filter.php")
    Call<MealResponse> getMealsByCategory(@Query("c") String category);

    @GET("filter.php")
    Call<MealResponse> getMealsByArea(@Query("a") String area);

    @GET("filter.php")
    Call<MealResponse> getMealsByMainIngredient(@Query("i") String ingredient);

    @GET("categories.php")
    Call<CategoryResponse> getAllCategories();

    @GET("list.php")
    Call<IngredientsResponse> getAllIngredients(@Query("i") String list);

    @GET("list.php")
    Call<CategoriesListResponse> getCategoriesList(@Query("c") String list);

    @GET("list.php")
    Call<AreasListResponse> getAreasList(@Query("a") String list);
}