package com.example.letsdive.authorization.domain.record;

import androidx.annotation.NonNull;

import com.example.letsdive.authorization.data.RecordRepositoryImpl;
import com.example.letsdive.authorization.domain.entities.RecordEntity;
import com.example.letsdive.authorization.domain.entities.Status;
import com.example.letsdive.authorization.domain.sign.SignUserRepository;

import java.util.function.Consumer;

public class AddRecordUseCase {
    private final RecordRepositoryImpl repo;

    public AddRecordUseCase(RecordRepositoryImpl repo) {
        this.repo = repo;
    }

    public void execute(
            @NonNull String placeName,
            @NonNull String date,
            @NonNull String startDate,
            @NonNull String endDate,
            long depth,
            Consumer<Status<RecordEntity>> callback
    ) {
        repo.createRecord(
                placeName,
                date,
                startDate,
                endDate,
                depth,
                callback
        );
    }
}
