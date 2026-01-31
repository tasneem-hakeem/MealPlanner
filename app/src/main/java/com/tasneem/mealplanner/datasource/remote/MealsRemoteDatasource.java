package com.tasneem.mealplanner.datasource.remote;

import com.tasneem.mealplanner.datasource.remote.dto.areaslist.AreasDto;
import com.tasneem.mealplanner.datasource.remote.dto.categorieslist.CategoriesDto;
import com.tasneem.mealplanner.datasource.remote.dto.category.CategoryDto;
import com.tasneem.mealplanner.datasource.remote.dto.ingredient.IngredientDto;
import com.tasneem.mealplanner.datasource.remote.dto.meal.MealDto;

import java.util.List;

public interface MealsRemoteDatasource {

    void getRandomMeal(ResultCallback<List<MealDto>> callback);

    void getMealsByName(String name, ResultCallback<List<MealDto>> callback);

    void getMealsByFirstLetter(char firstLetter, ResultCallback<List<MealDto>> callback);

    void getMealDetailsById(String id, ResultCallback<List<MealDto>> callback);

    void getMealsByCategory(String category, ResultCallback<List<MealDto>> callback);

    void getMealsByArea(String area, ResultCallback<List<MealDto>> callback);

    void getMealsByMainIngredient(String ingredient, ResultCallback<List<MealDto>> callback);

    void getAllCategories(ResultCallback<List<CategoryDto>> callback);

    void getAllIngredients(ResultCallback<List<IngredientDto>> callback);

    void getCategoriesList(ResultCallback<List<CategoriesDto>> callback);

    void getAreasList(ResultCallback<List<AreasDto>> callback);
}