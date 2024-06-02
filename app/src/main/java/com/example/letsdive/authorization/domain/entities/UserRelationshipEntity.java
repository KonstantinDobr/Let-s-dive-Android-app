package com.example.letsdive.authorization.domain.entities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class UserRelationshipEntity {
    @NonNull
    private final String id;

    @NonNull
    private final String firstId;
    @Nullable
    private final String secondId;

    public UserRelationshipEntity(@NonNull String id, @NonNull String firstId, @Nullable String secondId) {
        this.id = id;
        this.firstId = firstId;
        this.secondId = secondId;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getFirstId() {
        return firstId;
    }

    @Nullable
    public String getSecondId() {
        return secondId;
    }
}
