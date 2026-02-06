package com.tasneem.mealplanner.presentation.register.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.tasneem.mealplanner.R;
import com.tasneem.mealplanner.databinding.FragmentRegisterBinding;
import com.tasneem.mealplanner.presentation.register.presenter.RegisterPresenter;
import com.tasneem.mealplanner.presentation.register.presenter.RegisterPresenterImpl;

import java.util.Objects;

public class RegisterFragment extends Fragment implements RegisterView {
    private FragmentRegisterBinding binding;
    private RegisterPresenter presenter;

    private boolean isPasswordVisible = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        if (view == null) {
            binding = FragmentRegisterBinding.inflate(inflater);
            view = binding.getRoot();
        } else {
            binding = FragmentRegisterBinding.bind(view);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupListeners();

        presenter = new RegisterPresenterImpl(requireActivity().getApplication());
        presenter.attachView(this);

        setupPasswordToggle();
    }

    private void setupListeners() {
        binding.btnCreateAccount.setOnClickListener(v -> {
            clearErrors();
            presenter.onSignUpClicked();
        });

        binding.chipGoogle.setOnClickListener(v -> {
            // TODO: Handle Google sign-in
        });

        binding.btnTextSignIn.setOnClickListener(v -> navigateToLogin());
    }

    private void clearErrors() {
        binding.tilEmail.setError(null);
        binding.tilPassword.setError(null);
    }

    private void setupPasswordToggle() {
        binding.tilPassword.setEndIconOnClickListener(v -> {
            isPasswordVisible = !isPasswordVisible;

            if (isPasswordVisible) {
                binding.etPassword.setTransformationMethod(
                        android.text.method.HideReturnsTransformationMethod.getInstance()
                );
                binding.tilPassword.setEndIconDrawable(R.drawable.ic_eye);
            } else {
                binding.etPassword.setTransformationMethod(
                        android.text.method.PasswordTransformationMethod.getInstance()
                );
                binding.tilPassword.setEndIconDrawable(R.drawable.ic_eye_off);
            }

            binding.etPassword.setSelection(
                    Objects.requireNonNull(binding.etPassword.getText()).length()
            );
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        if (presenter != null) {
            presenter.detachView();
        }
    }

    @Override
    public void showLoading() {
        binding.lottieView.setVisibility(View.VISIBLE);
        binding.btnCreateAccount.setEnabled(false);
        binding.chipGoogle.setEnabled(false);
        binding.btnTextSignIn.setEnabled(false);
        binding.etEmail.clearFocus();
        binding.etPassword.clearFocus();
    }

    @Override
    public void hideLoading() {
        binding.lottieView.setVisibility(View.GONE);
        binding.btnCreateAccount.setEnabled(true);
        binding.chipGoogle.setEnabled(true);
        binding.btnTextSignIn.setEnabled(true);
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
    public void navigateToLogin() {
        // TODO: Navigate to Login screen
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
    public String getFullName() {
        return Objects.requireNonNull(binding.etName.getText()).toString().trim();
    }

    @Override
    public void clearInputFields() {
        binding.etName.setText("");
        binding.etEmail.setText("");
        binding.etPassword.setText("");
    }

    @Override
    public void showEmailError(String error) {
        binding.tilEmail.setError(error);
    }

    @Override
    public void showPasswordError(String error) {
        binding.tilPassword.setError(error);
    }
}