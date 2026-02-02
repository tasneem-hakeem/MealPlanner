package com.tasneem.mealplanner.data.datasource.meals.remote.dto.meal;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MealResponse {
    @SerializedName("meals") private List<MealDto> meals;

    public MealResponse(List<MealDto> meals) {
        this.meals = meals;
    }

    public List<MealDto> getMeals() {
        return meals;
    }

    public void setMeals(List<MealDto> meals) {
        this.meals = meals;
    }
}