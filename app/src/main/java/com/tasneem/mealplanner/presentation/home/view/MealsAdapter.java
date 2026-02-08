package com.tasneem.mealplanner.presentation.home.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tasneem.mealplanner.data.datasource.meals.model.Meal;
import com.tasneem.mealplanner.databinding.ItemMealBinding;
import com.tasneem.mealplanner.presentation.utils.GlideUtil;

import java.util.ArrayList;
import java.util.List;

public class MealsAdapter extends RecyclerView.Adapter<MealsAdapter.MealViewHolder> {
    private List<Meal> meals = new ArrayList<>();
    private final OnMealClickListener listener;

    public MealsAdapter(OnMealClickListener listener) {
        this.listener = listener;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMealBinding binding = ItemMealBinding.inflate(
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
        private final ItemMealBinding binding;

        public MealViewHolder(ItemMealBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Meal meal) {
            binding.tvMealName.setText(meal.getName());
            GlideUtil.loadImage(
                    itemView,
                    meal.getImageUrl(),
                    binding.imgMeal
            );
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onMealClicked(meal.getId());
                }
            });
        }
    }
}
