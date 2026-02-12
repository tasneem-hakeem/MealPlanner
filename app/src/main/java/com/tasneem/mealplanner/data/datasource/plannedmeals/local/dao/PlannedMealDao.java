package com.tasneem.mealplanner.data.datasource.plannedmeals.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.tasneem.mealplanner.data.db.entity.PlannedMeal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface PlannedMealDao {
    @Query("SELECT * FROM planned_meals")
    Flowable<List<PlannedMeal>> getAllPlannedMeals();

    @Query("SELECT * FROM planned_meals WHERE id = :id")
    Single<PlannedMeal> getPlannedMealById(String id);

    @Query("DELETE FROM planned_meals WHERE id = :id")
    Completable deletePlannedMealById(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertPlannedMeal(PlannedMeal meal);
}
