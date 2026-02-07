package com.tasneem.mealplanner.presentation.utils;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.tasneem.mealplanner.R;

public final class GlideUtil {

    private GlideUtil() {}

    public static void loadImage(
            @NonNull View view,
            @Nullable String url,
            @NonNull ImageView imageView
    ) {
        if (url == null || url.isEmpty()) {
            imageView.setImageResource(R.drawable.img_placeholder);
            return;
        }

        Glide.with(view)
                .load(url)
                .placeholder(R.drawable.img_placeholder)
                .error(R.drawable.img_placeholder)
                .centerCrop()
                .into(imageView);
    }
}
