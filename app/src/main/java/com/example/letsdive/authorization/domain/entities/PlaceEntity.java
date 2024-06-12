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

    @NonNull
    private final String recordId;

    private final long depth;

    public PlaceEntity(
            @NonNull String id,
            @NonNull String placeName,
            @NonNull String information,
            double latitude,
            double longitude,
            @NonNull String recordId,
            long depth
    ) {
        this.id = id;
        this.placeName = placeName;
        this.information = information;
        this.latitude = latitude;
        this.longitude = longitude;
        this.recordId = recordId;
        this.depth = depth;
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

    @NonNull
    public String getRecordId() {
        return recordId;
    }

    public long getDepth() {
        return depth;
    }
}
