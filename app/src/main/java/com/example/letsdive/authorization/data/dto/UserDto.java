package com.example.letsdive.authorization.data.dto;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.Set;

public class UserDto {
    @Nullable
    @SerializedName("id")
    public String id;
    @Nullable
    @SerializedName("username")
    public String username;
    @Nullable
    @SerializedName("photoUrl")
    public String photoUrl;
    @Nullable
    @SerializedName("records")
    public Set<RecordDto> records;
    @Nullable
    @SerializedName("places")
    public Set<PlaceDto> places;
}
