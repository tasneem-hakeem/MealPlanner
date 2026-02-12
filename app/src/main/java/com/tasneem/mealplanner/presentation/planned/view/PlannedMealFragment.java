package com.tasneem.mealplanner.presentation.planned.view;

import android.app.AlertDialog;
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

import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.tasneem.mealplanner.data.datasource.model.Meal;
import com.tasneem.mealplanner.data.datasource.repository.MealsRepositoryImpl;
import com.tasneem.mealplanner.databinding.FragmentPlannerBinding;
import com.tasneem.mealplanner.presentation.planned.presenter.PlannedMealPresenter;
import com.tasneem.mealplanner.presentation.planned.presenter.PlannedMealPresenterImpl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class PlannedMealFragment extends Fragment implements PlannedMealView, PlannedMealsAdapter.OnMealClickListener {

    private PlannedMealPresenter presenter;
    private PlannedMealsAdapter adapter;
    private FragmentPlannerBinding binding;

    private String selectedDate;
    private SimpleDateFormat dateFormat;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPlannerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        initPresenter();
        presenter.attachView(this);
        initRecyclerView();
        initCalendar();
    }

    private void initPresenter() {
        presenter = new PlannedMealPresenterImpl(new MealsRepositoryImpl(requireActivity().getApplication()));
    }

    private void initRecyclerView() {
        adapter = new PlannedMealsAdapter(this);
        binding.mealsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.mealsRecyclerView.setAdapter(adapter);
    }

    private void initCalendar() {
        Calendar today = Calendar.getInstance();
        try {
            binding.calendarView.setDate(today);
        } catch (OutOfDateRangeException e) {
            Log.d("Calendar", "Invalid date selected");
        }
        selectedDate = dateFormat.format(today.getTime());

        presenter.loadMealsForDate(selectedDate);

        binding.calendarView.setOnDayClickListener(eventDay -> {
            Calendar clickedDay = eventDay.getCalendar();
            selectedDate = dateFormat.format(clickedDay.getTime());
            presenter.loadMealsForDate(selectedDate);
        });
    }

    @Override
    public void showLoading() {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.mealsRecyclerView.setVisibility(View.GONE);
        binding.emptyStateCard.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        binding.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showMeals(List<Meal> meals) {
        binding.mealsRecyclerView.setVisibility(View.VISIBLE);
        binding.emptyStateCard.setVisibility(View.GONE);
        adapter.setMeals(meals);
    }

    @Override
    public void showEmptyState() {
        binding.mealsRecyclerView.setVisibility(View.GONE);
        binding.emptyStateCard.setVisibility(View.VISIBLE);
        binding.emptyStateText.setText("No meals planned for this day");
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDeleteSuccess() {
        Toast.makeText(getContext(), "Meal removed from plan", Toast.LENGTH_SHORT).show();
        presenter.loadMealsForDate(selectedDate);
    }

    @Override
    public void onMealClick(Meal meal) {
        // Navigate to meal details
        Toast.makeText(getContext(), "Meal clicked: " + meal.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteClick(Meal meal) {
        new AlertDialog.Builder(getContext())
                .setTitle("Remove Meal")
                .setMessage("Remove " + meal.getName() + " from your plan?")
                .setPositiveButton("Remove", (dialog, which) -> presenter.deleteMeal(meal.getId()))
                .setNegativeButton("Cancel", null)
                .show();
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