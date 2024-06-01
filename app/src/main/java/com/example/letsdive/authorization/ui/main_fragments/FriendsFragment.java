package com.example.letsdive.authorization.ui.main_fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.letsdive.R;
import com.example.letsdive.databinding.FragmentTextbookBinding;

public class FriendsFragment extends Fragment {

    private FragmentTextbookBinding binding;

    public FriendsFragment() {
        super(R.layout.fragment_textbook);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentTextbookBinding.bind(view);
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }
}