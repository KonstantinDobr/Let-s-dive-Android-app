package com.example.letsdive.authorization.ui.main_fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.letsdive.R;
import com.example.letsdive.authorization.data.UserRepositoryImpl;
import com.example.letsdive.authorization.domain.AddRecordToUserUseCase;
import com.example.letsdive.authorization.domain.UpdateUserUseCase;
import com.example.letsdive.authorization.domain.entities.FullUserEntity;
import com.example.letsdive.authorization.domain.sign.IsUserExistUseCase;
import com.example.letsdive.databinding.FragmentProfileBinding;

import java.io.Serializable;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    private final FullUserEntity user;

    private boolean isEdit = false;

    private final UpdateUserUseCase updateUserUseCase = new UpdateUserUseCase(
            UserRepositoryImpl.getInstance()
    );

    private final IsUserExistUseCase isUserExistUseCase = new IsUserExistUseCase(
            UserRepositoryImpl.getInstance()
    );

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

        if (user.getEmail() != null && !user.getEmail().isEmpty()) {
            binding.etEmail.setText(user.getEmail());
        }

        if (user.getInformation() != null && !user.getInformation().isEmpty()) {
            binding.etInfo.setText(user.getInformation());
        }

        binding.etInfo.setFocusable(false);
        binding.etInfo.setFocusableInTouchMode(false);
        binding.etInfo.setClickable(false);

        binding.etEmail.setFocusable(false);
        binding.etEmail.setFocusableInTouchMode(false);
        binding.etEmail.setClickable(false);

        binding.faEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isEdit) {
                    binding.faEdit.setImageDrawable(getResources().getDrawable(R.drawable.ok));

                    binding.etInfo.setFocusable(true);
                    binding.etInfo.setFocusableInTouchMode(true);
                    binding.etInfo.setClickable(true);

                    binding.etEmail.setFocusable(true);
                    binding.etEmail.setFocusableInTouchMode(true);
                    binding.etEmail.setClickable(true);

                    isEdit = true;
                } else {

                    String newEmail = binding.etEmail.getText().toString();
                    String newInfo = binding.etInfo.getText().toString();
                    if (newEmail == null || newEmail.isEmpty()) {
                        Toast.makeText(getActivity(), "Email cannot be null", Toast.LENGTH_SHORT).show();
                    } else if (newInfo == null || newInfo.isEmpty()) {
                        Toast.makeText(getActivity(), "Information cannot be null", Toast.LENGTH_SHORT).show();
                    } else if (newEmail.length() >= 40) {
                        Toast.makeText(getActivity(), "Too long email", Toast.LENGTH_SHORT).show();
                    } else if (newInfo.length() >= 100) {
                        Toast.makeText(getActivity(), "Too long information", Toast.LENGTH_SHORT).show();
                    } else {
                            updateUserUseCase.execute(
                                    user.getId(),
                                    newEmail,
                                    newInfo,
                                    userEntityStatus -> {

                                    }
                            );

                            binding.etInfo.setFocusable(false);
                            binding.etInfo.setFocusableInTouchMode(false);
                            binding.etInfo.setClickable(false);

                            binding.etEmail.setFocusable(false);
                            binding.etEmail.setFocusableInTouchMode(false);
                            binding.etEmail.setClickable(false);

                            binding.faEdit.setImageDrawable(getResources().getDrawable(R.drawable.edit));

                            isEdit = false;
                        }
                    }
                }
        });
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }
}
