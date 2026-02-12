package com.tasneem.mealplanner.presentation.planned.presenter;

import android.app.Application;
import android.util.Log;

import com.tasneem.mealplanner.data.datasource.meals.model.Meal;
import com.tasneem.mealplanner.data.datasource.meals.repository.MealsRepository;
import com.tasneem.mealplanner.data.datasource.meals.repository.MealsRepositoryImpl;
import com.tasneem.mealplanner.presentation.planned.view.PlannedMealView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PlannedMealPresenterImpl implements PlannedMealPresenter {

    private PlannedMealView view;
    private final MealsRepository repository;
    private final CompositeDisposable disposables;
    private String selectedDate;
    private final List<CalendarDay> calendarDays;
    private List<Meal> allPlannedMeals;

    public PlannedMealPresenterImpl(Application application) {
        this.repository = new MealsRepositoryImpl(application);
        this.disposables = new CompositeDisposable();
        this.calendarDays = new ArrayList<>();
        this.allPlannedMeals = new ArrayList<>();
        initializeCalendar();
    }

    @Override
    public void attachView(PlannedMealView view) {
        this.view = view;
        updateSelectedDateDisplay();
    }

    private void initializeCalendar() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        int currentDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        int daysFromMonday = (currentDayOfWeek == Calendar.SUNDAY) ? 6 : currentDayOfWeek - Calendar.MONDAY;
        calendar.add(Calendar.DAY_OF_MONTH, -daysFromMonday);

        for (int i = 0; i < 7; i++) {
            CalendarDay day = new CalendarDay();
            day.date = dateFormat.format(calendar.getTime());
            day.dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            day.dayName = new SimpleDateFormat("EEE", Locale.getDefault()).format(calendar.getTime()).toUpperCase();

            calendarDays.add(day);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        String today =
                new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                        .format(new Date());

        for (CalendarDay day : calendarDays) {
            if (day.date.equals(today)) {
                day.isSelected = true;
                selectedDate = day.date;
                break;
            }
        }
        updateSelectedDateDisplay();
    }

    @Override
    public List<CalendarDay> getCalendarDays() {
        return calendarDays;
    }

    @Override
    public void onDaySelected(int position) {
        for (CalendarDay day : calendarDays) {
            day.isSelected = false;
        }

        calendarDays.get(position).isSelected = true;
        selectedDate = calendarDays.get(position).date;

        view.updateCalendarSelection(position);
        updateSelectedDateDisplay();
        loadPlannedMealsForSelectedDate();
    }

    @Override
    public void loadPlannedMeals() {
        view.showLoading();

        disposables.add(
                repository.getAllPlannedMeals()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                meals -> {
                                    allPlannedMeals = meals;
                                    loadPlannedMealsForSelectedDate();
                                    view.hideLoading();
                                },
                                error -> {
                                    view.hideLoading();
                                    view.showError("Failed to load planned meals: " + error.getMessage());
                                }
                        )
        );
    }

    private void loadPlannedMealsForSelectedDate() {
        Log.d("PlannedMealPresenter", "Loading meals for date: " + selectedDate);
        Log.d("PlannedMealPresenter", "Total meals: " + allPlannedMeals.size());

        List<Meal> mealsForDate = allPlannedMeals.stream()
                .filter(meal -> {
                    String mealDate = meal.getDate();
                    Log.d("PlannedMealPresenter", "Meal: " + meal.getName() + ", Date: " + mealDate);
                    return mealDate != null && mealDate.equals(selectedDate);
                })
                .collect(Collectors.toList());

        Log.d("PlannedMealPresenter", "Meals for selected date: " + mealsForDate.size());

        if (mealsForDate.isEmpty()) {
            view.showEmptyState();
            view.updateTotalCalories(0);
        } else {
            view.hideEmptyState();
            view.showPlannedMeals(mealsForDate);
        }
    }

    private void updateSelectedDateDisplay() {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            SimpleDateFormat outputFormat = new SimpleDateFormat("EEEE, MMMM d", Locale.getDefault());
            Date date = inputFormat.parse(selectedDate);

            String todayDate = inputFormat.format(new Date());
            String displayText;

            if (selectedDate.equals(todayDate)) {
                displayText = "Today's Meals";
            } else {
                displayText = outputFormat.format(Objects.requireNonNull(date)) + " Meals";
            }

            view.updateSelectedDate(displayText);
        } catch (Exception e) {
            view.updateSelectedDate("Planned Meals");
        }
    }

    @Override
    public void deleteMeal(String mealId) {
        view.showLoading();

        disposables.add(
                repository.deletePlannedById(mealId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> {
                                    view.hideLoading();
                                    view.showMealDeleted();
                                    // Reload meals after deletion
                                    loadPlannedMeals();
                                },
                                error -> {
                                    view.hideLoading();
                                    view.showError("Failed to delete meal: " + error.getMessage());
                                }
                        )
        );
    }

    @Override
    public void onAddMealClicked() {
        view.navigateToAddMeal(selectedDate);
    }

    public static class CalendarDay {
        public String date;
        public int dayOfMonth;
        public String dayName;
        public boolean isSelected;
    }

    @Override
    public void detachView() {
        disposables.clear();
        view = null;
    }
}