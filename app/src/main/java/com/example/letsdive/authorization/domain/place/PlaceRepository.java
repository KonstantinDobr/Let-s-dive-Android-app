package com.example.letsdive.authorization.domain.place;

import androidx.annotation.NonNull;

import com.example.letsdive.authorization.domain.entities.PlaceEntity;
import com.example.letsdive.authorization.domain.entities.RecordEntity;
import com.example.letsdive.authorization.domain.entities.Status;

import java.util.function.Consumer;

public interface PlaceRepository {
    void createPlace(
            @NonNull String placeName,
            @NonNull String information,
            double latitude,
            double longitude,
            Consumer<Status<PlaceEntity>> callback
    );

    void getPlace(@NonNull String id, @NonNull Consumer<Status<PlaceEntity>> callback);
}
