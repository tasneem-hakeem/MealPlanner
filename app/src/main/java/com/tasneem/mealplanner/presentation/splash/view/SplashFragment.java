package com.tasneem.mealplanner.presentation.splash.view;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.model.KeyPath;
import com.tasneem.mealplanner.R;
import com.tasneem.mealplanner.databinding.FragmentSplashBinding;
import com.tasneem.mealplanner.presentation.splash.presenter.SplashPresenter;
import com.tasneem.mealplanner.presentation.splash.presenter.SplashPresenterImpl;

public class SplashFragment extends Fragment implements SplashView {
    private FragmentSplashBinding binding;
    private SplashPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new SplashPresenterImpl();
        presenter.attachView(this);
        presenter.checkIfLoggedInAndNavigate();
    }

    @Override
    public void navigateToLogin() {
        NavHostFragment.findNavController(this)
                .navigate(R.id.action_splashFragment2_to_loginFragment);
    }

    @Override
    public void navigateToHome() {
        NavHostFragment.findNavController(this)
                .navigate(R.id.action_splashFragment2_to_homeFragment);
    }

    @Override
    public void showError(String message) {
        //
    }
}