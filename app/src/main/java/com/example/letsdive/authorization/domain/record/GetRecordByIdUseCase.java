package com.example.letsdive.authorization.domain.record;

import androidx.annotation.NonNull;

import com.example.letsdive.authorization.data.RecordRepositoryImpl;
import com.example.letsdive.authorization.domain.entities.RecordEntity;
import com.example.letsdive.authorization.domain.entities.Status;

import java.util.function.Consumer;

public class GetRecordByIdUseCase {
    private final RecordRepositoryImpl repo;

    public GetRecordByIdUseCase(RecordRepositoryImpl repo) {
        this.repo = repo;
    }

    public void execute(
            @NonNull String id,
            Consumer<Status<RecordEntity>> callback
    ) {
        repo.getRecord(
                id,
                callback
        );
    }
}
