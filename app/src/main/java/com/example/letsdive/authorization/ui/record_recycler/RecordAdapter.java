package com.example.letsdive.authorization.ui.record_recycler;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.letsdive.authorization.domain.entities.RecordEntity;
import com.example.letsdive.databinding.ItemRecordBinding;

import java.util.List;

public class RecordAdapter extends RecyclerView.Adapter<RecordViewHolder> {

    private List<RecordEntity> recordEntityList;

    public RecordAdapter(List<RecordEntity> recordEntityList) {
        this.recordEntityList = recordEntityList;
    }

    @NonNull
    @Override
    public RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ItemRecordBinding binding = ItemRecordBinding.inflate(
                LayoutInflater.from(parent.getContext())
        );

        return new RecordViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecordViewHolder holder, int position) {
        holder.bind(recordEntityList.get(position));
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
}
