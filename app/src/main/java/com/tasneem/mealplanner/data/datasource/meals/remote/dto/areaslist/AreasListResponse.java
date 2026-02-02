package com.tasneem.mealplanner.data.datasource.meals.remote.dto.areaslist;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AreasListResponse {
    @SerializedName("meals") private List<AreasDto> areas;

    public AreasListResponse(List<AreasDto> areas) {
        this.areas = areas;
    }

    public List<AreasDto> getAreas() {
        return areas;
    }

    public void setAreas(List<AreasDto> areas) {
        this.areas = areas;
    }
}