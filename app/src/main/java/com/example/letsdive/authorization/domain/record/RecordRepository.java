package com.example.letsdive.authorization.domain.record;

import androidx.annotation.NonNull;

import com.example.letsdive.authorization.domain.entities.FullUserEntity;
import com.example.letsdive.authorization.domain.entities.RecordEntity;
import com.example.letsdive.authorization.domain.entities.Status;

import java.util.function.Consumer;

public interface RecordRepository {
    void createRecord(
            @NonNull String placeName,
            @NonNull String date,
            @NonNull String startDate,
            @NonNull String endDate,
            @NonNull String information,
            long depth,
            Consumer<Status<RecordEntity>> callback
    );

    void getRecord(@NonNull String id, @NonNull Consumer<Status<RecordEntity>> callback);

    void deleteRecord(@NonNull String id, @NonNull Consumer<Status<Void>> callback);

    void updateRecord(
            @NonNull String id,
            @NonNull String placeName,
            @NonNull String date,
            @NonNull String startDate,
            @NonNull String endDate,
            @NonNull String information,
            long depth,
            Consumer<Status<RecordEntity>> callback
    );
}
