package com.tasneem.mealplanner.presentation.planned.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.tasneem.mealplanner.R;
import com.tasneem.mealplanner.data.datasource.meals.model.Meal;
import com.tasneem.mealplanner.databinding.ItemPlannedMealBinding;
import com.tasneem.mealplanner.presentation.utils.GlideUtil;

import java.util.ArrayList;
import java.util.List;

public class PlannedMealsAdapter extends RecyclerView.Adapter<PlannedMealsAdapter.MealViewHolder> {

    private List<Meal> meals = new ArrayList<>();
    private OnMealClickListener listener;

    public interface OnMealClickListener {
        void onMealClick(Meal meal);
        void onDeleteClick(Meal meal);
    }

    public PlannedMealsAdapter(OnMealClickListener listener) {
        this.listener = listener;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_planned_meal, parent, false);
        return new MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        holder.bind(meals.get(position));
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    class MealViewHolder extends RecyclerView.ViewHolder {
        private final MaterialCardView cardView;
        private final ImageView mealImage;
        private final TextView mealName;
        private final TextView mealCategory;
        private final TextView mealCountry;
        private final ImageView deleteButton;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.mealCard);
            mealImage = itemView.findViewById(R.id.mealImage);
            mealName = itemView.findViewById(R.id.mealName);
            mealCategory = itemView.findViewById(R.id.mealCategory);
            mealCountry = itemView.findViewById(R.id.mealCountry);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }

        public void bind(Meal meal) {
            mealName.setText(meal.getName());
            mealCategory.setText(meal.getCategory());
            mealCountry.setText(meal.getOriginCountry());

            GlideUtil.loadImage(
                    itemView,
                    meal.getImageUrl(),
                    mealImage
            );
            cardView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onMealClick(meal);
                }
            });

            deleteButton.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onDeleteClick(meal);
                }
            });
        }
    }
}