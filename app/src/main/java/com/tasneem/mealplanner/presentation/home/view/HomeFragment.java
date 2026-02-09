package com.tasneem.mealplanner.presentation.home.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.tasneem.mealplanner.R;
import com.tasneem.mealplanner.data.datasource.meals.model.Meal;
import com.tasneem.mealplanner.databinding.FragmentHomeBinding;
import com.tasneem.mealplanner.presentation.home.presenter.HomePresenter;
import com.tasneem.mealplanner.presentation.home.presenter.HomePresenterImpl;
import com.tasneem.mealplanner.presentation.utils.GlideUtil;

import java.util.List;

public class HomeFragment extends Fragment implements HomeView, OnMealClickListener {
    private HomePresenter presenter;
    private FragmentHomeBinding binding;
    private MealsAdapter healthyMealsAdapter;
    private MealsAdapter breakfastSuggestionsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentHomeBinding.bind(view);

        presenter = new HomePresenterImpl(requireActivity().getApplication());
        presenter.attachView(this);
        presenter.onViewStarted();

        setUpRecyclers();
    }

    private void setUpRecyclers() {
        healthyMealsAdapter = new MealsAdapter(this);
        binding.rvHealthyMeals.setAdapter(healthyMealsAdapter);
        binding.rvHealthyMeals.setLayoutManager(
                new GridLayoutManager(requireContext(), 2, LinearLayoutManager.HORIZONTAL, false)
        );

        breakfastSuggestionsAdapter = new MealsAdapter(this);
        binding.rvBreakfastSuggestions.setAdapter(breakfastSuggestionsAdapter);
        binding.rvBreakfastSuggestions.setLayoutManager(
                new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        );
    }

    @Override
    public void showMealOfTheDayLoading() {
        binding.mealOfTheDayLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideMealOfTheDayLoading() {
        binding.mealOfTheDayLoading.setVisibility(View.GONE);
    }

    @Override
    public void showHealthyMealsLoading() {
        binding.healthyMealsLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideHealthyMealsLoading() {
        binding.healthyMealsLoading.setVisibility(View.GONE);
    }

    @Override
    public void showBreakfastSuggestionsLoading() {
        binding.breakfastSuggestionsLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideBreakfastSuggestionsLoading() {
        binding.breakfastSuggestionsLoading.setVisibility(View.GONE);
    }

    @Override
    public void showUserName(String name) {
        binding.tvHi.setText(getString(R.string.hi_user, name));
    }

    @Override
    public void showMealOfTheDay(Meal meal) {
        if (meal != null) {
            binding.tvMealName.setText(meal.getName());
            binding.tvBadge.setText(getString(R.string.meal_of_the_day));
            GlideUtil.loadImage(
                    binding.getRoot(),
                    meal.getImageUrl(),
                    binding.imgMeal
            );

            binding.cardMealOfTheDay.setOnClickListener(v -> onMealClicked(meal.getId()));
        }
    }

    @Override
    public void showHealthyMeals(List<Meal> meals) {
        healthyMealsAdapter.setMeals(meals);
    }

    @Override
    public void showBreakfastSuggestions(List<Meal> meals) {
        breakfastSuggestionsAdapter.setMeals(meals);
    }

    @Override
    public void onAddToPlannerClicked() {
        // TODO: handle this
    }

    @Override
    public void showError(String message) {
        //TODO: show error layout
    }

    @Override
    public void onMealClicked(String mealId) {
        HomeFragmentDirections.ActionHomeFragmentToMealDetailsFragment action =
                HomeFragmentDirections.actionHomeFragmentToMealDetailsFragment(mealId);

        NavHostFragment.findNavController(this).navigate(action);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        if (presenter != null) {
            presenter.detachView();
        }
    }
}