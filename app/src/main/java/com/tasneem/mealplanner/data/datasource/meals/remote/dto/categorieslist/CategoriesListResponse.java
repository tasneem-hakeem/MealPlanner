package com.tasneem.mealplanner.data.datasource.meals.remote.dto.categorieslist;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoriesListResponse {
    @SerializedName("meals") private List<CategoriesDto> categories;

    public CategoriesListResponse(List<CategoriesDto> categories) {
        this.categories = categories;
    }

    public List<CategoriesDto> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoriesDto> categories) {
        this.categories = categories;
    }
}