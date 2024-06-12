package com.example.letsdive.authorization.ui.main_fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.letsdive.R;
import com.example.letsdive.authorization.data.RecordRepositoryImpl;
import com.example.letsdive.authorization.data.UserRepositoryImpl;
import com.example.letsdive.authorization.domain.AddRecordToUserUseCase;
import com.example.letsdive.authorization.domain.DeleteRecordFromUserUseCase;
import com.example.letsdive.authorization.domain.entities.FullUserEntity;
import com.example.letsdive.authorization.domain.entities.RecordEntity;
import com.example.letsdive.authorization.domain.record.AddRecordUseCase;
import com.example.letsdive.authorization.domain.record.DeleteRecordByIdUseCase;
import com.example.letsdive.authorization.ui.record_recycler.RecordAdapter;
import com.example.letsdive.authorization.ui.utils.SpacingItemDecorator;
import com.example.letsdive.databinding.FragmentDiaryBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

public class DiaryFragment extends Fragment {
    private FragmentDiaryBinding binding;
    private final FullUserEntity user;

    private final DeleteRecordByIdUseCase deleteRecordByIdUseCase = new DeleteRecordByIdUseCase(
            RecordRepositoryImpl.getInstance()
    );
    private final DeleteRecordFromUserUseCase deleteRecordFromUserUseCase = new DeleteRecordFromUserUseCase(
            UserRepositoryImpl.getInstance()
    );

    public DiaryFragment(FullUserEntity user) {
        super(R.layout.fragment_diary);
        this.user = user;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentDiaryBinding.bind(view);

        if (!user.getRecords().isEmpty()) {
            createAdapter(user.getRecords());
        }
    }

    private void createAdapter(Set<RecordEntity> setRecords) {
        List<RecordEntity> records = new ArrayList<>(setRecords);
        RecordAdapter adapter = new RecordAdapter(records, getContext(), user);
        SpacingItemDecorator itemDecorator = new SpacingItemDecorator(35, 35, 35);
        binding.rvRecord.addItemDecoration(itemDecorator);
        binding.rvRecord.setAdapter(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                RecordEntity item = adapter.removeItem(position);
                deleteRecordFromUserUseCase.execute(
                        user.getId(),
                        item.getId(),
                        userEntityStatus -> {}
                );
                deleteRecordByIdUseCase.execute(
                        item.getId(),
                        voidStatus -> {}
                );
            }
        });

        itemTouchHelper.attachToRecyclerView(binding.rvRecord);
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }
}
