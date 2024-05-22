package com.example.letsdive.authorization.ui.main_fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;

import com.example.letsdive.R;
import com.example.letsdive.authorization.data.RecordRepositoryImpl;
import com.example.letsdive.authorization.data.UserRepositoryImpl;
import com.example.letsdive.authorization.domain.UpdateUserUseCase;
import com.example.letsdive.authorization.domain.entities.FullUserEntity;
import com.example.letsdive.authorization.domain.record.AddRecordUseCase;
import com.example.letsdive.authorization.ui.util_fragments.TimePickerFragment;
import com.example.letsdive.databinding.FragmentDiaryBinding;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

public class DiaryFragment extends Fragment {

    private FragmentDiaryBinding binding;

    private final FullUserEntity user;

    public DiaryFragment(FullUserEntity user) {
        super(R.layout.fragment_diary);
        this.user = user;
    }

    public final UpdateUserUseCase updateUserUseCase = new UpdateUserUseCase(
            UserRepositoryImpl.getInstance()
    );
    public final AddRecordUseCase addRecordUseCase = new AddRecordUseCase(
            RecordRepositoryImpl.getInstance()
    );

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentDiaryBinding.bind(view);

        MaterialDatePicker<Long> materialDatePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select Date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build();

        binding.faEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getActivity().getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
            }
        });

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long aLong) {
                String date = new SimpleDateFormat("MM-dd-yyy", Locale.getDefault()).format(new Date(aLong));
                binding.tvDiaryHeader.setText(MessageFormat.format("Selected Date: {0}", date));
                addRecordUseCase.execute(
                        "abacaba",
                        date,
                        "abacaba",
                        "abacaba",
                        10,
                        status -> {

                        });
            }
        });
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }
}
