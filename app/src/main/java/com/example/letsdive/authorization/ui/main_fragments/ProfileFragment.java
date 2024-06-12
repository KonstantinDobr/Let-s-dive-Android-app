package com.example.letsdive.authorization.ui.main_fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.letsdive.R;
import com.example.letsdive.authorization.data.UserRepositoryImpl;
import com.example.letsdive.authorization.domain.AddRecordToUserUseCase;
import com.example.letsdive.authorization.domain.UpdateUserUseCase;
import com.example.letsdive.authorization.domain.entities.FullUserEntity;
import com.example.letsdive.authorization.domain.sign.IsUserExistUseCase;
import com.example.letsdive.databinding.FragmentProfileBinding;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;

import java.io.IOError;
import java.io.Serializable;
import java.util.Objects;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    private final FullUserEntity user;

    private String url;

    private FirebaseStorage firebaseStorage = FirebaseStorage.getInstance("gs://lets-dive-422118.appspot.com");

    private boolean isEdit = false;

    private static final int PICK_IMAGE_REQUEST = 1;

    private final UpdateUserUseCase updateUserUseCase = new UpdateUserUseCase(
            UserRepositoryImpl.getInstance()
    );

    public ProfileFragment(FullUserEntity user) {
        super(R.layout.fragment_profile);
        this.user = user;
        this.url = user.getPhotoUrl();
    }

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        binding = FragmentProfileBinding.bind(view);

        if (user.getPhotoUrl() != null && !user.getPhotoUrl().isEmpty()) {
            try {
                StorageReference reference = firebaseStorage.getReference(url);
                Glide
                        .with(requireContext())
                        .load(reference)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(binding.ivBack);
            } catch (Error e) {
                Log.e("FireBase", "PathToFirebase error");
            }
        }

        binding.toolbar.setTitle(user.getUsername());

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

        binding.faPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

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
                    } else if (newInfo.length() >= 300) {
                        Toast.makeText(getActivity(), "Too long information", Toast.LENGTH_SHORT).show();
                    } else {
                        updateUserUseCase.execute(
                                user.getId(),
                                newEmail,
                                newInfo,
                                user.getPhotoUrl(),
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();

            String[] projection = {MediaStore.Images.Media.WIDTH, MediaStore.Images.Media.HEIGHT};
            Cursor cursor = requireActivity().getContentResolver().query(imageUri, projection, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                int widthIndex = cursor.getColumnIndex(MediaStore.Images.Media.WIDTH);
                int heightIndex = cursor.getColumnIndex(MediaStore.Images.Media.HEIGHT);
                int width = cursor.getInt(widthIndex);
                int height = cursor.getInt(heightIndex);

                if (width <= height) {
                    Toast.makeText(getActivity(), "Orientation must be horizontal", Toast.LENGTH_SHORT).show();
                    return;
                }

                cursor.close();
            }

            if (url == null || url.isEmpty()) {
                url = "/background" + user.getId();
                updateUserUseCase.execute(
                        user.getId(),
                        user.getEmail(),
                        user.getInformation(),
                        url,
                        userEntityStatus -> {

                        }
                );
            }

            StorageMetadata metadata = new StorageMetadata.Builder()
                    .setCacheControl("no-cache, no-store, must-revalidate")
                    .build();

            StorageReference reference = firebaseStorage.getReference().child(url);
            reference.putFile(imageUri, metadata);
            try {
                Glide.with(requireContext()).load(imageUri).into(binding.ivBack);
            } catch (IOError e) {
                Toast.makeText(getActivity(), "PathToFirebase error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }
}
