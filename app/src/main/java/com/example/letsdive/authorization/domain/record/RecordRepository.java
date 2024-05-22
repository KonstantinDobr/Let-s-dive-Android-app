package com.example.letsdive.authorization.domain.record;

import androidx.annotation.NonNull;

import com.example.letsdive.authorization.domain.entities.RecordEntity;
import com.example.letsdive.authorization.domain.entities.Status;

import java.util.function.Consumer;

public interface RecordRepository {
    void getRecord(@NonNull String id, @NonNull Consumer<Status<RecordEntity>> callback);

    void createRecord(@NonNull String placeName,
                      @NonNull String date,
                      @NonNull String startDate,
                      @NonNull String endDate,
                      int depth,
                      @NonNull Consumer<Status<Void>> callback);
}
