package com.example.letsdive.authorization.domain.place;

import androidx.annotation.NonNull;

import com.example.letsdive.authorization.domain.entities.PlaceEntity;
import com.example.letsdive.authorization.domain.entities.Status;

import java.util.function.Consumer;

public class DeletePlaceByIdUseCase {
    private final PlaceRepository repo;

    public DeletePlaceByIdUseCase(PlaceRepository repo) {
        this.repo = repo;
    }

    public void execute(@NonNull String id, @NonNull Consumer<Status<Void>> callback) {
        repo.deletePlaceById(id, callback);
    }
}
