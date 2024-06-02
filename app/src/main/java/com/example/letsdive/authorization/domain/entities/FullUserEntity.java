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
    @NonNull
    private final String email;
    @NonNull
    private final String information;
    @Nullable
    private final Set<RecordEntity> records;
    @Nullable
    private final Set<PlaceEntity> places;

    public FullUserEntity(
            @NonNull String id,
            @NonNull String username,
            @Nullable String photoUrl,
            @NonNull String email,
            @NonNull String information,
            @Nullable Set<RecordEntity> records,
            @Nullable Set<PlaceEntity> places
    ) {
        this.id = id;
        this.username = username;
        this.photoUrl = photoUrl;
        this.email = email;
        this.information = information;
        this.records = records;
        this.places = places;
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

    @NonNull
    public String getEmail() {
        return email;
    }

    @NonNull
    public String getInformation() {
        return information;
    }

    @Nullable
    public Set<RecordEntity> getRecords() {
        return records;
    }

    @Nullable
    public Set<PlaceEntity> getPlaces() {
        return places;
    }

    @Override
    public String toString() {
        return "FullUserEntity{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", email='" + email + '\'' +
                ", information='" + information + '\'' +
                ", records=" + records +
                ", places=" + places +
                '}';
    }
}
