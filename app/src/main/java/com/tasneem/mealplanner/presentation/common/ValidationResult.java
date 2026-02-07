package com.tasneem.mealplanner.presentation.common;

import androidx.annotation.StringRes;

public class ValidationResult {
    private final boolean valid;
    @StringRes private final Integer errorStringResource;

    private ValidationResult(boolean valid, Integer errorMessage) {
        this.valid = valid;
        this.errorStringResource = errorMessage;
    }

    public static ValidationResult success() {
        return new ValidationResult(true, null);
    }

    public static ValidationResult error(Integer message) {
        return new ValidationResult(false, message);
    }

    public boolean isValid() {
        return valid;
    }

    public Integer getErrorStringResource() {
        return errorStringResource;
    }
}
