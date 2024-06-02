package com.example.letsdive.authorization.domain.entities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ItemUserEntity {
    @NonNull
    private final String id;

    @NonNull
    private final String username;
    @Nullable
    private final String email;

    public ItemUserEntity(@NonNull String id, @NonNull String username, @Nullable String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    @Nullable
    public String getEmail() {
        return email;
    }
}

