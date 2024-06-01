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
import com.example.letsdive.authorization.data.UserRepositoryImpl;
import com.example.letsdive.authorization.domain.GetUserByUsernameUseCase;
import com.example.letsdive.authorization.domain.entities.FullUserEntity;
import com.example.letsdive.authorization.ui.main_fragments.DiaryFragment;
import com.example.letsdive.authorization.ui.main_fragments.MapFragment;
import com.example.letsdive.authorization.ui.main_fragments.ProfileFragment;
import com.example.letsdive.authorization.ui.main_fragments.FriendsFragment;
import com.example.letsdive.authorization.ui.services.MyMapServices;
import com.example.letsdive.databinding.ActivityAuthorizationBinding;
import com.google.android.material.navigation.NavigationBarView;

public class AuthorizationActivity extends AppCompatActivity implements Postman {

    private ActivityAuthorizationBinding binding;

    private FragmentManager fragmentManager;

    private String username;

    private final GetUserByUsernameUseCase getUserByUsernameUseCase = new GetUserByUsernameUseCase(
            UserRepositoryImpl.getInstance()
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAuthorizationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fragmentManager = getSupportFragmentManager();
        getWindow().setStatusBarColor(getColor(R.color.background_color));

        binding.bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {

            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                getUserByUsernameUseCase.execute(
                        username,
                        userEntityStatus -> {
                            FullUserEntity user = userEntityStatus.getValue();
                            int id = item.getItemId();
                            if (user == null) {
                                Log.d("AuthorizationActivity", "Null user");
                            } else if (id == R.id.item_profile) {
                                ProfileFragment fragment = new ProfileFragment(user);
                                fragmentManager.beginTransaction()
                                        .replace(R.id.root, fragment)
                                        .commit();
                            } else if (id == R.id.item_map) {
                                MapFragment fragment = new MapFragment();
                                fragment.getMapAsync(new MyMapServices(AuthorizationActivity.this, user));
                                fragmentManager.beginTransaction()
                                        .replace(R.id.root, fragment)
                                        .commit();
                            } else if (id == R.id.item_diary) {
                                DiaryFragment fragment = new DiaryFragment(user);
                                fragmentManager.beginTransaction()
                                        .replace(R.id.root, fragment)
                                        .commit();
                            } else if (id == R.id.item_friends) {
                                fragmentManager.beginTransaction()
                                        .replace(R.id.root, new FriendsFragment())
                                        .commit();
                            }
                        });

                return true;
            }
        });

    }

    @Override
    public void fragmentMail(FullUserEntity user) {
        this.username = user.getUsername();
        binding.bottomNavigation.setVisibility(View.VISIBLE);
    }
}