package com.tasneem.mealplanner.presentation.search.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;
import com.tasneem.mealplanner.R;
import com.tasneem.mealplanner.data.datasource.meals.model.Area;
import com.tasneem.mealplanner.data.datasource.meals.model.Category;
import com.tasneem.mealplanner.data.datasource.meals.model.Ingredient;
import com.tasneem.mealplanner.data.datasource.meals.model.Meal;
import com.tasneem.mealplanner.data.datasource.meals.repository.MealsRepositoryImpl;
import com.tasneem.mealplanner.databinding.FragmentSearchBinding;
import com.tasneem.mealplanner.presentation.search.presenter.SearchPresenter;
import com.tasneem.mealplanner.presentation.search.presenter.SearchPresenterImpl;
import com.tasneem.mealplanner.presentation.search.view.adapter.AreasAdapter;
import com.tasneem.mealplanner.presentation.search.view.adapter.CategoriesAdapter;
import com.tasneem.mealplanner.presentation.search.view.adapter.SearchResultsAdapter;
import com.tasneem.mealplanner.presentation.search.view.adapter.TrendingIngredientsAdapter;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class SearchFragment extends Fragment implements SearchView {

    private FragmentSearchBinding binding;
    private SearchPresenter presenter;

    private TrendingIngredientsAdapter trendingAdapter;
    private CategoriesAdapter categoriesAdapter;
    private AreasAdapter areasAdapter;
    private SearchResultsAdapter searchResultsAdapter;

    private boolean isSearchMode = false;
    private boolean isBrowsingMode = false;

    private final PublishSubject<String> searchSubject = PublishSubject.create();
    private final CompositeDisposable disposables = new CompositeDisposable();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MealsRepositoryImpl repository = new MealsRepositoryImpl(requireActivity().getApplication());
        presenter = new SearchPresenterImpl(repository);
        presenter.attachView(this);

        setupRecyclerViews();
        setupSearch();
        setupBackButton();
        loadInitialData();
    }

    private void setupRecyclerViews() {
        trendingAdapter = new TrendingIngredientsAdapter(ingredient -> {
            clearSearchInput();
            switchToBrowsingMode();
            presenter.onIngredientClicked(ingredient.getName());
        });
        binding.trendingIngredientsRecyclerView.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false)
        );
        binding.trendingIngredientsRecyclerView.setAdapter(trendingAdapter);

        categoriesAdapter = new CategoriesAdapter(category -> {
            clearSearchInput();
            switchToBrowsingMode();
            presenter.onCategoryClicked(category.getName());
        });
        binding.categoriesRecyclerView.setLayoutManager(
                new GridLayoutManager(requireContext(), 2, LinearLayoutManager.HORIZONTAL, false)
        );
        binding.categoriesRecyclerView.setAdapter(categoriesAdapter);

        areasAdapter = new AreasAdapter(area -> {
            clearSearchInput();
            switchToBrowsingMode();
            presenter.onAreaClicked(area.getName());
        });
        binding.areasRecyclerView.setLayoutManager(
                new GridLayoutManager(requireContext(), 2, LinearLayoutManager.HORIZONTAL, false)
        );
        binding.areasRecyclerView.setAdapter(areasAdapter);

        searchResultsAdapter = new SearchResultsAdapter(meal ->
                presenter.onMealClicked(meal.getId())
        );
        binding.searchResultsRecyclerView.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false)
        );
        binding.searchResultsRecyclerView.setAdapter(searchResultsAdapter);
    }

    private void setupSearch() {
        disposables.add(
                searchSubject
                        .debounce(300, TimeUnit.MILLISECONDS)
                        .distinctUntilChanged()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                this::handleSearchQuery,
                                error -> showError(R.string.search_error, error.getMessage())
                        )
        );

        binding.searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchSubject.onNext(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void setupBackButton() {
        binding.btnBack.setOnClickListener(v -> {
            switchToDefaultMode();
            presenter.clearSearch();
        });
    }

    private void handleSearchQuery(String query) {
        if (query == null || query.trim().isEmpty()) {
            switchToDefaultMode();
            presenter.clearSearch();
        } else {
            switchToSearchMode();
            presenter.searchMealsByName(query);
        }
    }

    private void clearSearchInput() {
        binding.searchEditText.setText("");
    }

    private void loadInitialData() {
        presenter.loadInitialData();
    }

    private void switchToSearchMode() {
        if (!isSearchMode) {
            isSearchMode = true;
            isBrowsingMode = false;

            binding.trendingIngredientsTitle.setVisibility(View.GONE);
            binding.trendingIngredientsRecyclerView.setVisibility(View.GONE);
            binding.browseCategoriesTitle.setVisibility(View.GONE);
            binding.categoriesRecyclerView.setVisibility(View.GONE);
            binding.browseAreasTitle.setVisibility(View.GONE);
            binding.areasRecyclerView.setVisibility(View.GONE);
            binding.contentScrollView.setVisibility(View.GONE);

            binding.searchResultsRecyclerView.setVisibility(View.VISIBLE);

            binding.searchInputLayout.setVisibility(View.VISIBLE);
            binding.btnBack.setVisibility(View.GONE);
        }
    }

    private void switchToBrowsingMode() {
        isSearchMode = true;
        isBrowsingMode = true;

        binding.trendingIngredientsTitle.setVisibility(View.GONE);
        binding.trendingIngredientsRecyclerView.setVisibility(View.GONE);
        binding.browseCategoriesTitle.setVisibility(View.GONE);
        binding.categoriesRecyclerView.setVisibility(View.GONE);
        binding.browseAreasTitle.setVisibility(View.GONE);
        binding.areasRecyclerView.setVisibility(View.GONE);
        binding.contentScrollView.setVisibility(View.GONE);

        binding.searchResultsRecyclerView.setVisibility(View.VISIBLE);

        binding.searchInputLayout.setVisibility(View.GONE);
        binding.btnBack.setVisibility(View.VISIBLE);

    }

    private void switchToDefaultMode() {
        if (isSearchMode || isBrowsingMode) {
            isSearchMode = false;
            isBrowsingMode = false;

            binding.trendingIngredientsTitle.setVisibility(View.VISIBLE);
            binding.trendingIngredientsRecyclerView.setVisibility(View.VISIBLE);
            binding.browseCategoriesTitle.setVisibility(View.VISIBLE);
            binding.categoriesRecyclerView.setVisibility(View.VISIBLE);
            binding.browseAreasTitle.setVisibility(View.VISIBLE);
            binding.areasRecyclerView.setVisibility(View.VISIBLE);
            binding.contentScrollView.setVisibility(View.VISIBLE);

            binding.searchResultsRecyclerView.setVisibility(View.GONE);

            binding.searchInputLayout.setVisibility(View.VISIBLE);
            binding.btnBack.setVisibility(View.GONE);
        }
    }

    @Override
    public void showSearchResults(List<Meal> meals) {
        searchResultsAdapter.setMeals(meals);
        binding.searchResultsRecyclerView.setVisibility(View.VISIBLE);
        binding.contentScrollView.setVisibility(View.GONE);
    }

    @Override
    public void showTrendingIngredients(List<Ingredient> ingredients) {
        trendingAdapter.setIngredients(ingredients);
    }

    @Override
    public void showCategories(List<Category> categories) {
        categoriesAdapter.setCategories(categories);
    }

    @Override
    public void showAreas(List<Area> areas) {
        areasAdapter.setAreas(areas);
    }

    @Override
    public void showEmptyState() {
        searchResultsAdapter.clearMeals();
        showError(R.string.no_meals_found, "");
        binding.searchResultsRecyclerView.setVisibility(View.VISIBLE);
        binding.contentScrollView.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {
        binding.contentScrollView.setVisibility(View.GONE);
        binding.searchResultsRecyclerView.setVisibility(View.GONE);
        binding.searchLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        binding.searchLoading.setVisibility(View.GONE);
    }

    @Override
    public void showError(@StringRes int messageResId, String errorDetails) {
        String message = getString(messageResId);

        if (errorDetails != null && !errorDetails.isEmpty()) {
            message += ": " + errorDetails;
        }
        Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToMealDetails(String mealId) {
        SearchFragmentDirections.ActionSearchFragmentToMealDetailsFragment action =
                SearchFragmentDirections.actionSearchFragmentToMealDetailsFragment(mealId);

        NavHostFragment.findNavController(this).navigate(action);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        disposables.clear();
        presenter.detachView();
        binding = null;
    }
}