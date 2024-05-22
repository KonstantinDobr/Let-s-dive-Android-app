package com.example.letsdive.authorization.domain;

import androidx.annotation.NonNull;

import com.example.letsdive.authorization.domain.entities.FullUserEntity;
import com.example.letsdive.authorization.domain.entities.ItemUserEntity;
import com.example.letsdive.authorization.domain.entities.Status;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public interface UserRepository {
    void getAllUsers(@NonNull Consumer<Status<List<ItemUserEntity>>> callback);

    void getUser(@NonNull String id, @NonNull Consumer<Status<FullUserEntity>> callback);
    void updateUser(
            @NonNull String id,
            @NonNull String username,
            @NonNull String photoUrl,
            @NonNull Set<Long> records,
            @NonNull Consumer<Status<Void>> callback);
}
