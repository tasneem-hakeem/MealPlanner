package com.tasneem.mealplanner.presentation.mealdetails.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tasneem.mealplanner.data.datasource.meals.model.Ingredient;
import com.tasneem.mealplanner.databinding.ItemIngredientBinding;
import com.tasneem.mealplanner.presentation.utils.GlideUtil;

import java.util.ArrayList;
import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {
    private List<Ingredient> ingredients = new ArrayList<>();

    public IngredientAdapter() {

    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public IngredientAdapter.IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemIngredientBinding binding = ItemIngredientBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new IngredientAdapter.IngredientViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        Ingredient ingredient = ingredients.get(position);
        holder.bind(ingredient);
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    static class IngredientViewHolder extends RecyclerView.ViewHolder {
        private final ItemIngredientBinding binding;

        public IngredientViewHolder(ItemIngredientBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Ingredient ingredient) {
            binding.tvIngredientName.setText(ingredient.getName());
            binding.tvIngredientAmount.setText(ingredient.getMeasure());

            String BASE_IMAGE_URL = "https://www.themealdb.com/images/ingredients/";
            String IMAGE_PREVIEW_SIZE = "https://www.themealdb.com/images/ingredients/";
            GlideUtil.loadImage(itemView,
                    BASE_IMAGE_URL +
                            ingredient.getName() +
                            IMAGE_PREVIEW_SIZE, binding.ivIngredientIcon
            );
        }
    }
}
