package com.tasneem.mealplanner.presentation.search.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

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
    private boolean isBrowsingMode = false; // جديد: للتفريق بين البحث بالنص والتصفح

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
        // Trending Ingredients RecyclerView
        trendingAdapter = new TrendingIngredientsAdapter(ingredient -> {
            // عند الضغط على ingredient
            clearSearchInput();
            switchToBrowsingMode(); // إخفاء حقل البحث
            presenter.onIngredientClicked(ingredient.getName());
        });
        binding.trendingIngredientsRecyclerView.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false)
        );
        binding.trendingIngredientsRecyclerView.setAdapter(trendingAdapter);

        // Categories RecyclerView
        categoriesAdapter = new CategoriesAdapter(category -> {
            // عند الضغط على category
            clearSearchInput();
            switchToBrowsingMode(); // إخفاء حقل البحث
            presenter.onCategoryClicked(category.getName());
        });
        binding.categoriesRecyclerView.setLayoutManager(
                new GridLayoutManager(requireContext(), 2, LinearLayoutManager.HORIZONTAL, false)
        );
        binding.categoriesRecyclerView.setAdapter(categoriesAdapter);

        // Areas RecyclerView
        areasAdapter = new AreasAdapter(area -> {
            // عند الضغط على area/country
            clearSearchInput();
            switchToBrowsingMode(); // إخفاء حقل البحث
            presenter.onAreaClicked(area.getName());
        });
        binding.areasRecyclerView.setLayoutManager(
                new GridLayoutManager(requireContext(), 2, LinearLayoutManager.HORIZONTAL, false)
        );
        binding.areasRecyclerView.setAdapter(areasAdapter);

        // Search Results RecyclerView
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
                                error -> showError("Search error: " + error.getMessage())
                        )
        );

        binding.searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchSubject.onNext(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    /**
     * إعداد زر الرجوع
     */
    private void setupBackButton() {
        binding.btnBack.setOnClickListener(v -> {
            // الرجوع للوضع الافتراضي
            switchToDefaultMode();
            presenter.clearSearch();
        });
    }

    private void handleSearchQuery(String query) {
        if (query == null || query.trim().isEmpty()) {
            switchToDefaultMode();
            presenter.clearSearch();
        } else {
            // عند البحث بالنص، نعرض حقل البحث
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

    /**
     * وضع البحث: عند الكتابة في حقل البحث
     * - يخفي الـ categories والـ ingredients والـ areas
     * - يعرض النتائج
     * - يبقي حقل البحث ظاهر
     */
    private void switchToSearchMode() {
        if (!isSearchMode) {
            isSearchMode = true;
            isBrowsingMode = false;

            // إخفاء القوائم
            binding.trendingIngredientsTitle.setVisibility(View.GONE);
            binding.trendingIngredientsRecyclerView.setVisibility(View.GONE);
            binding.browseCategoriesTitle.setVisibility(View.GONE);
            binding.categoriesRecyclerView.setVisibility(View.GONE);
            binding.browseAreasTitle.setVisibility(View.GONE);
            binding.areasRecyclerView.setVisibility(View.GONE);

            binding.contentScrollView.setVisibility(View.GONE);
            binding.searchResultsRecyclerView.setVisibility(View.VISIBLE);

            // إبقاء حقل البحث ظاهر
            binding.searchBarContainer.setVisibility(View.VISIBLE);

            // إخفاء زر الرجوع
            binding.btnBack.setVisibility(View.GONE);
        }
    }

    /**
     * وضع التصفح: عند الضغط على category أو ingredient أو area
     * - يخفي الـ categories والـ ingredients والـ areas
     * - يعرض النتائج
     * - يخفي حقل البحث
     * - يعرض زر الرجوع
     */
    private void switchToBrowsingMode() {
        isSearchMode = true;
        isBrowsingMode = true;

        // إخفاء القوائم
        binding.trendingIngredientsTitle.setVisibility(View.GONE);
        binding.trendingIngredientsRecyclerView.setVisibility(View.GONE);
        binding.browseCategoriesTitle.setVisibility(View.GONE);
        binding.categoriesRecyclerView.setVisibility(View.GONE);
        binding.browseAreasTitle.setVisibility(View.GONE);
        binding.areasRecyclerView.setVisibility(View.GONE);

        binding.contentScrollView.setVisibility(View.GONE);
        binding.searchResultsRecyclerView.setVisibility(View.VISIBLE);

        // إخفاء حقل البحث
        binding.searchBarContainer.setVisibility(View.GONE);

        // إظهار زر الرجوع
        binding.btnBack.setVisibility(View.VISIBLE);
    }

    /**
     * الوضع الافتراضي: الصفحة الرئيسية
     */
    private void switchToDefaultMode() {
        if (isSearchMode || isBrowsingMode) {
            isSearchMode = false;
            isBrowsingMode = false;

            // إظهار القوائم
            binding.trendingIngredientsTitle.setVisibility(View.VISIBLE);
            binding.trendingIngredientsRecyclerView.setVisibility(View.VISIBLE);
            binding.browseCategoriesTitle.setVisibility(View.VISIBLE);
            binding.categoriesRecyclerView.setVisibility(View.VISIBLE);
            binding.browseAreasTitle.setVisibility(View.VISIBLE);
            binding.areasRecyclerView.setVisibility(View.VISIBLE);

            binding.contentScrollView.setVisibility(View.VISIBLE);
            binding.searchResultsRecyclerView.setVisibility(View.GONE);

            // إظهار حقل البحث
            binding.searchBarContainer.setVisibility(View.VISIBLE);

            // إخفاء زر الرجوع
            binding.btnBack.setVisibility(View.GONE);
        }
    }

    // ==================== SearchView Interface Implementation ====================

    @Override
    public void showSearchResults(List<Meal> meals) {
        searchResultsAdapter.setMeals(meals);

        // إذا كنا في وضع التصفح، نتأكد من إخفاء حقل البحث
        if (isBrowsingMode) {
            binding.searchBarContainer.setVisibility(View.GONE);
            binding.btnBack.setVisibility(View.VISIBLE);
        }

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
        showError("No meals found");
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
    public void showError(String message) {
        if (getContext() != null) {
            android.widget.Toast.makeText(getContext(), message, android.widget.Toast.LENGTH_LONG).show();
        }
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