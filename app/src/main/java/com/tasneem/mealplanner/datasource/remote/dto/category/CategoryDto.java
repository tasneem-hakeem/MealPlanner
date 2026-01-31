package com.tasneem.mealplanner.datasource.remote.dto.category;

import com.google.gson.annotations.SerializedName;

public class CategoryDto {
    @SerializedName("idCategory") private String id;
    @SerializedName("strCategory") private String name;
    @SerializedName("strCategoryThumb") private String imageUrl;
    @SerializedName("strCategoryDescription") private String description;

    public CategoryDto(String id, String name, String imageUrl, String description) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.description = description;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
