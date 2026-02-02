package com.tasneem.mealplanner.data.datasource.meals.remote;

public interface ResponseMapper<R, T> {
    T map(R response);
}
