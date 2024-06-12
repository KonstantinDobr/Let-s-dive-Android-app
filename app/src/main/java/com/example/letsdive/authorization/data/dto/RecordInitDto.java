package com.example.letsdive.authorization.data.dto;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class RecordInitDto {
    @Nullable
    @SerializedName("placeName")
    public String placeName;
    @Nullable
    @SerializedName("date")
    public String date;
    @Nullable
    @SerializedName("startDate")
    public String startDate;
    @Nullable
    @SerializedName("endDate")
    public String endDate;
    @Nullable
    @SerializedName("information")
    public String information;
    @SerializedName("depth")
    public long depth;

    @Nullable
    @SerializedName("userId")
    public UserDto user;

    public RecordInitDto(@Nullable String placeName, @Nullable String date, @Nullable String startDate, @Nullable String endDate, @Nullable String information, long depth, @Nullable UserDto user) {
        this.placeName = placeName;
        this.date = date;
        this.startDate = startDate;
        this.endDate = endDate;
        this.information = information;
        this.depth = depth;
        this.user = user;
    }
}
