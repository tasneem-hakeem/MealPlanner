package com.tasneem.mealplanner.data.datasource.meals.remote.dto.meal;

public class MealIngredientDto {
    private String name;
    private String measure;

    public MealIngredientDto(String name, String measure) {
        this.name = name;
        this.measure = measure;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }
}