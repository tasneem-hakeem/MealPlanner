package com.tasneem.mealplanner.presentation.favorites.view;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.snackbar.Snackbar;
import com.tasneem.mealplanner.R;
import com.tasneem.mealplanner.data.datasource.model.Meal;
import com.tasneem.mealplanner.databinding.FragmentFavoritesBinding;
import com.tasneem.mealplanner.presentation.favorites.presenter.FavoriteMealPresenter;
import com.tasneem.mealplanner.presentation.favorites.presenter.FavoriteMealPresenterImpl;
import com.tasneem.mealplanner.presentation.home.view.OnMealClickListener;

import java.util.List;

public class FavoritesFragment extends Fragment implements FavoriteMealView, OnMealClickListener {
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
        presenter.checkUserLoggedIn();

        adapter = new FavoriteMealAdapter(this);
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
    public void showLoginLayout() {
        binding.layoutNotLoggedIn.getRoot().setVisibility(VISIBLE);
        binding.layoutNotLoggedIn.btnSignIn.setOnClickListener(v -> NavHostFragment.findNavController(this)
                .navigate(R.id.action_favoritesFragment_to_loginFragment));
        binding.rvFavoriteMeals.setVisibility(GONE);
    }

    @Override
    public void onRemoveFromFavClicked(String mealId) {
        Snackbar.make(binding.getRoot(), R.string.meal_removed_from_favorites_successfully, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onMealClicked(String mealId) {
        FavoritesFragmentDirections.ActionFavoritesFragmentToMealDetailsFragment action =
                FavoritesFragmentDirections.actionFavoritesFragmentToMealDetailsFragment(mealId);

        NavHostFragment.findNavController(this).navigate(action);

    }
}