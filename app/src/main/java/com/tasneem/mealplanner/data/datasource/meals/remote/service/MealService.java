package com.tasneem.mealplanner.data.datasource.meals.remote.service;

import com.tasneem.mealplanner.data.datasource.meals.remote.dto.areaslist.AreasListResponse;
import com.tasneem.mealplanner.data.datasource.meals.remote.dto.categorieslist.CategoriesListResponse;
import com.tasneem.mealplanner.data.datasource.meals.remote.dto.category.CategoryResponse;
import com.tasneem.mealplanner.data.datasource.meals.remote.dto.ingredient.IngredientsResponse;
import com.tasneem.mealplanner.data.datasource.meals.remote.dto.meal.MealResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealService {
    @GET("random.php")
    Single<MealResponse> getRandomMeal();

    @GET("search.php")
    Single<MealResponse> getMealsByName(@Query("s") String name);

    @GET("search.php")
    Single<MealResponse> getMealsByFirstLetter(@Query("f") char firstLetter);

    @GET("lookup.php")
    Single<MealResponse> getMealDetailsById(@Query("i") String id);

    @GET("filter.php")
    Single<MealResponse> getMealsByCategory(@Query("c") String category);

    @GET("filter.php")
    Single<MealResponse> getMealsByArea(@Query("a") String area);

    @GET("filter.php")
    Single<MealResponse> getMealsByMainIngredient(@Query("i") String ingredient);

    @GET("categories.php")
    Single<CategoryResponse> getAllCategories();

    @GET("list.php")
    Single<IngredientsResponse> getAllIngredients(@Query("i") String list);

    @GET("list.php")
    Single<CategoriesListResponse> getCategoriesList(@Query("c") String list);

    @GET("list.php")
    Single<AreasListResponse> getAreasList(@Query("a") String list);
}