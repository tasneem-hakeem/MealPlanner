package com.tasneem.mealplanner.datasource.remote;

public interface ResultCallback<T> {
    void onSuccess(T data);
    void onError(String message);
}
