package com.example.letsdive.authorization.ui.record_recycler;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.letsdive.R;
import com.example.letsdive.authorization.data.RecordRepositoryImpl;
import com.example.letsdive.authorization.data.UserRepositoryImpl;
import com.example.letsdive.authorization.domain.GetUserByIdUseCase;
import com.example.letsdive.authorization.domain.entities.FullUserEntity;
import com.example.letsdive.authorization.domain.entities.RecordEntity;
import com.example.letsdive.authorization.domain.record.AddRecordUseCase;
import com.example.letsdive.authorization.domain.record.UpdateRecordUseCase;
import com.example.letsdive.databinding.ItemRecordBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

public class RecordAdapter extends RecyclerView.Adapter<RecordViewHolder> {

    private List<RecordEntity> recordEntityList;
    private Context context;
    private FullUserEntity user;
    private String startTime;
    private String endTime;

    private final UpdateRecordUseCase updateRecordUseCase = new UpdateRecordUseCase(
            RecordRepositoryImpl.getInstance()
    );
    private final GetUserByIdUseCase getUserByIdUseCase = new GetUserByIdUseCase(
            UserRepositoryImpl.getInstance()
    );

    public RecordAdapter(List<RecordEntity> recordEntityList, Context context, FullUserEntity user) {
        this.recordEntityList = recordEntityList;
        this.context = context;
        this.user = user;
    }

    @NonNull
    @Override
    public RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ItemRecordBinding binding = ItemRecordBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );

        return new RecordViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecordViewHolder holder, int position) {
        RecordEntity item = recordEntityList.get(position);

        holder.bind(item);

        boolean isExpanded = item.isExpanded();
        holder.changeVisibility(isExpanded, item);

        holder.binding.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setExpanded(!item.isExpanded());
                notifyItemChanged(position);
            }
        });

        holder.binding.tvLabelData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDateDialog(item, holder);
            }
        });

        holder.binding.tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDateDialog(item, holder);
            }
        });

        holder.binding.tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTimeDialog(true, item, holder);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recordEntityList.size();
    }

    public void updateList (List<RecordEntity> items) {
        if (items != null && !items.isEmpty()) {
            recordEntityList.clear();
            recordEntityList.addAll(items);
            notifyDataSetChanged();
        }
    }

    public RecordEntity removeItem(int position) {
        RecordEntity item = recordEntityList.get(position);
        recordEntityList.remove(position);
        notifyItemRemoved(position);
        return item;
    }

    private void openDateDialog(RecordEntity item, @NonNull RecordViewHolder holder) {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog pickerDialog  = new DatePickerDialog(context, R.style.DateDialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                String myYear = String.valueOf(year);
                String myMonth = String.valueOf(month + 1);
                String myDay = String.valueOf(dayOfMonth);

                if (month < 9) {
                    myMonth = '0' + myMonth;
                }
                if (dayOfMonth < 10) {
                    myDay = '0' + myDay;
                }
                String date = myDay + "-" + myMonth + "-" + myYear;
                updateRecordUseCase.execute(
                        item.getId(),
                        item.getPlaceName(),
                        date,
                        item.getStartDate(),
                        item.getEndDate(),
                        item.getInformation(),
                        item.getDepth(),
                        recordEntityStatus -> {
                        }
                );
                getUserByIdUseCase.execute(
                        user.getId(),
                        userEntityStatus -> {
                            List<RecordEntity> records = new ArrayList<>(userEntityStatus.getValue().getRecords());
                            updateList(records);
                        }
                );
            }
        }, year, month, dayOfMonth);

        pickerDialog.show();
    }

    private void openTimeDialog(boolean is_startTime, RecordEntity item, @NonNull RecordViewHolder holder) {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        TimePickerDialog pickerDialog = new TimePickerDialog(context, R.style.TimePickerDialogTheme, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                String myHour = String.valueOf(hourOfDay);
                String myMinute = String.valueOf(minute);

                if (hourOfDay < 10) {
                    myHour = '0' + myHour;
                }
                if (minute < 10) {
                    myMinute = '0' + myMinute;
                }

                String time = myHour + ":" + myMinute;
                if (is_startTime) {
                    startTime = time;
                    openTimeDialog(false, item, holder);
                } else {
                    endTime = time;
                    updateRecordUseCase.execute(
                            item.getId(),
                            item.getPlaceName(),
                            item.getDate(),
                            startTime,
                            endTime,
                            item.getInformation(),
                            item.getDepth(),
                            recordEntityStatus -> {
                            }
                    );
                    getUserByIdUseCase.execute(
                            user.getId(),
                            userEntityStatus -> {
                                List<RecordEntity> records = new ArrayList<>(userEntityStatus.getValue().getRecords());
                                updateList(records);
                            }
                    );
                }
            }

        }, hour, minute, false);

        LayoutInflater inflater = LayoutInflater.from(context);
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
}
