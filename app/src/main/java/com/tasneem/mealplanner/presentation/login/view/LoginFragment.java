package com.tasneem.mealplanner.presentation.login.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.tasneem.mealplanner.data.datasource.auth.model.User;
import com.tasneem.mealplanner.databinding.FragmentLoginBinding;
import com.tasneem.mealplanner.presentation.login.presenter.LoginPresenter;
import com.tasneem.mealplanner.presentation.login.presenter.LoginPresenterImpl;

import java.util.Objects;

public class LoginFragment extends Fragment implements LoginView {
    private FragmentLoginBinding binding;

    private LoginPresenter presenter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        if (view == null) {
            binding = FragmentLoginBinding.inflate(inflater);
            view = binding.getRoot();
        } else {
            binding = FragmentLoginBinding.bind(view);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupListeners();

        presenter = new LoginPresenterImpl(requireActivity().getApplication());
        presenter.attachView(this);
        presenter.checkUserLoggedIn();
    }

    private void setupListeners() {
        binding.btnSignIn.setOnClickListener(v -> {
            clearErrors();
            presenter.onLoginClicked();
        });

        binding.chipGoogle.setOnClickListener(v -> {
            // TODO: Handle Google sign-in
        });

        binding.btnTextSignUp.setOnClickListener(v -> navigateToSignUp());
    }

    @Override
    public void showLoading() {
        binding.lottieView.setVisibility(View.VISIBLE);
        binding.btnSignIn.setEnabled(false);
        binding.chipGoogle.setEnabled(false);
        binding.btnTextSignUp.setEnabled(false);
        binding.etEmail.clearFocus();
        binding.etPassword.clearFocus();
    }

    @Override
    public void hideLoading() {
        binding.lottieView.setVisibility(View.GONE);
        binding.btnSignIn.setEnabled(true);
        binding.chipGoogle.setEnabled(true);
        binding.btnTextSignUp.setEnabled(true);
    }

    @Override
    public void showError(String message) {
        Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showSuccess(String message) {
        Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToSignUp() {
        // TODO: Navigate to Sign Up screen
    }

    @Override
    public void navigateToHome(User user) {
        // TODO: Navigate to Home screen
    }

    @Override
    public String getEmail() {
        return Objects.requireNonNull(binding.etEmail.getText()).toString().trim();
    }

    @Override
    public String getPassword() {
        return Objects.requireNonNull(binding.etPassword.getText()).toString().trim();
    }

    @Override
    public void clearInputFields() {
        binding.etEmail.setText("");
        binding.etPassword.setText("");
    }

    @Override
    public void showEmailError(String error) {
        binding.etEmail.setError(error);
        binding.tilEmail.setErrorEnabled(true);
    }

    @Override
    public void showPasswordError(String error) {
        binding.etPassword.setError(error);
        binding.tilPassword.setErrorEnabled(true);
    }

    private void clearErrors() {
        binding.etEmail.setError(null);
        binding.etPassword.setError(null);
        binding.tilEmail.setErrorEnabled(false);
        binding.tilPassword.setErrorEnabled(false);
        binding.etEmail.clearFocus();
        binding.etPassword.clearFocus();
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