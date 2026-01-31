package com.tasneem.mealplanner.datasource.remote.dto.meal;

import androidx.annotation.NonNull;

public class MealIngredientDto {
    private String name;
    private String measure;

    public MealIngredientDto(String name, String measure) {
        this.name = name;
        this.measure = measure;
    }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public String getMeasure() { return measure; }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    /*@NonNull
    @Override
    public String toString() {
        return "MealIngredientDto{" +
                "name='" + name + '\'' +
                ", measure='" + measure + '\'' +
                '}';
    }*/
}