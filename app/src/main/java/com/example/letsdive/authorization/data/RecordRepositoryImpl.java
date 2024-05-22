package com.example.letsdive.authorization.data;

import androidx.annotation.NonNull;

import com.example.letsdive.authorization.data.dto.RecordDto;
import com.example.letsdive.authorization.data.dto.RecordInitDto;
import com.example.letsdive.authorization.data.network.RetrofitFactory;
import com.example.letsdive.authorization.data.source.CredentialsDataSource;
import com.example.letsdive.authorization.data.source.RecordApi;
import com.example.letsdive.authorization.data.source.UserApi;
import com.example.letsdive.authorization.data.utils.CallToConsumer;
import com.example.letsdive.authorization.domain.entities.FullUserEntity;
import com.example.letsdive.authorization.domain.entities.RecordEntity;
import com.example.letsdive.authorization.domain.entities.Status;
import com.example.letsdive.authorization.domain.record.RecordRepository;

import java.util.function.Consumer;

public class RecordRepositoryImpl implements RecordRepository {

    private static RecordRepositoryImpl INSTANCE;
    private final RecordApi recordApi = RetrofitFactory.getInstance().getRecordApi();

    private RecordRepositoryImpl() {}

    public static synchronized RecordRepositoryImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RecordRepositoryImpl();
        }
        return INSTANCE;
    }

    @Override
    public void getRecord(@NonNull String id, @NonNull Consumer<Status<RecordEntity>> callback) {
        recordApi.GetById(id).enqueue(new CallToConsumer<>(
                callback,
                record -> {
                    final String resultId = record.id;
                    final String placeName = record.placeName;
                    final String date = record.date;
                    final String startDate = record.startDate;
                    final String endDate = record.endDate;

                    if (resultId != null && placeName != null &&
                    date != null &&
                    startDate != null &&
                    endDate != null) {
                        return new RecordEntity(
                                resultId,
                                placeName,
                                date,
                                startDate,
                                endDate,
                                record.depth
                        );
                    } else {
                        return null;
                    }
        }
                ));
    }

    @Override
    public void createRecord(
            @NonNull String placeName,
            @NonNull String date,
            @NonNull String startDate,
            @NonNull String endDate,
            int depth,
            @NonNull Consumer<Status<Void>> callback) {
        recordApi.create(new RecordInitDto(placeName, date, startDate, endDate, depth)).enqueue(new CallToConsumer<>(
                callback,
                dto -> null
        ));
    }
}
