package com.example.letsdive.authorization.domain;

import androidx.annotation.NonNull;

import com.example.letsdive.authorization.domain.entities.FullUserEntity;
import com.example.letsdive.authorization.domain.entities.Status;

import java.util.function.Consumer;

public class DeletePlaceFromUserUseCase {

    private final UserRepository repo;

    public DeletePlaceFromUserUseCase(UserRepository repo) {
        this.repo = repo;
    }

    public void execute(
            @NonNull String userId,
            @NonNull String placeId,
            Consumer<Status<FullUserEntity>> callback
    ) {
        repo.deletePlace(userId, placeId, callback);
    }

}
