package com.example.letsdive.authorization.domain;

import androidx.annotation.NonNull;

import com.example.letsdive.authorization.domain.entities.FullUserEntity;
import com.example.letsdive.authorization.domain.entities.Status;

import java.util.function.Consumer;

public class DeleteRecordFromUserUseCase {
    private final UserRepository repo;

    public DeleteRecordFromUserUseCase(UserRepository repo) {
        this.repo = repo;
    }

    public void execute(
            @NonNull String userId,
            @NonNull String recordId,
            Consumer<Status<FullUserEntity>> callback
    ) {
        repo.deleteRecord(userId, recordId, callback);
    }
}
