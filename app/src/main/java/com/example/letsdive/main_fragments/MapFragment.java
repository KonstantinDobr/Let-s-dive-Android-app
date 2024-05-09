package com.example.letsdive.main_fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.letsdive.R;
import com.example.letsdive.databinding.FragmentMapBinding;

public class MapFragment extends Fragment {

    private FragmentMapBinding binding;

    public MapFragment() {
        super(R.layout.fragment_map);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentMapBinding.bind(view);
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }
}
