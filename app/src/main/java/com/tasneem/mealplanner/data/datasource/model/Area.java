package com.tasneem.mealplanner.data.datasource.model;

public class Area {
    private final String name;
    private final String flagUrl;

    public Area(String name, String flagUrl) {
        this.name = name;
        this.flagUrl = flagUrl;
    }

    public String getName() {
        return name;
    }

    public String getFlagUrl() {
        return flagUrl;
    }
}