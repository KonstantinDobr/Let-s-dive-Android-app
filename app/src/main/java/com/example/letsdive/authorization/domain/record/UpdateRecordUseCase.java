package com.example.letsdive.authorization.domain.record;

import androidx.annotation.NonNull;

import com.example.letsdive.authorization.data.RecordRepositoryImpl;
import com.example.letsdive.authorization.domain.entities.RecordEntity;
import com.example.letsdive.authorization.domain.entities.Status;

import java.util.function.Consumer;

public class UpdateRecordUseCase {
    private final RecordRepositoryImpl repo;

    public UpdateRecordUseCase(RecordRepositoryImpl repo) {
        this.repo = repo;
    }

    public void execute(
            @NonNull String id,
            @NonNull String placeName,
            @NonNull String date,
            @NonNull String startDate,
            @NonNull String endDate,
            @NonNull String information,
            long depth,
            Consumer<Status<RecordEntity>> callback
    ) {
        repo.updateRecord(
                id,
                placeName,
                date,
                startDate,
                endDate,
                information,
                depth,
                callback
        );
    }
}
