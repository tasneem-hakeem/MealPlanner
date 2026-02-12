package com.tasneem.mealplanner.presentation.planned.view;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.google.android.material.card.MaterialCardView;
import com.tasneem.mealplanner.R;
import com.tasneem.mealplanner.data.datasource.meals.model.Meal;
import com.tasneem.mealplanner.data.datasource.meals.repository.MealsRepositoryImpl;
import com.tasneem.mealplanner.presentation.planned.presenter.PlannedMealPresenter;
import com.tasneem.mealplanner.presentation.planned.presenter.PlannedMealPresenterImpl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class PlannedMealFragment extends Fragment implements PlannedMealView, PlannedMealsAdapter.OnMealClickListener {

    private PlannedMealPresenter presenter;
    private PlannedMealsAdapter adapter;

    private com.applandeo.materialcalendarview.CalendarView calendarView;
    private RecyclerView mealsRecyclerView;
    private ProgressBar progressBar;
    private MaterialCardView emptyStateCard;
    private TextView emptyStateText;

    private String selectedDate;
    private SimpleDateFormat dateFormat;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_planner, container, false);

        initViews(view);
        initPresenter();
        initRecyclerView();
        initCalendar();

        return view;
    }

    private void initViews(View view) {
        calendarView = view.findViewById(R.id.calendarView);
        mealsRecyclerView = view.findViewById(R.id.mealsRecyclerView);
        progressBar = view.findViewById(R.id.progressBar);
        emptyStateCard = view.findViewById(R.id.emptyStateCard);
        emptyStateText = view.findViewById(R.id.emptyStateText);

        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    }

    private void initPresenter() {
        presenter = new PlannedMealPresenterImpl(this, new MealsRepositoryImpl(requireActivity().getApplication()));
    }

    private void initRecyclerView() {
        adapter = new PlannedMealsAdapter(this);
        mealsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mealsRecyclerView.setAdapter(adapter);
    }

    private void initCalendar() {
        // Set today's date as selected
        Calendar today = Calendar.getInstance();
        try {
            calendarView.setDate(today);
        } catch (OutOfDateRangeException e) {
            Log.d("Calendar", "Invalid date selected");
        }
        selectedDate = dateFormat.format(today.getTime());

        // Load meals for today
        presenter.loadMealsForDate(selectedDate);

        // Set calendar click listener
        calendarView.setOnDayClickListener(eventDay -> {
            Calendar clickedDay = eventDay.getCalendar();
            selectedDate = dateFormat.format(clickedDay.getTime());
            presenter.loadMealsForDate(selectedDate);
        });
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        mealsRecyclerView.setVisibility(View.GONE);
        emptyStateCard.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showMeals(List<Meal> meals) {
        mealsRecyclerView.setVisibility(View.VISIBLE);
        emptyStateCard.setVisibility(View.GONE);
        adapter.setMeals(meals);
    }

    @Override
    public void showEmptyState() {
        mealsRecyclerView.setVisibility(View.GONE);
        emptyStateCard.setVisibility(View.VISIBLE);
        emptyStateText.setText("No meals planned for this day");
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
        // You can implement navigation here
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
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}