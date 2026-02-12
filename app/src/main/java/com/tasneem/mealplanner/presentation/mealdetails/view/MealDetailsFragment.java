package com.tasneem.mealplanner.presentation.mealdetails.view;

import static android.view.View.GONE;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.tasneem.mealplanner.R;
import com.tasneem.mealplanner.data.datasource.model.Meal;
import com.tasneem.mealplanner.databinding.FragmentMealDetailsBinding;
import com.tasneem.mealplanner.presentation.mealdetails.presenter.MealDetailsPresenter;
import com.tasneem.mealplanner.presentation.mealdetails.presenter.MealDetailsPresenterImpl;
import com.tasneem.mealplanner.presentation.utils.GetFlagsUtil;
import com.tasneem.mealplanner.presentation.utils.GlideUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MealDetailsFragment extends Fragment implements MealDetailsView {
    private MealDetailsPresenter presenter;
    private FragmentMealDetailsBinding binding;
    private IngredientAdapter ingredientAdapter;
    private YouTubePlayer youTubePlayer;
    private String currentVideoUrl;
    private Context context;
    private Meal currentMeal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meal_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentMealDetailsBinding.bind(view);
        context = requireContext();
        presenter = new MealDetailsPresenterImpl(requireActivity().getApplication());
        presenter.attachView(this);

        handleArgsAndStart();
        setupRecyclerView();
        initVideoPlayer();
    }

    private void handleArgsAndStart() {
        Bundle args = getArguments();
        String mealId = null;

        if (args != null) {
            mealId = MealDetailsFragmentArgs.fromBundle(args).getMealId();
        }

        if (mealId != null) {
            presenter.onViewStarted(mealId);
        } else {
            Snackbar.make(binding.getRoot(), R.string.no_meal_selected, Snackbar.LENGTH_SHORT).show();
            NavHostFragment.findNavController(this).navigateUp();
        }
    }

    private void setupRecyclerView() {
        ingredientAdapter = new IngredientAdapter();
        binding.rvIngredients.setAdapter(ingredientAdapter);
        binding.rvIngredients.setLayoutManager(
                new GridLayoutManager(context, 2, LinearLayoutManager.HORIZONTAL, false)
        );
    }

    private void initVideoPlayer() {
        getLifecycle().addObserver(binding.youtubePlayerView);

        binding.youtubePlayerView.addYouTubePlayerListener(
                new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady(@NonNull YouTubePlayer player) {
                        youTubePlayer = player;
                    }
                }
        );

        binding.fabPlayVideo.setOnClickListener(v -> {
            if (currentVideoUrl != null && !currentVideoUrl.isEmpty()) {
                playVideo(currentVideoUrl);
            }
        });
    }

    @Override
    public void showLoading() {
        binding.lottieView.setVisibility(View.VISIBLE);
        binding.contentContainer.setVisibility(GONE);
    }

    @Override
    public void hideLoading() {
        binding.lottieView.setVisibility(GONE);
        binding.contentContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMealDetails(Meal meal) {
        currentMeal = meal;
        currentVideoUrl = meal.getVideoUrl();
        ingredientAdapter.setIngredients(meal.getIngredients());

        binding.tvMealTitle.setText(meal.getName());
        binding.tvCategory.setText(meal.getCategory());
        binding.tvCountry.setText(meal.getOriginCountry());
        GlideUtil.loadImage(
                binding.getRoot(),
                GetFlagsUtil.getFlagUrl(meal.getOriginCountry()),
                binding.ivCountryIcon
        );
        String ingredientLiteral = context.getString(R.string.ingredients);
        binding.tvIngredientsCount.setText(String.valueOf(meal.getIngredients().size()));
        binding.tvIngredientsCount.append(" " + ingredientLiteral);
        binding.tvSteps.setText(meal.getSteps());

        GlideUtil.loadImage(binding.getRoot(), meal.getImageUrl(), binding.ivMealImage);
        GlideUtil.loadImage(binding.getRoot(), meal.getImageUrl(), binding.ivVideoThumbnail);

        binding.btnAddToPlan.setOnClickListener(v -> showDatePickerDialog(currentMeal));
        binding.btnBack.setOnClickListener(v -> navigateBack());
        binding.btnFavorite.setOnClickListener(v -> {
            if (currentMeal != null) {
                presenter.onFavoriteClicked(currentMeal);
            }
        });
    }

    @Override
    public void updateFavoriteIcon(boolean isFavorite) {
        if (isFavorite) {
            binding.btnFavorite.setIconResource(R.drawable.ic_heart_filled);
        } else {
            binding.btnFavorite.setIconResource(R.drawable.ic_heart_outlined);
        }
    }

    @Override
    public void showAddedToFavoritesMessage() {
        Snackbar.make(binding.getRoot(), R.string.meal_added_to_favorites_successfully, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showRemovedFromFavoritesMessage() {
        Snackbar.make(binding.getRoot(), R.string.meal_removed_from_favorites_successfully, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showFavoriteError(String message) {
        Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String message) {
        binding.lottieView.setVisibility(GONE);
        binding.contentContainer.setVisibility(GONE);
        binding.layoutSomethingWrong.getRoot().setVisibility(View.VISIBLE);
    }

    @Override
    public void showNoInternetError(String message) {
        binding.lottieView.setVisibility(GONE);
        binding.contentContainer.setVisibility(GONE);
        binding.layoutNoInternet.getRoot().setVisibility(View.VISIBLE);
    }

    @Override
    public void navigateBack() {
        NavHostFragment.findNavController(this).navigateUp();
    }

    @Override
    public void playVideo(String videoUrl) {
        if (youTubePlayer != null && videoUrl != null && !videoUrl.isEmpty()) {
            String videoId = extractYoutubeId(videoUrl);
            if (!videoId.isEmpty()) {
                binding.videoThumbnailOverlay.setVisibility(GONE);
                binding.youtubePlayerView.setVisibility(View.VISIBLE);

                youTubePlayer.loadVideo(videoId, 0);
            }
        }
    }

    private String extractYoutubeId(String youtubeUrl) {
        String pattern = "^(?:https?://)?(?:www\\.)?(?:youtube\\.com/watch\\?v=|youtu\\.be/)([\\w-]{11}).*$";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(youtubeUrl);

        if (matcher.matches()) {
            return matcher.group(1);
        } else {
            return "";
        }
    }

    @Override
    public void showAddedToPlanMessage() {
        Snackbar.make(binding.getRoot(), R.string.meal_added_to_planner, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showAddToPlanError(String message) {
        Snackbar.make(binding.getRoot(), R.string.add_to_planner_failed, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showDatePickerDialog(Meal meal) {
        Calendar calendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                (view, year, month, dayOfMonth) -> {
                    Calendar selectedDate = Calendar.getInstance();
                    selectedDate.set(year, month, dayOfMonth);

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    String formattedDate = dateFormat.format(selectedDate.getTime());

                    Meal plannedMeal = new Meal(
                            meal.getId(),
                            meal.getName(),
                            meal.getCategory(),
                            meal.getOriginCountry(),
                            meal.getSteps(),
                            meal.getImageUrl()
                    );

                    presenter.onAddToPlanClicked(plannedMeal, formattedDate);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());

        datePickerDialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (youTubePlayer != null) {
            youTubePlayer.pause();
        }
        presenter.detachView();
    }
}