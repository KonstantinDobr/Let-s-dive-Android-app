package com.example.letsdive.authorization.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.letsdive.authorization.data.dto.AccountDto;
import com.example.letsdive.authorization.data.dto.Container;
import com.example.letsdive.authorization.data.dto.PlaceDto;
import com.example.letsdive.authorization.data.dto.RecordDto;
import com.example.letsdive.authorization.data.dto.UserDto;
import com.example.letsdive.authorization.data.network.RetrofitFactory;
import com.example.letsdive.authorization.data.source.CredentialsDataSource;
import com.example.letsdive.authorization.data.source.UserApi;
import com.example.letsdive.authorization.data.utils.CallToConsumer;
import com.example.letsdive.authorization.domain.UserRepository;
import com.example.letsdive.authorization.domain.entities.FullUserEntity;
import com.example.letsdive.authorization.domain.entities.ItemUserEntity;
import com.example.letsdive.authorization.domain.entities.PlaceEntity;
import com.example.letsdive.authorization.domain.entities.RecordEntity;
import com.example.letsdive.authorization.domain.entities.Status;
import com.example.letsdive.authorization.domain.sign.SignUserRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import retrofit2.Call;

public class UserRepositoryImpl implements UserRepository, SignUserRepository {
    private static UserRepositoryImpl INSTANCE;
    private UserApi userApi = RetrofitFactory.getInstance().getUserApi();
    private final CredentialsDataSource credentialsDataSource = CredentialsDataSource.getInstance();

    private UserRepositoryImpl() {}

    public static synchronized UserRepositoryImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserRepositoryImpl();
        }
        return INSTANCE;
    }

    @Override
    public void getAllUsers(@NonNull Consumer<Status<List<ItemUserEntity>>> callback) {
        userApi.getAll().enqueue(new CallToConsumer<>(
                callback,
                usersDto -> {
                    ArrayList<ItemUserEntity> result = new ArrayList<>(usersDto.size());
                    for (UserDto user : usersDto) {
                        final String id = user.id;
                        final String username = user.username;
                        if (id != null && username != null) {
                            result.add(new ItemUserEntity(id, username, user.email));
                        }
                    }
                    return result;
                }
        ));
    }

    @Override
    public void getUser(@NonNull String id, @NonNull Consumer<Status<FullUserEntity>> callback) {
        userApi.getById(id).enqueue(new CallToConsumer<>(
                callback,
                this::userDtoToFullUserEntity
        ));

    }

    @Override
    public void addRecord(@NonNull String userId, @NonNull String recordId, @NonNull Consumer<Status<RecordEntity>> callback) {
        userApi.addRecord(userId, recordId).enqueue(new CallToConsumer<>(
                callback,
                recordDto -> {
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
        ));
    }

    @Override
    public void deleteRecord(@NonNull String userId, @NonNull String recordId, @NonNull Consumer<Status<FullUserEntity>> callback) {
        userApi.deleteRecord(userId, recordId).enqueue(new CallToConsumer<>(
                callback,
                this::userDtoToFullUserEntity
        ));
    }

    @Override
    public void addPlace(@NonNull String userId, @NonNull String placeId, @NonNull Consumer<Status<FullUserEntity>> callback) {
        userApi.addPlace(userId, placeId).enqueue(new CallToConsumer<>(
                callback,
                this::userDtoToFullUserEntity
        ));
    }

    @Override
    public void deletePlace(@NonNull String userId, @NonNull String placeId, @NonNull Consumer<Status<FullUserEntity>> callback) {
        userApi.deletePlace(userId, placeId).enqueue(new CallToConsumer<>(
                callback,
                this::userDtoToFullUserEntity
        ));
    }

    @Override
    public void getUserByUsername(@NonNull String username, @NonNull Consumer<Status<FullUserEntity>> callback) {
        userApi.getByUsername(username).enqueue(new CallToConsumer<>(
                callback,
                this::userDtoToFullUserEntity
        ));
    }

    @Override
    public void update(@NonNull String id, @NonNull String email, @NonNull String information, @NonNull String photoUrl, @NonNull Consumer<Status<FullUserEntity>> callback) {
        userApi.update(id, new Container(email, information, photoUrl)).enqueue(new CallToConsumer<>(
                callback,
                this::userDtoToFullUserEntity
        ));
    }

    @Override
    public void isExistUser(@NonNull String username, Consumer<Status<Void>> callback) {
        userApi.isExist(username).enqueue(new CallToConsumer<>(
                callback,
                dto -> null
        ));
    }

    @Override
    public void createAccount(@NonNull String username, @NonNull String password, Consumer<Status<Void>> callback) {
        userApi.register(new AccountDto(username, password)).enqueue(new CallToConsumer<>(
                callback,
                dto -> null
        ));
    }

    @Override
    public void login(@NonNull String username, @NonNull String password, Consumer<Status<FullUserEntity>> callback) {
        credentialsDataSource.updateLogin(username, password);
        userApi = RetrofitFactory.getInstance().getUserApi();
        userApi.login().enqueue(new CallToConsumer<>(
                callback,
                this::userDtoToFullUserEntity
        ));
    }

    @Override
    public void logout() {
        credentialsDataSource.logout();
    }

    private FullUserEntity userDtoToFullUserEntity(UserDto user) {
        if (user == null) return null;
        final String resultId = user.id;
        final String username_login = user.username;
        final String email = user.email;
        final String information = user.information;

        Set<RecordEntity> resultRecords = new HashSet<>(user.records.size());
        for (RecordDto recordDto : user.records) {
            final String listRecordId = recordDto.id;
            final String placeName = recordDto.placeName;
            final String date = recordDto.date;
            final String startDate = recordDto.startDate;
            final String endDate = recordDto.endDate;
            final String RecordInformation = recordDto.information;
            if (
                    listRecordId != null &&
                            placeName != null &&
                            date != null &&
                            startDate != null &&
                            endDate != null &&
                            RecordInformation != null
            ) {
                resultRecords.add(new RecordEntity(
                        listRecordId,
                        placeName,
                        date,
                        startDate,
                        endDate,
                        RecordInformation,
                        recordDto.depth
                ));
            }
        }

        Set<PlaceEntity> resultPlaces = new HashSet<>(user.places.size());
        for (PlaceDto placeDto : user.places) {
            final String listPlaceId = placeDto.id;
            final String placePlaceName = placeDto.placeName;
            final String placeInformation = placeDto.information;
            final String recordInf = placeDto.recordId;

            if (
                    listPlaceId != null &&
                            placePlaceName != null &&
                            placeInformation != null&&
                            recordInf != null
            ) {
                resultPlaces.add(new PlaceEntity(
                        listPlaceId,
                        placePlaceName,
                        placeInformation,
                        placeDto.latitude,
                        placeDto.longitude,
                        recordInf,
                        placeDto.depth
                ));
            }
        }

        if (resultId != null && username_login != null) {
            return new FullUserEntity(
                    resultId,
                    username_login,
                    user.photoUrl,
                    email,
                    information,
                    resultRecords,
                    resultPlaces
            );
        } else {
            return null;
        }
    }
}
