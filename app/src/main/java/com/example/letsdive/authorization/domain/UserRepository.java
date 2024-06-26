package com.example.letsdive.authorization.domain;

import androidx.annotation.NonNull;

import com.example.letsdive.authorization.data.dto.PlaceDto;
import com.example.letsdive.authorization.data.dto.RecordDto;
import com.example.letsdive.authorization.domain.entities.FullUserEntity;
import com.example.letsdive.authorization.domain.entities.ItemUserEntity;
import com.example.letsdive.authorization.domain.entities.PlaceEntity;
import com.example.letsdive.authorization.domain.entities.RecordEntity;
import com.example.letsdive.authorization.domain.entities.Status;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public interface UserRepository {
    void getAllUsers(@NonNull Consumer<Status<List<ItemUserEntity>>> callback);

    void getUser(@NonNull String id, @NonNull Consumer<Status<FullUserEntity>> callback);

    void addRecord(@NonNull String userId, @NonNull String recordId, @NonNull Consumer<Status<RecordEntity>> callback);

    void deleteRecord(@NonNull String userId, @NonNull String recordId, @NonNull Consumer<Status<FullUserEntity>> callback);

    void addPlace(@NonNull String userId, @NonNull String placeId, @NonNull Consumer<Status<FullUserEntity>> callback);

    void deletePlace(@NonNull String userId, @NonNull String placeId, @NonNull Consumer<Status<FullUserEntity>> callback);

    void getUserByUsername(@NonNull String username, @NonNull Consumer<Status<FullUserEntity>> callback);

    void update(
            @NonNull String id,
            @NonNull String email,
            @NonNull String information,
            @NonNull String photoUrl,
            @NonNull Consumer<Status<FullUserEntity>> callback
    );
}
