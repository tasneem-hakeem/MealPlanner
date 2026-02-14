package com.tasneem.mealplanner.presentation.profile.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.tasneem.mealplanner.R;
import com.tasneem.mealplanner.data.datasource.auth.model.User;
import com.tasneem.mealplanner.databinding.FragmentProfileBinding;
import com.tasneem.mealplanner.presentation.profile.presenter.ProfilePresenter;
import com.tasneem.mealplanner.presentation.profile.presenter.ProfilePresenterImpl;

public class ProfileFragment extends Fragment implements ProfileView {

    private FragmentProfileBinding binding;
    private ProfilePresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentProfileBinding.bind(view);

        presenter = new ProfilePresenterImpl();
        presenter.attachView(this);
        presenter.loadUserProfile();

        binding.btnLogout.setOnClickListener(v ->
                presenter.onLogoutClicked()
        );
        binding.btnBack.setOnClickListener(v ->
                NavHostFragment.findNavController(this).navigateUp()
        );
    }

    @Override
    public void showUserData(User user) {
        binding.tvUserName.setText(user.getDisplayName());
        binding.tvUserEmail.setText(user.getEmail());
    }

    @Override
    public void showError(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToLogin() {
        NavHostFragment.findNavController(this)
                .navigate(R.id.action_profileFragment_to_loginFragment);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
        binding = null;
    }
}