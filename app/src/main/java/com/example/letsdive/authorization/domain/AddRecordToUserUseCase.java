package com.example.letsdive.authorization.domain;

import androidx.annotation.NonNull;

import com.example.letsdive.authorization.domain.entities.FullUserEntity;
import com.example.letsdive.authorization.domain.entities.Status;
import com.example.letsdive.authorization.domain.sign.SignUserRepository;

import java.util.function.Consumer;

public class AddRecordToUserUseCase {
    private final UserRepository repo;

    public AddRecordToUserUseCase(UserRepository repo) {
        this.repo = repo;
    }

    public void execute(
            @NonNull String userId,
            @NonNull String recordId,
            Consumer<Status<FullUserEntity>> callback
    ) {
        repo.addRecord(userId, recordId, callback);
    }
}
