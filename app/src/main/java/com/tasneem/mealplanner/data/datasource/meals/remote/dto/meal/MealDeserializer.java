package com.tasneem.mealplanner.data.datasource.meals.remote.dto.meal;

import android.util.Log;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MealDeserializer implements JsonDeserializer<MealDto> {

    @Override
    public MealDto deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        JsonObject obj = json.getAsJsonObject();
        MealDto meal = new MealDto(
                getAsStringSafe(obj, "idMeal"),
                getAsStringSafe(obj, "strMeal"),
                getAsStringSafe(obj, "strCategory"),
                getAsStringSafe(obj, "strArea"),
                getAsStringSafe(obj, "strInstructions"),
                getAsStringSafe(obj, "strMealThumb"),
                getAsStringSafe(obj, "strYoutube"),
                getAsStringSafe(obj, "strSource"),
                getAsStringSafe(obj, "dateModified")
        );

        List<MealIngredientDto> ingredients = new ArrayList<>();

        for (int i = 1; i <= 20; i++) {
            String ingredientKey = "strIngredient" + i;
            String measureKey = "strMeasure" + i;

            String ingredient = getAsStringSafe(obj, ingredientKey);
            String measure = getAsStringSafe(obj, measureKey);

            Log.d("MealDeserializer", "ingredient = " + ingredient);
            Log.d("MealDeserializer", "measure = " + measure);

            if (ingredient != null && !ingredient.trim().isEmpty()) {
                ingredients.add(
                        new MealIngredientDto(
                                ingredient.trim(),
                                measure != null ? measure.trim() : ""
                        )
                );
            }
        }

        meal.setIngredients(ingredients);
        return meal;
    }

    private String getAsStringSafe(JsonObject obj, String key) {
        if (!obj.has(key) || obj.get(key).isJsonNull()) {
            return null;
        }
        return obj.get(key).getAsString();
    }
}
