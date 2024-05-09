package com.example.letsdive;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.letsdive.databinding.ActivityMainBinding;
import com.example.letsdive.main_fragments.DiaryFragment;
import com.example.letsdive.main_fragments.MapFragment;
import com.example.letsdive.main_fragments.ProfileFragment;
import com.example.letsdive.main_fragments.TextbookFragment;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fragmentManager = getSupportFragmentManager();

        binding.bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {

            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.item_profile) {
                    fragmentManager.beginTransaction()
                            .add(R.id.fcv_main_container, new ProfileFragment())
                            .commit();
                } else if (id == R.id.item_map) {
                    fragmentManager.beginTransaction()
                            .add(R.id.fcv_main_container, new MapFragment())
                            .commit();
                } else if (id == R.id.item_diary) {
                    fragmentManager.beginTransaction()
                            .add(R.id.fcv_main_container, new DiaryFragment())
                            .commit();
                } else if (id == R.id.item_textbook) {
                    fragmentManager.beginTransaction()
                            .add(R.id.fcv_main_container, new TextbookFragment())
                            .commit();
                }
                return true;
            }
        });
    }
}