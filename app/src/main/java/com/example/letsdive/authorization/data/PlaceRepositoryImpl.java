package com.example.letsdive.authorization.data;

import androidx.annotation.NonNull;

import com.example.letsdive.authorization.data.dto.PlaceInitDto;
import com.example.letsdive.authorization.data.network.RetrofitFactory;
import com.example.letsdive.authorization.data.source.PlaceApi;
import com.example.letsdive.authorization.data.utils.CallToConsumer;
import com.example.letsdive.authorization.domain.entities.PlaceEntity;
import com.example.letsdive.authorization.domain.entities.Status;
import com.example.letsdive.authorization.domain.place.PlaceRepository;

import java.util.function.Consumer;

public class PlaceRepositoryImpl implements PlaceRepository {

    private static PlaceRepositoryImpl INSTANCE;

    private PlaceApi placeApi = RetrofitFactory.getInstance().getPlaceApi();

    private PlaceRepositoryImpl() {}

    public static synchronized PlaceRepositoryImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PlaceRepositoryImpl();
        }
        return INSTANCE;
    }

    @Override
    public void createPlace(
            @NonNull String placeName,
            @NonNull String information,
            double latitude,
            double longitude,
            @NonNull String recordId,
            long depth,
            Consumer<Status<PlaceEntity>> callback
    ) {
        placeApi.createPlace(new PlaceInitDto(
                placeName,
                information,
                latitude,
                longitude,
                recordId,
                depth,
                null
        )).enqueue(new CallToConsumer<>(
                callback,
                placeDto -> {
                    final String resultId = placeDto.id;
                    final String resultPlaceName = placeDto.placeName;
                    final String resultInformation = placeDto.information;

                    if (
                            resultId != null &&
                                    resultPlaceName != null &&
                    resultInformation != null
                    ) {
                        return new PlaceEntity(
                                resultId,
                                resultPlaceName,
                                resultInformation,
                                placeDto.latitude,
                                placeDto.longitude,
                                recordId,
                                placeDto.depth
                        );
                    } else {
                        return null;
                    }
                }));
    }

    @Override
    public void getPlace(@NonNull String id, @NonNull Consumer<Status<PlaceEntity>> callback) {

    }

    @Override
    public void updatePlace(
            @NonNull String id,
            @NonNull String placeName,
            @NonNull String information,
            double latitude,
            double longitude,
            @NonNull String recordId,
            long depth,
            Consumer<Status<PlaceEntity>> callback
    ) {
        placeApi.updatePlace(id, new PlaceInitDto(
                placeName,
                information,
                latitude,
                longitude,
                recordId,
                depth,
                null
        )).enqueue(new CallToConsumer<>(
                callback,
                placeDto -> null
        ));
    }

    @Override
    public void getByPlaceName(@NonNull String placeName, @NonNull String userId, Consumer<Status<PlaceEntity>> callback) {
        placeApi.getByPlaceName(placeName, userId).enqueue(new CallToConsumer<>(
                callback,
                placeDto -> {
                    final String resultId = placeDto.id;
                    final String resultPlaceName = placeDto.placeName;
                    final String resultInformation = placeDto.information;
                    final String recordId = placeDto.recordId;

                    if (
                            resultId != null &&
                                    resultPlaceName != null &&
                                    resultInformation != null&&
                                    recordId != null
                    ) {
                        return new PlaceEntity(
                                resultId,
                                resultPlaceName,
                                resultInformation,
                                placeDto.latitude,
                                placeDto.longitude,
                                recordId,
                                placeDto.depth
                        );
                    } else {
                        return null;
                    }
                }
        ));
    }

    @Override
    public void deletePlaceById(@NonNull String id, @NonNull Consumer<Status<Void>> callback) {
        placeApi.deleteById(id).enqueue(new CallToConsumer<>(
                callback,
                dto -> null
        ));
    }
}
