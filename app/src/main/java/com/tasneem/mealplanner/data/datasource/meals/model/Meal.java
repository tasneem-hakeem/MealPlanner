package com.tasneem.mealplanner.data.datasource.meals.model;

import java.util.List;

public class Meal {
    private final String id;
    private final String name;
    private final String category;
    private final String originCountry;
    private final String steps;
    private final String imageUrl;
    private final String videoUrl;
    private final String sourceUrl;
    private final String lastEditTime;
    private final List<Ingredient> ingredients;

    public Meal(String id, String name, String category, String originCountry, String steps, String imageUrl, String videoUrl, String sourceUrl, String lastEditTime, List<Ingredient> ingredients) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.originCountry = originCountry;
        this.steps = steps;
        this.imageUrl = imageUrl;
        this.videoUrl = videoUrl;
        this.sourceUrl = sourceUrl;
        this.lastEditTime = lastEditTime;
        this.ingredients = ingredients;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getOriginCountry() {return originCountry;}

    public String getSteps() {return steps;}

    public String getImageUrl() {
        return imageUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public String getLastEditTime() {
        return lastEditTime;
    }

    public List<Ingredient> getIngredients() {return ingredients;}
}
