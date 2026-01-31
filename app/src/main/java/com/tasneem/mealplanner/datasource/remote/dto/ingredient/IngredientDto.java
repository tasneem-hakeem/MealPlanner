package com.tasneem.mealplanner.datasource.remote.dto.ingredient;

import com.google.gson.annotations.SerializedName;

public class IngredientDto {
    @SerializedName("idIngredient") private String id;
    @SerializedName("strIngredient") private String name;
    @SerializedName("strDescription") private String description;
    @SerializedName("strThumb") private String imageUrl;
    @SerializedName("strType") private String type;

    public IngredientDto(String id, String name, String description, String imageUrl, String type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.type = type;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
