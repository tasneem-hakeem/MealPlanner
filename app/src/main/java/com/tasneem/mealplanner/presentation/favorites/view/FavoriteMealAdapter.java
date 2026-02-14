package com.tasneem.mealplanner.presentation.favorites.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tasneem.mealplanner.data.datasource.model.Meal;
import com.tasneem.mealplanner.databinding.ItemFavoriteMealBinding;
import com.tasneem.mealplanner.presentation.home.view.OnMealClickListener;
import com.tasneem.mealplanner.presentation.utils.GetFlagsUtil;
import com.tasneem.mealplanner.presentation.utils.GlideUtil;

import java.util.ArrayList;
import java.util.List;

public class FavoriteMealAdapter extends RecyclerView.Adapter<FavoriteMealAdapter.FavoriteMealViewHolder> {
    private List<Meal> meals = new ArrayList<>();
    private final OnMealClickListener listener;

    public FavoriteMealAdapter(OnMealClickListener listener) {
        this.listener = listener;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoriteMealAdapter.FavoriteMealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFavoriteMealBinding binding = ItemFavoriteMealBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new FavoriteMealAdapter.FavoriteMealViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteMealAdapter.FavoriteMealViewHolder holder, int position) {
        Meal meal = meals.get(position);
        holder.bind(meal);
    }

    @Override
    public int getItemCount() {
        if (meals == null)
            return 0;
        return meals.size();
    }

    class FavoriteMealViewHolder extends RecyclerView.ViewHolder {
        private final ItemFavoriteMealBinding binding;

        public FavoriteMealViewHolder(ItemFavoriteMealBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Meal meal) {
            binding.tvFavoriteMealTitle.setText(meal.getName());
            binding.tvFavCategory.setText(meal.getCategory());
            binding.tvFavCountry.setText(meal.getOriginCountry());
            GlideUtil.loadImage(binding.getRoot(), GetFlagsUtil.getFlagUrl(meal.getOriginCountry()), binding.ivCountryIcon);
            GlideUtil.loadImage(binding.getRoot(), meal.getImageUrl(), binding.ivRecipeImage);
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onMealClicked(meal.getId());
                }
            });
        }
    }
}
