package com.example.letsdive.authorization.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.letsdive.R;
import com.example.letsdive.authorization.domain.entities.FullUserEntity;
import com.example.letsdive.authorization.ui.main_fragments.DiaryFragment;
import com.example.letsdive.authorization.ui.main_fragments.MapFragment;
import com.example.letsdive.authorization.ui.main_fragments.ProfileFragment;
import com.example.letsdive.authorization.ui.main_fragments.TextbookFragment;
import com.example.letsdive.databinding.ActivityAuthorizationBinding;
import com.google.android.material.navigation.NavigationBarView;

import java.io.Serializable;

public class AuthorizationActivity extends AppCompatActivity implements Postman {

    private ActivityAuthorizationBinding binding;

    private FragmentManager fragmentManager;

    private FullUserEntity user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAuthorizationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fragmentManager = getSupportFragmentManager();

        binding.bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {

            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (user == null) {
                    Log.d("AuthorizationActivity", "Null user");
                } else if (id == R.id.item_profile) {
                    ProfileFragment fragment = new ProfileFragment(user);
                    fragmentManager.beginTransaction()
                            .add(R.id.root, fragment)
                            .commit();
                } else if (id == R.id.item_map) {
                    fragmentManager.beginTransaction()
                            .add(R.id.root, new MapFragment())
                            .commit();
                } else if (id == R.id.item_diary) {
                    DiaryFragment fragment = new DiaryFragment(user);
                    fragmentManager.beginTransaction()
                            .add(R.id.root, fragment)
                            .commit();
                } else if (id == R.id.item_textbook) {
                    fragmentManager.beginTransaction()
                            .add(R.id.root, new TextbookFragment())
                            .commit();
                }
                return true;
            }
        });

    }

    @Override
    public void fragmentMail(FullUserEntity user) {
        this.user = user;
//        Log.d("ABACABA", user.getUsername());
        binding.bottomNavigation.setVisibility(View.VISIBLE);
    }
}