package com.tasneem.mealplanner.presentation.mealdetails.presenter;

import android.app.Application;
import android.content.Context;

import com.tasneem.mealplanner.R;
import com.tasneem.mealplanner.data.datasource.auth.repository.AuthenticationRepository;
import com.tasneem.mealplanner.data.datasource.auth.repository.AuthenticationRepositoryImpl;
import com.tasneem.mealplanner.data.datasource.model.Meal;
import com.tasneem.mealplanner.data.datasource.repository.MealsRepository;
import com.tasneem.mealplanner.data.datasource.repository.MealsRepositoryImpl;
import com.tasneem.mealplanner.presentation.mealdetails.view.MealDetailsView;

import java.io.IOException;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealDetailsPresenterImpl implements MealDetailsPresenter {
    private MealDetailsView view;
    private final MealsRepository repository;
    private final AuthenticationRepository auth;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final Context context;
    private boolean isFavorite = false;

    public MealDetailsPresenterImpl(Application application) {
        repository = new MealsRepositoryImpl(application);
        auth = new AuthenticationRepositoryImpl();
        this.context = application.getApplicationContext();
    }


    @Override
    public void attachView(MealDetailsView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
        disposables.clear();
    }

    @Override
    public void onViewStarted(String mealId) {
        if (view == null) return;

        loadMealDetails(mealId);
        executeIfLoggedIn(() -> checkIfMealIsFavorite(mealId));
    }

    private void loadMealDetails(String mealId) {
        view.showLoading();
        Disposable disposable = repository.getMealDetailsById(mealId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        meal -> {
                            view.hideLoading();
                            view.showMealDetails(meal);
                        },
                        throwable -> {
                            view.hideLoading();
                            if (throwable instanceof IOException) {
                                view.showNoInternetError(context.getString(R.string.no_internet_connection));
                            } else {
                                view.showError(context.getString(R.string.something_went_wrong));
                            }
                        }
                );
        disposables.add(disposable);
    }

    @Override
    public void checkIfMealIsFavorite(String mealId) {
        Disposable disposable = repository.getFavoriteById(mealId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        meal -> {
                            isFavorite = true;
                            if (view != null) {
                                view.updateFavoriteIcon(true);
                            }
                        },
                        throwable -> {
                            isFavorite = false;
                            if (view != null) {
                                view.updateFavoriteIcon(false);
                            }
                        }
                );
        disposables.add(disposable);
    }

    @Override
    public void onFavoriteClicked(Meal meal) {
        executeIfLoggedIn(() -> {
            if (isFavorite) {
                removeFromFavorites(meal);
            } else {
                addToFavorites(meal);
            }
        });
    }


    private void addToFavorites(Meal meal) {
        Disposable disposable = repository.addMealToFavorite(meal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                            isFavorite = true;
                            if (view != null) {
                                view.updateFavoriteIcon(true);
                                view.showAddedToFavoritesMessage();
                            }
                        },
                        throwable -> {
                            if (view != null) {
                                view.showFavoriteError(context.getString(R.string.add_to_favorites_failed));
                            }
                        }
                );
        disposables.add(disposable);
    }

    private void removeFromFavorites(Meal meal) {
        Disposable disposable = repository.deleteFavoriteById(meal.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                            isFavorite = false;
                            if (view != null) {
                                view.updateFavoriteIcon(false);
                                view.showRemovedFromFavoritesMessage();
                            }
                        },
                        throwable -> {
                            if (view != null) {
                                view.showFavoriteError(context.getString(R.string.remove_from_favorites_failed));
                            }
                        }
                );
        disposables.add(disposable);
    }

    @Override
    public void onAddToPlanClicked(Meal meal, String selectedDate) {

        executeIfLoggedIn(() -> {

            meal.setDate(selectedDate);

            disposables.add(
                    repository.addMealToPlanned(meal)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    () -> view.showAddedToPlanMessage(),
                                    error -> view.showAddToPlanError(error.getMessage())
                            )
            );

        });
    }


    private void executeIfLoggedIn(Runnable action) {
        Disposable disposable = auth.isUserSignedIn()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        isSignedIn -> {
                            if (isSignedIn) {
                                action.run();
                            } else {
                                if (view != null) view.showLoginLayout();
                            }
                        },
                        throwable -> {
                            if (view != null) view.showError(throwable.getMessage());
                        }
                );

        disposables.add(disposable);
    }

}