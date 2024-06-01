package com.example.letsdive.authorization.ui.user_recycler;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.letsdive.authorization.domain.entities.ItemUserEntity;
import com.example.letsdive.databinding.ItemUserBinding;

public class UserViewHolder extends RecyclerView.ViewHolder {
    private ItemUserBinding binding;

    public UserViewHolder(ItemUserBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(ItemUserEntity user) {
        binding.tvUsername.setText(user.getUsername());
        if (user.getEmail() != null && !user.getEmail().isEmpty()) {
            binding.tvEmail.setText(user.getEmail());
        }
    }
}
