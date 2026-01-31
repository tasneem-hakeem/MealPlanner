package com.tasneem.mealplanner.datasource.remote.dto.ingredient;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IngredientsResponse {
    @SerializedName("meals") private List<IngredientDto> ingredients;

    public IngredientsResponse(List<IngredientDto> ingredients) {
        this.ingredients = ingredients;
    }

    public List<IngredientDto> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientDto> ingredients) {
        this.ingredients = ingredients;
    }
}
