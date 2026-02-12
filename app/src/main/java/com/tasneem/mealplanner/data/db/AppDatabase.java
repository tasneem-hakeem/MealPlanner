package com.tasneem.mealplanner.data.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.tasneem.mealplanner.data.datasource.favoritemeals.local.dao.FavoriteMealDao;
import com.tasneem.mealplanner.data.datasource.plannedmeals.local.dao.PlannedMealDao;
import com.tasneem.mealplanner.data.db.converter.IngredientConverter;
import com.tasneem.mealplanner.data.db.entity.FavoriteMeal;
import com.tasneem.mealplanner.data.db.entity.PlannedMeal;

@Database(entities = {FavoriteMeal.class, PlannedMeal.class}, version = 1)
@TypeConverters({IngredientConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;

    public abstract FavoriteMealDao favoriteMealDao();

    public abstract PlannedMealDao plannedMealDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, AppDatabase.class, "meals_db")
                    .build();
        }
        return INSTANCE;
    }
}
