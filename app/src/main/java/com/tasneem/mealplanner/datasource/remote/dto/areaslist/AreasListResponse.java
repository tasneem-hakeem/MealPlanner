package com.tasneem.mealplanner.datasource.remote.dto.areaslist;

import java.util.List;

public class AreasListResponse {
    private List<AreasDto> areas;

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