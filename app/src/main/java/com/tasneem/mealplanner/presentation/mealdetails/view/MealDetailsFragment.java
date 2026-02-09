package com.tasneem.mealplanner.presentation.mealdetails.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.tasneem.mealplanner.R;
import com.tasneem.mealplanner.data.datasource.meals.model.Meal;
import com.tasneem.mealplanner.databinding.FragmentMealDetailsBinding;
import com.tasneem.mealplanner.presentation.mealdetails.presenter.MealDetailsPresenter;
import com.tasneem.mealplanner.presentation.mealdetails.presenter.MealDetailsPresenterImpl;
import com.tasneem.mealplanner.presentation.utils.GlideUtil;

public class MealDetailsFragment extends Fragment implements MealDetailsView {
    private MealDetailsPresenter presenter;
    private FragmentMealDetailsBinding binding;
    private IngredientAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meal_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentMealDetailsBinding.bind(view);

        presenter = new MealDetailsPresenterImpl(requireActivity().getApplication());
        presenter.attachView(this);
        presenter.onViewStarted();

        adapter = new IngredientAdapter();
        binding.rvIngredients.setAdapter(adapter);
        binding.rvIngredients.setLayoutManager(
                new GridLayoutManager(requireContext(), 2, LinearLayoutManager.HORIZONTAL, false)
        );
    }

    @Override
    public void showLoading() {
        binding.lottieView.setVisibility(View.VISIBLE);
        binding.contentContainer.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        binding.lottieView.setVisibility(View.GONE);
        binding.contentContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMealDetails(Meal meal) {
        adapter.setIngredients(meal.getIngredients());
        binding.tvMealTitle.setText(meal.getName());
        binding.tvCategory.setText(meal.getCategory());
        binding.tvIngredientsCount.setText(String.valueOf(meal.getIngredients().size()));
        GlideUtil.loadImage(
                binding.getRoot(),
                meal.getImageUrl(),
                binding.ivMealImage
        );
        binding.btnAddToPlan.setOnClickListener(v -> presenter.onAddToPlanClicked());
        binding.btnBack.setOnClickListener(v -> navigateBack());
//        binding.btnFavorite.setOnClickListener(v -> presenter.onFavoriteClicked(meal.isFavorite()));
        binding.fabPlayVideo.setOnClickListener(v -> presenter.onVideoClicked());
    }

    @Override
    public void updateFavoriteStatus(Boolean isFavorite) {
        // TODO: update favorite status
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void showNoInternetError(String message) {

    }

    @Override
    public void navigateBack() {

    }

    @Override
    public void playVideo(String videoUrl) {

    }

    @Override
    public void showAddedToPlanMessage() {

    }

    @Override
    public void showAddToPlanError(String message) {

    }
}