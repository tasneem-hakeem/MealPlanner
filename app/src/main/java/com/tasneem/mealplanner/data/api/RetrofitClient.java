package com.tasneem.mealplanner.data.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tasneem.mealplanner.data.datasource.meals.remote.service.MealService;
import com.tasneem.mealplanner.data.datasource.meals.remote.dto.meal.MealDeserializer;
import com.tasneem.mealplanner.data.datasource.meals.remote.dto.meal.MealDto;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit;
    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";

    private RetrofitClient() {}

    static Gson gson = new GsonBuilder()
            .registerTypeAdapter(MealDto.class, new MealDeserializer())
            .create();

    private static Retrofit getInstance() {
        synchronized (RetrofitClient.class) {
            if (retrofit == null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                        .build();
            }
        }
        return retrofit;
    }

    public static MealService getService() {
        return getInstance().create(MealService.class);
    }
}
