package com.tasneem.mealplanner.data.datasource.meals.model;

public class Ingredient {

    private final String id;
    private final String name;
    private final String measure;
    private final String description;
    private final String imageUrl;
    private final String type;

    public Ingredient(
            String id,
            String name,
            String measure,
            String description,
            String imageUrl,
            String type
    ) {
        this.id = id;
        this.name = name;
        this.measure = measure;
        this.description = description;
        this.imageUrl = imageUrl;
        this.type = type;
    }

    public String getId() {return id;}

    public String getName() {return name;}

    public String getMeasure() {return measure;}

    public String getDescription() {return description;}

    public String getImageUrl() {return imageUrl;}

    public String getType() {return type;}
}
