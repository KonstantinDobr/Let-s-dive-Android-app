package com.example.letsdive.authorization.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.letsdive.databinding.ActivityAuthorizationBinding;

public class AuthorizationActivity extends AppCompatActivity {

    private ActivityAuthorizationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAuthorizationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}