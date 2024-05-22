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
    @SerializedName("depth")
    public int depth;

    public RecordInitDto(@Nullable String placeName, @Nullable String date, @Nullable String startDate, @Nullable String endDate, int depth) {
        this.placeName = placeName;
        this.date = date;
        this.startDate = startDate;
        this.endDate = endDate;
        this.depth = depth;
    }

    @Nullable
    public String getPlaceName() {
        return placeName;
    }

    @Nullable
    public String getDate() {
        return date;
    }

    @Nullable
    public String getStartDate() {
        return startDate;
    }

    @Nullable
    public String getEndDate() {
        return endDate;
    }

    public int getDepth() {
        return depth;
    }
}
