package com.example.letsdive.authorization.data.dto;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class PlaceInitDto {
    @Nullable
    @SerializedName("placeName")
    public String placeName;
    @Nullable
    @SerializedName("information")
    public String information;
    @SerializedName("latitude")
    public double latitude;
    @SerializedName("longitude")
    public double longitude;

    @Nullable
    @SerializedName("recordId")
    public String recordId;

    @SerializedName("depth")
    public long depth;

    @Nullable
    @SerializedName("userId")
    public UserDto user;

    public PlaceInitDto(@Nullable String placeName, @Nullable String information, double latitude, double longitude, String recordId, long depth, @Nullable UserDto user) {
        this.placeName = placeName;
        this.information = information;
        this.latitude = latitude;
        this.longitude = longitude;
        this.recordId = recordId;
        this.user = user;
        this.depth = depth;
    }
}
