package com.tasneem.mealplanner.datasource.remote.dto.categorieslist;

import java.util.List;

public class CategoriesListResponse {
    private List<CategoriesDto>  categories;

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