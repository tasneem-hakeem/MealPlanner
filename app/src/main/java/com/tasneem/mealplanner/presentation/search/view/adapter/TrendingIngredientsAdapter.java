package com.tasneem.mealplanner.presentation.search.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.tasneem.mealplanner.R;
import com.tasneem.mealplanner.data.datasource.meals.model.Ingredient;
import com.tasneem.mealplanner.databinding.ItemChipTrendingBinding;

import java.util.ArrayList;
import java.util.List;

public class TrendingIngredientsAdapter extends RecyclerView.Adapter<TrendingIngredientsAdapter.ViewHolder> {

    private List<Ingredient> ingredients = new ArrayList<>();
    private final OnIngredientClickListener listener;

    public interface OnIngredientClickListener {
        void onIngredientClick(Ingredient ingredient);
    }

    public TrendingIngredientsAdapter(OnIngredientClickListener listener) {
        this.listener = listener;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients != null ? ingredients : new ArrayList<>();
        notifyDataSetChanged();
    }

    public void clearIngredients() {
        this.ingredients.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemChipTrendingBinding binding = ItemChipTrendingBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(ingredients.get(position), position);
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemChipTrendingBinding binding;

        ViewHolder(ItemChipTrendingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Ingredient ingredient, int position) {
            binding.getRoot();
            Chip chip = binding.getRoot();
            chip.setText(ingredient.getName());
            chip.setOnClickListener(v -> listener.onIngredientClick(ingredient));

            if (position == 0) {
                chip.setChipBackgroundColorResource(R.color.primary_10);
                chip.setChipStrokeColorResource(R.color.primary);
                chip.setChipStrokeWidth(2f);
                chip.setChipIconResource(R.drawable.ic_fire);
                chip.setChipIconTintResource(R.color.primary);
            } else {
                chip.setChipBackgroundColorResource(R.color.surface);
                chip.setChipStrokeColorResource(R.color.outline);
                chip.setChipStrokeWidth(1f);
                chip.setChipIcon(null);
            }
        }
    }
}