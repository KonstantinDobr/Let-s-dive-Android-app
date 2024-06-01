package com.example.letsdive.authorization.data.dto;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class UserRelationshipDto {
    @Nullable
    @SerializedName("id")
    public String id;
    @Nullable
    @SerializedName("firstId")
    public String firstId;
    @Nullable
    @SerializedName("secondId")
    public String secondId;
}
