package com.example.letsdive.authorization.domain.entities;

import androidx.annotation.NonNull;

public class ItemUserEntity {
    @NonNull
    private final String id;

    @NonNull
    private final String name;

    public ItemUserEntity(@NonNull String id, @NonNull String name) {
        this.id = id;
        this.name = name;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getName() {
        return name;
    }
}

