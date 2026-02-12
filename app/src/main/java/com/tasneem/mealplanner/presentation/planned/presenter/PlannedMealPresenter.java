package com.tasneem.mealplanner.presentation.planned.presenter;

import com.tasneem.mealplanner.presentation.planned.presenter.PlannedMealPresenterImpl.CalendarDay;
import com.tasneem.mealplanner.presentation.planned.view.PlannedMealView;

import java.util.List;

public interface PlannedMealPresenter {

    void attachView(PlannedMealView view);

    void detachView();

    void loadPlannedMeals();

    void onDaySelected(int position);

    List<CalendarDay> getCalendarDays();

    void deleteMeal(String mealId);

    void onAddMealClicked();
}