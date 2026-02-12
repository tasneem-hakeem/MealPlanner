package com.tasneem.mealplanner.presentation.planned.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tasneem.mealplanner.data.datasource.meals.model.Meal;
import com.tasneem.mealplanner.databinding.ItemPlannedMealBinding;
import com.tasneem.mealplanner.presentation.utils.GlideUtil;

import java.util.ArrayList;
import java.util.List;

public class PlannedMealsAdapter extends RecyclerView.Adapter<PlannedMealsAdapter.MealViewHolder> {

    private List<Meal> meals;
    private final OnMealDeleteListener deleteListener;

    public interface OnMealDeleteListener {
        void onDeleteMeal(Meal meal);
    }

    public PlannedMealsAdapter(OnMealDeleteListener deleteListener) {
        this.meals = new ArrayList<>();
        this.deleteListener = deleteListener;
    }

    public void updateMeals(List<Meal> newMeals) {
        this.meals = newMeals;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPlannedMealBinding binding = ItemPlannedMealBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new MealViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        Meal meal = meals.get(position);
        holder.bind(meal);
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    class MealViewHolder extends RecyclerView.ViewHolder {

        private final ItemPlannedMealBinding binding;

        public MealViewHolder(@NonNull ItemPlannedMealBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Meal meal) {
            binding.tvMealName.setText(meal.getName());

            GlideUtil.loadImage(
                    itemView,
                    meal.getImageUrl(),
                    binding.ivMealImage
            );

            binding.tvMealTime.setText(meal.getCategory());

            binding.tvMealServing.setText(meal.getOriginCountry());

            binding.btnDeleteMeal.setOnClickListener(v -> {
                if (deleteListener != null) {
                    deleteListener.onDeleteMeal(meal);
                }
            });

            binding.mealCard.setOnClickListener(v -> {
                // TODO: Navigate to meal details
                // You can add a click listener interface similar to delete
            });
        }
    }
}