package com.example.letsdive.authorization.domain;

import androidx.annotation.NonNull;

import com.example.letsdive.authorization.domain.entities.FullUserEntity;
import com.example.letsdive.authorization.domain.entities.Status;

import java.util.function.Consumer;

public class AddPlaceToUserUseCase {
    private final UserRepository repo;

    public AddPlaceToUserUseCase(UserRepository repo) {
        this.repo = repo;
    }

    public void execute(
            @NonNull String userId,
            @NonNull String placeId,
            Consumer<Status<FullUserEntity>> callback
    ) {
        repo.addPlace(userId, placeId, callback);
    }
}
