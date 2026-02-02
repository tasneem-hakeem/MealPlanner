package com.tasneem.mealplanner.data.datasource.meals.remote.dto.categorieslist;

import com.google.gson.annotations.SerializedName;

public class CategoriesDto {
    @SerializedName("strCategory") private String categoryName;

    public CategoriesDto(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}