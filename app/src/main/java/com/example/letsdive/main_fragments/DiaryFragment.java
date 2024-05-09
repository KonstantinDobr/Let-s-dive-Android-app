package com.example.letsdive.main_fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.letsdive.R;
import com.example.letsdive.databinding.FragmentDiaryBinding;

public class DiaryFragment extends Fragment {

    private FragmentDiaryBinding binding;

    public DiaryFragment() {
        super(R.layout.fragment_diary);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentDiaryBinding.bind(view);
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }
}
