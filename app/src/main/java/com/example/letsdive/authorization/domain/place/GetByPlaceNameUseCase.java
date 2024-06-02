package com.example.letsdive.authorization.domain.place;

import androidx.annotation.NonNull;

import com.example.letsdive.authorization.domain.entities.PlaceEntity;
import com.example.letsdive.authorization.domain.entities.Status;

import java.util.function.Consumer;

public class GetByPlaceNameUseCase {
    private final PlaceRepository repo;

    public GetByPlaceNameUseCase(PlaceRepository repo) {
        this.repo = repo;
    }

    public void execute(@NonNull String placeName, @NonNull Consumer<Status<PlaceEntity>> callback) {
        repo.getByPlaceName(placeName, callback);
    }
}
