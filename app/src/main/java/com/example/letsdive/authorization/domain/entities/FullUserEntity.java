package com.example.letsdive.authorization.domain.entities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;

public class FullUserEntity implements Serializable {
    @NonNull
    private final String id;
    @NonNull
    private final String username;
    @Nullable
    private final String photoUrl;

    public FullUserEntity(
            @NonNull String id,
            @NonNull String username,
            @Nullable String photoUrl
    ) {
        this.id = id;
        this.username = username;
        this.photoUrl = photoUrl;
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
    public String getPhotoUrl() {
        return photoUrl;
    }

    @Override
    public String toString() {
        return "FullUserEntity{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                '}';
    }
}
