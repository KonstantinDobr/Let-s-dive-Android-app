package com.example.letsdive.authorization.ui.main_fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.letsdive.R;
import com.example.letsdive.authorization.domain.entities.FullUserEntity;
import com.example.letsdive.databinding.FragmentProfileBinding;

import java.io.Serializable;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    private final FullUserEntity user;

    public ProfileFragment(FullUserEntity user) {
        super(R.layout.fragment_profile);
        this.user = user;
    }

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        binding = FragmentProfileBinding.bind(view);

        binding.tvUsername.setText(user.getUsername());
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }
}
