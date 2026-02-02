package com.tasneem.mealplanner.data.datasource.meals.remote;

public interface ResultCallback<T> {
    void onSuccess(T data);
    void onError(String message);
}
