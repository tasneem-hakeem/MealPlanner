package com.tasneem.mealplanner.data.datasource.meals.repository;

import android.app.Application;

import com.tasneem.mealplanner.data.datasource.meals.favorites.local.datasource.FavoriteMealLocalDatasource;
import com.tasneem.mealplanner.data.datasource.meals.favorites.mappers.FavoriteMealEntityMapper;
import com.tasneem.mealplanner.data.datasource.meals.mapper.CategoryMapper;
import com.tasneem.mealplanner.data.datasource.meals.mapper.IngredientMapper;
import com.tasneem.mealplanner.data.datasource.meals.mapper.MealsMapper;
import com.tasneem.mealplanner.data.datasource.meals.model.Category;
import com.tasneem.mealplanner.data.datasource.meals.model.Ingredient;
import com.tasneem.mealplanner.data.datasource.meals.model.Meal;
import com.tasneem.mealplanner.data.datasource.meals.remote.MealsRemoteDatasource;
import com.tasneem.mealplanner.data.datasource.meals.remote.MealsRemoteDatasourceImpl;
import com.tasneem.mealplanner.data.datasource.meals.remote.dto.areaslist.AreasDto;
import com.tasneem.mealplanner.data.datasource.meals.remote.dto.categorieslist.CategoriesDto;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class MealsRepositoryImpl implements MealsRepository {

    private final MealsRemoteDatasource remote;
    private final FavoriteMealLocalDatasource favoriteDatasource;

    public MealsRepositoryImpl(Application application) {
        this.remote = new MealsRemoteDatasourceImpl();
        this.favoriteDatasource = new FavoriteMealLocalDatasource(application.getApplicationContext());
    }

    @Override
    public Single<Meal> getRandomMeal() {
        return remote.getRandomMeal()
                .map(response -> MealsMapper.fromList(response.getMeals()).get(0));
    }

    @Override
    public Single<List<Meal>> getMealsByName(String name) {
        return remote.getMealsByName(name)
                .map(response -> MealsMapper.fromList(response.getMeals()));
    }

    @Override
    public Single<List<Meal>> getMealsByFirstLetter(char firstLetter) {
        return remote.getMealsByFirstLetter(firstLetter)
                .map(response -> MealsMapper.fromList(response.getMeals()));
    }

    @Override
    public Single<Meal> getMealDetailsById(String id) {
        return remote.getMealDetailsById(id)
                .map(response -> MealsMapper.fromList(response.getMeals()).get(0));
    }

    @Override
    public Single<List<Meal>> getMealsByCategory(String category) {
        return remote.getMealsByCategory(category)
                .map(response -> MealsMapper.fromList(response.getMeals()));
    }

    @Override
    public Single<List<Meal>> getMealsByArea(String area) {
        return remote.getMealsByArea(area)
                .map(response -> MealsMapper.fromList(response.getMeals()));
    }

    @Override
    public Single<List<Meal>> getMealsByMainIngredient(String ingredient) {
        return remote.getMealsByMainIngredient(ingredient)
                .map(response -> MealsMapper.fromList(response.getMeals()));
    }

    @Override
    public Single<List<Category>> getAllCategories() {
        return remote.getAllCategories().map(response -> CategoryMapper.fromList(response.getCategories()));
    }

    @Override
    public Single<List<Ingredient>> getAllIngredients() {
        return remote.getAllIngredients().map(response -> IngredientMapper.fromIngredients(response.getIngredients()));
    }

    @Override
    public Single<List<String>> getCategoriesList() {
        return remote.getCategoriesList().map(response -> response.getCategories()
                .stream()
                .map(CategoriesDto::getCategoryName)
                .collect(Collectors.toList()));
    }

    @Override
    public Single<List<String>> getAreasList() {
        return remote.getAreasList().map(response -> response.getAreas()
                .stream()
                .map(AreasDto::getAreaName)
                .collect(Collectors.toList()));
    }

    @Override
    public Flowable<List<Meal>> getAllFavorites() {
        return favoriteDatasource.getAllFavorites().map(FavoriteMealEntityMapper::fromList);
    }

    @Override
    public Single<Meal> getFavoriteById(String id) {
        return favoriteDatasource.getFavoriteById(id).map(FavoriteMealEntityMapper::from);
    }

    @Override
    public Completable deleteFavoriteById(String id) {
        return favoriteDatasource.deleteFavoriteById(id);
    }

    @Override
    public Completable addMealToFavorite(Meal meal) {
        return favoriteDatasource.insertFavoriteMeal(FavoriteMealEntityMapper.to(meal));
    }
}
