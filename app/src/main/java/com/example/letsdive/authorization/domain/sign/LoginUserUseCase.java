package com.example.letsdive.authorization.domain.sign;

import androidx.annotation.NonNull;

import com.example.letsdive.authorization.domain.entities.FullUserEntity;
import com.example.letsdive.authorization.domain.entities.Status;

import java.util.function.Consumer;

public class LoginUserUseCase {
    private final SignUserRepository repo;

    public LoginUserUseCase(SignUserRepository repo) {
        this.repo = repo;
    }

    public void execute(
            @NonNull String username,
            @NonNull String password,
            Consumer<Status<FullUserEntity>> callback
    ) {
        repo.login(username, password, (status) -> {
            if (status.getStatusCode() != 200) repo.logout();
            callback.accept(status);
        });

    }
}