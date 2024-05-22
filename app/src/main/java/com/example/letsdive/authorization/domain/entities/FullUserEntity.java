package com.example.letsdive.authorization.domain.entities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.Set;

public class FullUserEntity implements Serializable {
    @NonNull
    private final String id;
    @NonNull
    private final String username;
    @Nullable
    private final String photoUrl;
    @Nullable
    private final Set<Long> records;

    public FullUserEntity(
            @NonNull String id,
            @NonNull String username,
            @Nullable String photoUrl,
            @Nullable Set<Long> records
    ) {
        this.id = id;
        this.username = username;
        this.photoUrl = photoUrl;
        this.records = records;
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
    @Nullable
    public Set<Long> getRecords() {
        return records;
    }

    @Override
    public String toString() {
        return "FullUserEntity{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", records=" + records +
                '}';
    }
}
