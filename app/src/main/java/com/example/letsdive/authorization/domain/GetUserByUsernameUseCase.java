package com.example.letsdive.authorization.domain;

import androidx.annotation.NonNull;

import com.example.letsdive.authorization.domain.entities.FullUserEntity;
import com.example.letsdive.authorization.domain.entities.Status;

import java.util.function.Consumer;

public class GetUserByUsernameUseCase {
    private final UserRepository repo;

    public GetUserByUsernameUseCase(UserRepository repo) {
        this.repo = repo;
    }

    public void execute(@NonNull String username, @NonNull Consumer<Status<FullUserEntity>> callback) {
        repo.getUserByUsername(username, callback);
    }
}
