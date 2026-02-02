package com.tasneem.mealplanner.data.datasource.meals.remote.dto.areaslist;

import com.google.gson.annotations.SerializedName;

public class AreasDto {
    @SerializedName("strArea")
    private String areaName;

    public AreasDto(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
}