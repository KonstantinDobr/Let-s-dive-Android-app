package com.example.letsdive.authorization.domain.entities;

import androidx.annotation.NonNull;

public class ItemUserEntity {
    @NonNull
    private final String id;

    @NonNull
    private final String username;

    public ItemUserEntity(@NonNull String id, @NonNull String username) {
        this.id = id;
        this.username = username;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getUsername() {
        return username;
    }
}

