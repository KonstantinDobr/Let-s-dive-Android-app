package com.example.letsdive.authorization.domain.sign;

import androidx.annotation.NonNull;

import com.example.letsdive.authorization.domain.entities.FullUserEntity;
import com.example.letsdive.authorization.domain.entities.Status;

import java.util.function.Consumer;

public interface SignUserRepository {
    void isExistUser(@NonNull String username, Consumer<Status<Void>> callback);
    void createAccount(
            @NonNull String username,
            @NonNull String password,
            Consumer<Status<Void>> callback
    );
    void login(
            @NonNull String username,
            @NonNull String password,
            Consumer<Status<FullUserEntity>> callback
    );

    void logout();
}
