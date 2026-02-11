package com.tasneem.mealplanner.presentation.search.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tasneem.mealplanner.data.datasource.meals.model.Area;
import com.tasneem.mealplanner.databinding.ItemAreaCircleBinding;
import com.tasneem.mealplanner.presentation.utils.GlideUtil;

import java.util.ArrayList;
import java.util.List;

public class AreasAdapter extends RecyclerView.Adapter<AreasAdapter.ViewHolder> {

    private List<Area> areas = new ArrayList<>();
    private final OnAreaClickListener listener;

    public interface OnAreaClickListener {
        void onAreaClick(Area area);
    }

    public AreasAdapter(OnAreaClickListener listener) {
        this.listener = listener;
    }

    public void setAreas(List<Area> areas) {
        this.areas = areas != null ? areas : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAreaCircleBinding binding = ItemAreaCircleBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(areas.get(position));
    }

    @Override
    public int getItemCount() {
        return areas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemAreaCircleBinding binding;

        ViewHolder(ItemAreaCircleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Area area) {
            binding.areaName.setText(area.getName());

            // Load area flag using Glide
            GlideUtil.loadImage(
                    binding.getRoot(),
                    area.getFlagUrl(),
                    binding.areaFlag
            );

            // Set click listener
            binding.getRoot().setOnClickListener(v -> listener.onAreaClick(area));
        }
    }
}