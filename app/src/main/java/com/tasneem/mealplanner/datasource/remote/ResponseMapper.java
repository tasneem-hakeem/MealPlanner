package com.tasneem.mealplanner.datasource.remote;

public interface ResponseMapper<R, T> {
    T map(R response);
}
