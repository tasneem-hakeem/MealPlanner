package com.tasneem.mealplanner.datasource.remote.dto.meal;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MealDto {
    @SerializedName("idMeal") private String id;
    @SerializedName("strMeal") private String name;
    @SerializedName("strCategory") private String category;
    @SerializedName("strArea") private String originCountry;
    @SerializedName("strInstructions") private String steps;
    @SerializedName("strMealThumb") private String imageUrl;
    @SerializedName("strYoutube") private String videoUrl;
    @SerializedName("strSource") private String sourceUrl;
    @SerializedName("dateModified") private String lastEditTime;
    private List<MealIngredientDto> ingredients;

    public MealDto(String id, String name, String category, String originCountry, String steps, String imageUrl, String videoUrl, String sourceUrl, String lastEditTime) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.originCountry = originCountry;
        this.steps = steps;
        this.imageUrl = imageUrl;
        this.videoUrl = videoUrl;
        this.sourceUrl = sourceUrl;
        this.lastEditTime = lastEditTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(String originCountry) {
        this.originCountry = originCountry;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(String lastEditTime) {
        this.lastEditTime = lastEditTime;
    }

    public List<MealIngredientDto> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<MealIngredientDto> ingredients) {
        this.ingredients = ingredients;
    }

    /*@NonNull
    @Override
    public String toString() {
        return "MealDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", originCountry='" + originCountry + '\'' +
                ", steps='" + steps + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", sourceUrl='" + sourceUrl + '\'' +
                ", lastEditTime='" + lastEditTime + '\'' +
                ", ingredients=" + ingredients +
                '}';
    }*/
}
