package com.tasneem.mealplanner.data.datasource.meals.favorites.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.tasneem.mealplanner.data.db.entity.FavoriteMeal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface FavoriteMealDao {
    @Query("SELECT * FROM favorites")
    Flowable<List<FavoriteMeal>> getAllFavorites();

    @Query("SELECT * FROM favorites WHERE id = :id")
    Single<FavoriteMeal> getFavoriteById(String id);

    @Query("DELETE FROM favorites WHERE id = :id")
    Completable deleteFavoriteById(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertFavoriteMeal(FavoriteMeal meal);
}
