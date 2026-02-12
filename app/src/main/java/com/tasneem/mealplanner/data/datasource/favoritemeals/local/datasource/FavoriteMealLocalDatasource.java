package com.tasneem.mealplanner.data.datasource.favoritemeals.local.datasource;

import android.content.Context;

import com.tasneem.mealplanner.data.datasource.favoritemeals.local.dao.FavoriteMealDao;
import com.tasneem.mealplanner.data.db.AppDatabase;
import com.tasneem.mealplanner.data.db.entity.FavoriteMeal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class FavoriteMealLocalDatasource {
    private final FavoriteMealDao favoriteMealDao;

    public FavoriteMealLocalDatasource(Context context) {
        this.favoriteMealDao = AppDatabase.getInstance(context).favoriteMealDao();
    }

    public Completable deleteFavoriteById(String id) {
        return favoriteMealDao.deleteFavoriteById(id);
    }

    public Completable insertFavoriteMeal(FavoriteMeal meal) {
        return favoriteMealDao.insertFavoriteMeal(meal);
    }

    public Flowable<List<FavoriteMeal>> getAllFavorites() {
        return favoriteMealDao.getAllFavorites();
    }

    public Single<FavoriteMeal> getFavoriteById(String id) {
        return favoriteMealDao.getFavoriteById(id);
    }
}
