package com.example.letsdive.authorization.data.dto;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class PlaceDto {
    @Nullable
    @SerializedName("id")
    public String id;
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
    @SerializedName("userId")
    public UserDto user;
}
