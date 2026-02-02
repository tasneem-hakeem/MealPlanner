package com.tasneem.mealplanner;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.tasneem.mealplanner.data.datasource.meals.remote.MealsRemoteDatasource;
import com.tasneem.mealplanner.data.datasource.meals.remote.MealsRemoteDatasourceImpl;
import com.tasneem.mealplanner.data.datasource.meals.remote.ResultCallback;
import com.tasneem.mealplanner.data.api.RetrofitClient;
import com.tasneem.mealplanner.data.datasource.meals.remote.dto.categorieslist.CategoriesDto;
import com.tasneem.mealplanner.data.datasource.meals.remote.dto.meal.MealDto;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    MealsRemoteDatasource mealsRemoteDatasource = new MealsRemoteDatasourceImpl(RetrofitClient.getService());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mealsRemoteDatasource.getCategoriesList(
                new ResultCallback<>() {
                    @Override
                    public void onSuccess(List<CategoriesDto> data) {
                        Log.d("Main", "Meals count: " + data.toString());
                    }

                    @Override
                    public void onError(String message) {
                        Log.e("Main", message);
                    }
                }
        );
    }
}