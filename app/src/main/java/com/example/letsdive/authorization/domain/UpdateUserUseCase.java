package com.example.letsdive.authorization.domain;

import androidx.annotation.NonNull;

import com.example.letsdive.authorization.domain.entities.FullUserEntity;
import com.example.letsdive.authorization.domain.entities.Status;

import java.util.Set;
import java.util.function.Consumer;

public class UpdateUserUseCase {
    private final UserRepository repo;

    public UpdateUserUseCase(UserRepository repo) {
        this.repo = repo;
    }

    public void execute(
            @NonNull String id,
            @NonNull String username,
            @NonNull String photoUrl,
            @NonNull Set<Long> records,
            @NonNull Consumer<Status<Void>> callback) {
        repo.updateUser(
                id,
                username,
                photoUrl,
                records,
                callback);
    }
}
