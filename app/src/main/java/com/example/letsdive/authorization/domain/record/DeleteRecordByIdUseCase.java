package com.example.letsdive.authorization.domain.record;

import androidx.annotation.NonNull;

import com.example.letsdive.authorization.domain.UserRepository;
import com.example.letsdive.authorization.domain.entities.FullUserEntity;
import com.example.letsdive.authorization.domain.entities.Status;

import java.util.function.Consumer;

public class DeleteRecordByIdUseCase {

    private final RecordRepository repo;

    public DeleteRecordByIdUseCase(RecordRepository repo) {
        this.repo = repo;
    }

    public void execute(
            @NonNull String id,
            Consumer<Status<Void>> callback
    ) {
        repo.deleteRecord(id, callback);
    }
}
