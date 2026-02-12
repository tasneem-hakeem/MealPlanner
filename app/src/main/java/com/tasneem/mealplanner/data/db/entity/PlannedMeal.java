package com.tasneem.mealplanner.data.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "planned_meals")
public class PlannedMeal {
    @PrimaryKey
    @NonNull
    private final String id;
    private final String name;
    private final String category;
    private final String originCountry;
    private final String steps;
    private final String imageUrl;
    private final String date;

    public PlannedMeal(@NonNull String id, String name, String category, String originCountry, String steps, String imageUrl, String date) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.originCountry = originCountry;
        this.steps = steps;
        this.imageUrl = imageUrl;
        this.date = date;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getOriginCountry() {
        return originCountry;
    }

    public String getSteps() {
        return steps;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDate() {
        return date;
    }
}