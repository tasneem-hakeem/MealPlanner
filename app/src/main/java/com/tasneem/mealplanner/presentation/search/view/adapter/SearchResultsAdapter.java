package com.tasneem.mealplanner.presentation.search.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tasneem.mealplanner.data.datasource.model.Meal;
import com.tasneem.mealplanner.databinding.ItemFavoriteMealBinding;
import com.tasneem.mealplanner.presentation.utils.GetFlagsUtil;
import com.tasneem.mealplanner.presentation.utils.GlideUtil;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.ViewHolder> {

    private List<Meal> meals = new ArrayList<>();
    private final OnMealClickListener listener;

    public interface OnMealClickListener {
        void onMealClick(Meal meal);
    }

    public SearchResultsAdapter(OnMealClickListener listener) {
        this.listener = listener;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals != null ? meals : new ArrayList<>();
        notifyDataSetChanged();
    }

    public void clearMeals() {
        this.meals.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFavoriteMealBinding binding = ItemFavoriteMealBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(meals.get(position));
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemFavoriteMealBinding binding;

        ViewHolder(ItemFavoriteMealBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Meal meal) {
            binding.tvFavoriteMealTitle.setText(meal.getName());
            binding.tvFavCategory.setText(meal.getCategory());
            binding.tvFavCountry.setText(meal.getOriginCountry());

            GlideUtil.loadImage(
                    binding.getRoot(),
                    meal.getImageUrl(),
                    binding.ivRecipeImage
            );

            GlideUtil.loadImage(
                    binding.getRoot(),
                    GetFlagsUtil.getFlagUrl(meal.getOriginCountry()),
                    binding.ivCountryIcon
            );

            binding.getRoot().setOnClickListener(v -> listener.onMealClick(meal));
        }
    }
}