package com.example.letsdive.authorization.domain;

import androidx.annotation.NonNull;

import com.example.letsdive.authorization.domain.entities.FullUserEntity;
import com.example.letsdive.authorization.domain.entities.PlaceEntity;
import com.example.letsdive.authorization.domain.entities.RecordEntity;
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
            @NonNull String email,
            @NonNull String information,
            @NonNull String photoUrl,
            @NonNull Consumer<Status<FullUserEntity>> callback
    ) {
        repo.update(id,
                email,
                information,
                photoUrl,
                callback);
    }
}
