package com.example.letsdive.authorization.domain.record;

import androidx.annotation.NonNull;

import com.example.letsdive.authorization.domain.UserRepository;
import com.example.letsdive.authorization.domain.entities.FullUserEntity;
import com.example.letsdive.authorization.domain.entities.RecordEntity;
import com.example.letsdive.authorization.domain.entities.Status;

import java.util.function.Consumer;

public class GetRecordByIdUseCase {
    private final RecordRepository repo;

    public GetRecordByIdUseCase(RecordRepository repo) {
        this.repo = repo;
    }

    public void execute(@NonNull String id, @NonNull Consumer<Status<RecordEntity>> callback) {
        repo.getRecord(id, callback);
    }
}
