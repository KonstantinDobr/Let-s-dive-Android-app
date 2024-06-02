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
    private RecordApi recordApi = RetrofitFactory.getInstance().getRecordApi();

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
            long depth,
            Consumer<Status<RecordEntity>> callback
    ) {
        recordApi.createRecord(new RecordInitDto(
                placeName,
                date,
                startDate,
                endDate,
                depth,
                null
        )).enqueue(new CallToConsumer<>(
                callback,
                recordDto -> {
                    final String resultId = recordDto.id;
                    final String resultPlaceName = recordDto.placeName;
                    final String resultDate = recordDto.date;
                    final String resultStartDate = recordDto.startDate;
                    final String resultEndDate = recordDto.endDate;
                    if (
                            resultId != null &&
                            resultPlaceName != null &&
                                    resultDate != null &&
                                    resultStartDate != null &&
                                    resultEndDate != null
                    ) {
                        return new RecordEntity(
                                resultId,
                                resultPlaceName,
                                resultDate,
                                resultStartDate,
                                resultEndDate,
                                recordDto.depth
                        );
                    } else {
                        return null;
                    }
                }
        ));
    }

    @Override
    public void getRecord(@NonNull String id, @NonNull Consumer<Status<RecordEntity>> callback) {

    }
}
