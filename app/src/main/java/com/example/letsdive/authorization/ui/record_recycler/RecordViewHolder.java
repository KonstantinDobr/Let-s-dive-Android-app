package com.example.letsdive.authorization.ui.record_recycler;

import androidx.recyclerview.widget.RecyclerView;

import com.example.letsdive.authorization.domain.entities.RecordEntity;
import com.example.letsdive.databinding.ItemRecordBinding;

public class RecordViewHolder extends RecyclerView.ViewHolder {

    private ItemRecordBinding binding;

    public RecordViewHolder(ItemRecordBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(RecordEntity item) {
        binding.tvDate.setText("Дата: " + item.getDate());
        binding.tvTime.setText("Время: " + item.getStartDate() + " - " + item.getEndDate());
        binding.tvPlace.setText(item.getPlaceName());
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
    }
}
