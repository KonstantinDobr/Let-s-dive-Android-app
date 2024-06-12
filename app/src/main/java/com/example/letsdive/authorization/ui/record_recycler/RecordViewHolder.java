package com.example.letsdive.authorization.ui.record_recycler;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.example.letsdive.authorization.domain.entities.RecordEntity;
import com.example.letsdive.databinding.ItemRecordBinding;

public class RecordViewHolder extends RecyclerView.ViewHolder {

    public ItemRecordBinding binding;

    public RecordViewHolder(ItemRecordBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(RecordEntity item) {
        if (item.getDate() != null && !item.getDate().isEmpty() && !item.getDate().equals("")) {
            binding.tvLabelData.setText("Погружение от " + item.getDate());
        } else {
            binding.tvLabelData.setText("Дата не добавлена");
        }
        binding.tvLabelPlace.setText(item.getPlaceName());
    }

    public void changeVisibility(boolean key, RecordEntity item) {
        if (key) {
            binding.expandableLayout.setVisibility(View.VISIBLE);
            if (item.getDate() != null && !item.getDate().isEmpty()) {
                binding.tvLabelData.setText("Погружение от " + item.getDate());
                binding.tvDate.setText("Дата: " + item.getDate());
            } else {
                binding.tvDate.setText("Дата не добавлена");
                binding.tvLabelData.setText("Дата не добавлена");
            }
            if (
                    item.getStartDate() != null && !item.getStartDate().isEmpty() &&
                            item.getEndDate() != null && !item.getEndDate().isEmpty()
            ) {
                binding.tvTime.setText("Время: " + item.getStartDate() + " - " + item.getEndDate());
            } else {
                binding.tvTime.setText("Время не добавлено");
            }

            binding.tvPlace.setText(item.getPlaceName());

            if (item.getDepth() != -1) {
                long depth = item.getDepth();
                String end = "";
                if (depth % 100 >= 10 && depth % 100 <= 20) {
                    end = "метров";
                } else if (depth % 10 == 1) {
                    end = "метр";
                } else if (depth % 10 >= 2 && depth % 10 <= 4){
                    end = "метра";
                } else {
                    end = "метров";
                }
                binding.tvDepth.setText("Глубина погружения " + String.valueOf(item.getDepth()) + " " + end);
            } else {
                binding.tvDepth.setText("Глубина погружения не добавлена");
            }

            binding.tvInformation.setText(item.getInformation());

        } else {
            binding.expandableLayout.setVisibility(View.GONE);
        }
    }
}
