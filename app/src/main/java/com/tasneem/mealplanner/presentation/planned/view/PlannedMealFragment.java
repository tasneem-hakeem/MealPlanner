package com.tasneem.mealplanner.presentation.planned.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.tasneem.mealplanner.R;
import com.tasneem.mealplanner.data.datasource.meals.model.Meal;
import com.tasneem.mealplanner.databinding.FragmentPlannerBinding;
import com.tasneem.mealplanner.databinding.ItemCalendarBinding;
import com.tasneem.mealplanner.presentation.planned.presenter.PlannedMealPresenter;
import com.tasneem.mealplanner.presentation.planned.presenter.PlannedMealPresenterImpl;

import java.util.List;

public class PlannedMealFragment extends Fragment implements PlannedMealView {

    private static final String TAG = "PlannedMealFragment";

    private FragmentPlannerBinding binding;
    private PlannedMealPresenter presenter;
    private PlannedMealsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPlannerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializePresenter();
        presenter.attachView(this);
        setupRecyclerView();
        setupClickListeners();
        setupCalendar();
        presenter.loadPlannedMeals();
    }

    private void initializePresenter() {
        presenter = new PlannedMealPresenterImpl(requireActivity().getApplication());
    }

    private void setupRecyclerView() {
        adapter = new PlannedMealsAdapter(meal -> presenter.deleteMeal(meal.getId()));
        binding.rvPlannedMeals.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvPlannedMeals.setAdapter(adapter);
    }

    private void setupCalendar() {
        List<PlannedMealPresenterImpl.CalendarDay> days = presenter.getCalendarDays();

        Log.d(TAG, "Setting up calendar with " + days.size() + " days");

        binding.calendarContainer.removeAllViews();

        for (int i = 0; i < days.size(); i++) {
            final int position = i;
            PlannedMealPresenterImpl.CalendarDay day = days.get(i);

            Log.d(TAG, "Adding day: " + day.dayName + " " + day.dayOfMonth + " selected: " + day.isSelected);

            ItemCalendarBinding dayBinding = ItemCalendarBinding.inflate(
                    LayoutInflater.from(requireContext()),
                    binding.calendarContainer,
                    false
            );

            dayBinding.tvDayName.setText(day.dayName);
            dayBinding.tvDayNumber.setText(String.valueOf(day.dayOfMonth));

            updateDayView(dayBinding, day.isSelected);

            dayBinding.getRoot().setOnClickListener(v -> {
                Log.d(TAG, "Day clicked: position " + position);
                presenter.onDaySelected(position);
            });

            binding.calendarContainer.addView(dayBinding.getRoot());
        }

        Log.d(TAG, "Calendar setup complete. Container has " + binding.calendarContainer.getChildCount() + " children");
    }

    private void updateDayView(ItemCalendarBinding dayBinding, boolean isSelected) {
        dayBinding.tvDayNumber.setSelected(isSelected);

        if (isSelected) {
            dayBinding.tvDayName.setTextColor(getResources().getColor(R.color.primary, null));
            dayBinding.tvDayNumber.setTextColor(getResources().getColor(R.color.white, null));
        } else {
            dayBinding.tvDayName.setTextColor(getResources().getColor(R.color.text_tertiary, null));
            dayBinding.tvDayNumber.setTextColor(getResources().getColor(R.color.text_primary, null));
        }
    }

    private void setupClickListeners() {
        binding.btnAddMeal.setOnClickListener(v -> presenter.onAddMealClicked());
        binding.btnAddMealEmpty.setOnClickListener(v -> presenter.onAddMealClicked());
    }

    @Override
    public void showPlannedMeals(List<Meal> meals) {
        Log.d(TAG, "Showing " + meals.size() + " planned meals");
        if (meals.isEmpty()) {
            Log.d(TAG, "Meals list is empty!");
        } else {
            for (Meal meal : meals) {
                Log.d(TAG, "Meal: " + meal.getName() + ", Date: " + meal.getDate());
            }
        }
        adapter.updateMeals(meals);
    }

    @Override
    public void showLoading() {
        binding.loadingLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        binding.loadingLayout.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {
        Log.e(TAG, "Error: " + message);
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showEmptyState() {
        Log.d(TAG, "Showing empty state");
        binding.emptyStateLayout.setVisibility(View.VISIBLE);
        binding.rvPlannedMeals.setVisibility(View.GONE);
    }

    @Override
    public void hideEmptyState() {
        Log.d(TAG, "Hiding empty state");
        binding.emptyStateLayout.setVisibility(View.GONE);
        binding.rvPlannedMeals.setVisibility(View.VISIBLE);
    }

    @Override
    public void updateSelectedDate(String dateText) {
        Log.d(TAG, "Update selected date: " + dateText);
        binding.tvSelectedDate.setText(dateText);
    }

    @Override
    public void updateTotalCalories(int calories) {
        binding.tvTotalCalories.setText(calories + " kcal");
    }

    @Override
    public void showMealDeleted() {
        Toast.makeText(requireContext(), "Meal deleted successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToAddMeal(String selectedDate) {
        Toast.makeText(requireContext(), "Navigate to add meal for " + selectedDate, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateCalendarSelection(int position) {
        Log.d(TAG, "Updating calendar selection to position: " + position);

        for (int i = 0; i < binding.calendarContainer.getChildCount(); i++) {
            View dayView = binding.calendarContainer.getChildAt(i);

            ItemCalendarBinding dayBinding = ItemCalendarBinding.bind(dayView);

            boolean isSelected = i == position;
            updateDayView(dayBinding, isSelected);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (presenter != null) {
            presenter.detachView();
        }
        binding = null;
    }
}