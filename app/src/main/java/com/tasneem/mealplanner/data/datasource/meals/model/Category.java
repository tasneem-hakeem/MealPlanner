package com.tasneem.mealplanner.data.datasource.meals.model;

public class Category {
    private final String id;
    private final String name;
    private final String imageUrl;
    private final String description;

    public Category(String id, String name, String imageUrl, String description) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDescription() {
        return description;
    }
}
