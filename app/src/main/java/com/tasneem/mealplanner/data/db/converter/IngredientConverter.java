package com.tasneem.mealplanner.data.db.converter;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tasneem.mealplanner.data.datasource.meals.model.Ingredient;

import java.lang.reflect.Type;
import java.util.List;

public class IngredientConverter {

    private static final Gson gson = new Gson();

    @TypeConverter
    public static String fromIngredientList(List<Ingredient> ingredients) {
        return gson.toJson(ingredients);
    }

    @TypeConverter
    public static List<Ingredient> toIngredientList(String ingredientsString) {
        Type listType = new TypeToken<List<Ingredient>>() {}.getType();
        return gson.fromJson(ingredientsString, listType);
    }
}