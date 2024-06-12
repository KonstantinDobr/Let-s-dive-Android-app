package com.example.letsdive.authorization.domain.place;

import androidx.annotation.NonNull;

import com.example.letsdive.authorization.data.PlaceRepositoryImpl;
import com.example.letsdive.authorization.data.RecordRepositoryImpl;
import com.example.letsdive.authorization.domain.entities.PlaceEntity;
import com.example.letsdive.authorization.domain.entities.RecordEntity;
import com.example.letsdive.authorization.domain.entities.Status;

import java.util.function.Consumer;

public class AddPlaceUseCase {
    private final PlaceRepositoryImpl repo;

    public AddPlaceUseCase(PlaceRepositoryImpl repo) {
        this.repo = repo;
    }

    public void execute(
            @NonNull String placeName,
            @NonNull String information,
            double latitude,
            double longitude,
            @NonNull String recordId,
            long depth,
            Consumer<Status<PlaceEntity>> callback
    ) {
        repo.createPlace(
                placeName,
                information,
                latitude,
                longitude,
                recordId,
                depth,
                callback
        );
    }
}
