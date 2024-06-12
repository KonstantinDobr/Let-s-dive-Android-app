package com.example.letsdive.authorization.data;

import androidx.annotation.NonNull;

import com.example.letsdive.authorization.data.dto.RecordDto;
import com.example.letsdive.authorization.data.dto.RecordInitDto;
import com.example.letsdive.authorization.data.network.RetrofitFactory;
import com.example.letsdive.authorization.data.source.CredentialsDataSource;
import com.example.letsdive.authorization.data.source.RecordApi;
import com.example.letsdive.authorization.data.source.UserApi;
import com.example.letsdive.authorization.data.utils.CallToConsumer;
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
    public void createRecord(
            @NonNull String placeName,
            @NonNull String date,
            @NonNull String startDate,
            @NonNull String endDate,
            @NonNull String information,
            long depth,
            Consumer<Status<RecordEntity>> callback
    ) {
        recordApi.createRecord(new RecordInitDto(
                placeName,
                date,
                startDate,
                endDate,
                information,
                depth,
                null
        )).enqueue(new CallToConsumer<>(
                callback,
                this::recordDtoToRecordEntity
        ));
    }

    @Override
    public void getRecord(@NonNull String id, @NonNull Consumer<Status<RecordEntity>> callback) {
        recordApi.getById(id).enqueue(new CallToConsumer<>(
                callback,
                this::recordDtoToRecordEntity
        ));
    }

    @Override
    public void deleteRecord(@NonNull String id, @NonNull Consumer<Status<Void>> callback) {
        recordApi.deleteRecord(id).enqueue(new CallToConsumer<>(
                callback,
                unused -> null
        ));
    }

    @Override
    public void updateRecord(
            @NonNull String id,
            @NonNull String placeName,
            @NonNull String date,
            @NonNull String startDate,
            @NonNull String endDate,
            @NonNull String information,
            long depth,
            Consumer<Status<RecordEntity>> callback
    ) {
        recordApi.updateRecord(id, new RecordInitDto(
                placeName,
                date,
                startDate,
                endDate,
                information,
                depth,
                null
        )).enqueue(new CallToConsumer<>(
                callback,
                this::recordDtoToRecordEntity
        ));
    }

    private RecordEntity recordDtoToRecordEntity(RecordDto recordDto) {
        if (recordDto == null) return null;
        final String resultId = recordDto.id;
        final String resultPlaceName = recordDto.placeName;
        final String resultDate = recordDto.date;
        final String resultStartDate = recordDto.startDate;
        final String resultEndDate = recordDto.endDate;
        final String resultInformation = recordDto.information;
        if (
                resultId != null &&
                        resultPlaceName != null &&
                        resultDate != null &&
                        resultStartDate != null &&
                        resultEndDate != null &&
                        resultInformation != null
        ) {
            return new RecordEntity(
                    resultId,
                    resultPlaceName,
                    resultDate,
                    resultStartDate,
                    resultEndDate,
                    resultInformation,
                    recordDto.depth
            );
        } else {
            return null;
        }
    }
}
