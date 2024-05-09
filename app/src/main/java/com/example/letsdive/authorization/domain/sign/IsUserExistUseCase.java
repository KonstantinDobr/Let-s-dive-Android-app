package com.example.letsdive.authorization.domain.sign;

import androidx.annotation.NonNull;

import com.example.letsdive.authorization.domain.entities.Status;

import java.util.function.Consumer;

public class IsUserExistUseCase {
    private final SignUserRepository repo;

    public IsUserExistUseCase(SignUserRepository repo) {
        this.repo = repo;
    }

    public void execute(@NonNull String login, Consumer<Status<Boolean>> callback) {
        repo.isExistUser(login, status -> {
            boolean isAvailable = status.getStatusCode() == 200 || status.getStatusCode() == 404;
            callback.accept(
                    new Status<>(
                            status.getStatusCode(),
                            isAvailable ? status.getStatusCode() == 200 : null,
                            status.getErrors()
                    )
            );
        });
    }
}
