package com.example.letsdive.authorization.domain.sign;

import androidx.annotation.NonNull;

import com.example.letsdive.authorization.domain.entities.Status;

import java.util.function.Consumer;

public class CreateUserUseCase {
    private final SignUserRepository repo;

    public CreateUserUseCase(SignUserRepository repo) {
        this.repo = repo;
    }

    public void execute(
            @NonNull String login,
            @NonNull String password,
            Consumer<Status<Void>> callback
    ) {
        repo.createAccount(login, password, callback);
    }
}

