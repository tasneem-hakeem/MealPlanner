package com.tasneem.mealplanner.presentation.common;

import android.util.Patterns;

import com.tasneem.mealplanner.R;

public final class UserInputValidator {

    private UserInputValidator() {
    }

    public static ValidationResult validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return ValidationResult.error(R.string.email_cannot_be_empty);
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult.error(R.string.invalid_email_format);
        }

        return ValidationResult.success();
    }

    public static ValidationResult validatePassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            return ValidationResult.error(R.string.password_cannot_be_empty);
        }

        if (password.length() < 6) {
            return ValidationResult.error(R.string.password_must_be_at_least_6_characters);
        }

        return ValidationResult.success();
    }
}
