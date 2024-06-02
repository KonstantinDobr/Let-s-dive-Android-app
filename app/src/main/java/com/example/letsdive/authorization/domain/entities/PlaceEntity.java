package com.example.letsdive.authorization.domain.entities;

import androidx.annotation.NonNull;

public class PlaceEntity {

    @NonNull
    private final String id;
    @NonNull
    private final String placeName;
    @NonNull
    private final String information;
    private final double latitude;
    private final double longitude;

    public PlaceEntity(
            @NonNull String id,
            @NonNull String placeName,
            @NonNull String information,
            double latitude,
            double longitude
    ) {
        this.id = id;
        this.placeName = placeName;
        this.information = information;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getPlaceName() {
        return placeName;
    }

    @NonNull
    public String getInformation() {
        return information;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
