package com.example.letsdive.authorization.ui.main_fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
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
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;

import com.example.letsdive.R;
import com.example.letsdive.authorization.data.RecordRepositoryImpl;
import com.example.letsdive.authorization.data.UserRepositoryImpl;
import com.example.letsdive.authorization.domain.AddRecordToUserUseCase;
import com.example.letsdive.authorization.domain.entities.FullUserEntity;
import com.example.letsdive.authorization.domain.entities.RecordEntity;
import com.example.letsdive.authorization.domain.record.AddRecordUseCase;
import com.example.letsdive.authorization.domain.sign.LoginUserUseCase;
import com.example.letsdive.authorization.ui.recycler.RecordAdapter;
import com.example.letsdive.authorization.ui.recycler.SpacingItemDecorator;
import com.example.letsdive.authorization.ui.util_fragments.TimePickerFragment;
import com.example.letsdive.databinding.DialogWindowBinding;
import com.example.letsdive.databinding.FragmentDiaryBinding;
import com.example.letsdive.databinding.TitleBinding;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

public class DiaryFragment extends Fragment {

    private FragmentDiaryBinding binding;
    private final FullUserEntity user;

    private RecordAdapter adapter;

    private String date;
    private String startTime;
    private String endTime;

    private final AddRecordUseCase addRecordUseCase = new AddRecordUseCase(
            RecordRepositoryImpl.getInstance()
    );
    private final AddRecordToUserUseCase addRecordToUserUseCase = new AddRecordToUserUseCase(
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

        binding.faEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDateDialog();
            }
        });

        if (!user.getRecords().isEmpty()) {
            createAdapter(user.getRecords());
        }
    }

    private void createAdapter(Set<RecordEntity> setRecords) {
        List<RecordEntity> records = new ArrayList<>(setRecords);
        RecordAdapter adapter = new RecordAdapter(records);
        this.adapter = adapter;
        SpacingItemDecorator itemDecorator = new SpacingItemDecorator(35, 35);
        binding.rvRecord.addItemDecoration(itemDecorator);
        binding.rvRecord.setAdapter(adapter);
    }

    private void openDateDialog() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog pickerDialog  = new DatePickerDialog(requireContext(), R.style.DateDialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                date = dayOfMonth + "-" + month + "-" + year;
                openTimeDialog(true);
            }
        }, year, month, dayOfMonth);

        pickerDialog.show();
    }

    private void openTimeDialog(boolean is_startTime) {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        TimePickerDialog pickerDialog = new TimePickerDialog(getContext(), R.style.TimePickerDialogTheme, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String time = String.valueOf(hourOfDay) + ":" + String.valueOf(minute);
                if (is_startTime) {
                    startTime = time;
                    openTimeDialog(false);
                } else {
                    endTime = time;
                    openInputDialog();
                }
            }

        }, hour, minute, false);

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.title, null);
        TextView title = (TextView) dialogView.findViewById(R.id.tv_title);

        if (is_startTime) {
            title.setText("Начало погружения");
        } else {
            title.setText("Конец погружения");
        }


        pickerDialog.setCustomTitle(dialogView);

        pickerDialog.show();
    }

    private void openInputDialog() {
        final Dialog dialogWindow = new Dialog(getActivity());
        dialogWindow.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogWindow.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogWindow.setCancelable(false);
        dialogWindow.setContentView(R.layout.dialog_window);

        EditText placeEditText = (EditText) dialogWindow.findViewById(R.id.et_place);
        EditText depthEditText = (EditText) dialogWindow.findViewById(R.id.et_depth);

        Button confirmButton = (Button) dialogWindow.findViewById(R.id.btn_confirm);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (placeEditText.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Place name cannot be null", Toast.LENGTH_SHORT).show();
                } else if (depthEditText.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Depth cannot be null", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        long depth = Long.valueOf(depthEditText.getText().toString());
                        addRecordUseCase.execute(
                                placeEditText.getText().toString(),
                                date,
                                startTime,
                                endTime,
                                depth,
                                recordEntityStatus -> {
                                    RecordEntity record = recordEntityStatus.getValue();
                                    addRecordToUserUseCase.execute(user.getId(), record.getId(), fullUserEntityStatus -> {
                                                Set<RecordEntity> records = user.getRecords();
                                                records.add(record);
                                                if (records.size() == 1) {
                                                    createAdapter(records);
                                                }
                                                adapter.updateList(new ArrayList<>(records));
                                            }
                                    );
                                }
                        );
                        dialogWindow.dismiss();
                    } catch (RuntimeException e) {
                        Toast.makeText(getContext(), "Depth must be integer", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        dialogWindow.show();
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }
}
