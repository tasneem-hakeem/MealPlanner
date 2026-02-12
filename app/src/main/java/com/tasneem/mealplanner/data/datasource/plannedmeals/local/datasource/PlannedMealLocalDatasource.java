package com.tasneem.mealplanner.data.datasource.plannedmeals.local.datasource;

import android.content.Context;

import com.tasneem.mealplanner.data.datasource.plannedmeals.local.dao.PlannedMealDao;
import com.tasneem.mealplanner.data.db.AppDatabase;
import com.tasneem.mealplanner.data.db.entity.PlannedMeal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class PlannedMealLocalDatasource {
    private final PlannedMealDao plannedMealDao;

    public PlannedMealLocalDatasource(Context context) {
        this.plannedMealDao = AppDatabase.getInstance(context).plannedMealDao();
    }

    public Completable deletePlannedById(String id) {
        return plannedMealDao.deletePlannedMealById(id);
    }

    public Completable insertPlannedMeal(PlannedMeal meal) {
        return plannedMealDao.insertPlannedMeal(meal);
    }

    public Flowable<List<PlannedMeal>> getAllPlannedMeals() {
        return plannedMealDao.getAllPlannedMeals();
    }

    public Single<PlannedMeal> getPlannedMealById(String id) {
        return plannedMealDao.getPlannedMealById(id);
    }

    public Flowable<List<PlannedMeal>> getPlannedMealsByDate(String date) {
        return plannedMealDao.getMealsByDate(date);
    }
}
