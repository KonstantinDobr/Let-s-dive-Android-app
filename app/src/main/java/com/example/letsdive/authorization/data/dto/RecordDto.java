package com.example.letsdive.authorization.data.dto;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class RecordDto {
    @Nullable
    @SerializedName("id")
    public String id;
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
}
