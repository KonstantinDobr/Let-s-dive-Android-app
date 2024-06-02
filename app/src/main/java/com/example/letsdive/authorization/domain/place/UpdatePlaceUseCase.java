package com.example.letsdive.authorization.domain.place;

import androidx.annotation.NonNull;

import com.example.letsdive.authorization.data.PlaceRepositoryImpl;
import com.example.letsdive.authorization.domain.entities.PlaceEntity;
import com.example.letsdive.authorization.domain.entities.Status;

import java.util.function.Consumer;

public class UpdatePlaceUseCase {
    private final PlaceRepositoryImpl repo;

    public UpdatePlaceUseCase(PlaceRepositoryImpl repo) {
        this.repo = repo;
    }

    public void execute(
            @NonNull String id,
            @NonNull String placeName,
            @NonNull String information,
            double latitude,
            double longitude,
            Consumer<Status<PlaceEntity>> callback
    ) {
        repo.updatePlace(
                id,
                placeName,
                information,
                latitude,
                longitude,
                callback
        );
    }
}
