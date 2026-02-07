package com.tasneem.mealplanner.presentation.home.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.tasneem.mealplanner.R;
import com.tasneem.mealplanner.data.datasource.meals.model.Meal;
import com.tasneem.mealplanner.databinding.FragmentHomeBinding;
import com.tasneem.mealplanner.presentation.home.presenter.HomePresenter;
import com.tasneem.mealplanner.presentation.home.presenter.HomePresenterImpl;
import com.tasneem.mealplanner.presentation.utils.GlideUtil;

import java.util.List;

public class HomeFragment extends Fragment implements HomeView {
    private HomePresenter presenter;
    private FragmentHomeBinding binding;

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

    }

    @Override
    public void hideHealthyMealsLoading() {

    }

    @Override
    public void showBreakfastSuggestionsLoading() {

    }

    @Override
    public void hideBreakfastSuggestionsLoading() {

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
        }
    }

    @Override
    public void showHealthyMeals(List<Meal> meals) {

    }

    @Override
    public void showBreakfastSuggestions(List<Meal> meals) {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void navigateToMealDetails(String mealId) {

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