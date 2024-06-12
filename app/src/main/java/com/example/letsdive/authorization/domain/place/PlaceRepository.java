package com.example.letsdive.authorization.domain.place;

import androidx.annotation.NonNull;

import com.example.letsdive.authorization.domain.entities.PlaceEntity;
import com.example.letsdive.authorization.domain.entities.Status;

import java.util.function.Consumer;

public interface PlaceRepository {
    void createPlace(
            @NonNull String placeName,
            @NonNull String information,
            double latitude,
            double longitude,
            @NonNull String recordId,
            long depth,
            Consumer<Status<PlaceEntity>> callback
    );

    void getPlace(@NonNull String id, @NonNull Consumer<Status<PlaceEntity>> callback);

    void updatePlace(
            @NonNull String id,
            @NonNull String placeName,
            @NonNull String information,
            double latitude,
            double longitude,
            @NonNull String recordId,
            long depth,
            Consumer<Status<PlaceEntity>> callback
    );

    void getByPlaceName(@NonNull String placeName, @NonNull String userId, Consumer<Status<PlaceEntity>> callback);

    void deletePlaceById(@NonNull String id, @NonNull Consumer<Status<Void>> callback);
}
