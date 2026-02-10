package com.tasneem.mealplanner.presentation.favorites.view;

import static android.view.View.VISIBLE;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.tasneem.mealplanner.R;
import com.tasneem.mealplanner.data.datasource.meals.model.Meal;
import com.tasneem.mealplanner.databinding.FragmentFavoritesBinding;
import com.tasneem.mealplanner.presentation.favorites.presenter.FavoriteMealPresenter;
import com.tasneem.mealplanner.presentation.favorites.presenter.FavoriteMealPresenterImpl;

import java.util.List;

public class FavoritesFragment extends Fragment implements FavoriteMealView {
    private FragmentFavoritesBinding binding;
    private FavoriteMealPresenter presenter;
    private FavoriteMealAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding = FragmentFavoritesBinding.bind(view);
        presenter = new FavoriteMealPresenterImpl(requireActivity().getApplication());
        presenter.attachView(this);
        presenter.onViewStarted();

        adapter = new FavoriteMealAdapter();
        binding.rvFavoriteMeals.setAdapter(adapter);

    }

    @Override
    public void showFavoriteMeals(List<Meal> meals) {
        adapter.setMeals(meals);
    }

    @Override
    public void showError(String message) {
        binding.layoutSomethingWrong.getRoot().setVisibility(VISIBLE);
    }

    @Override
    public void onRemoveFromFavClicked(String mealId) {
        Snackbar.make(binding.getRoot(), R.string.meal_removed_from_favorites_successfully, Snackbar.LENGTH_SHORT).show();
    }
}