package com.example.letsdive.authorization.data.dto;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class UserRelationshipInitDto {
    @Nullable
    @SerializedName("firstId")
    public String firstId;
    @Nullable
    @SerializedName("secondId")
    public String secondId;

    public UserRelationshipInitDto(@Nullable String firstId, @Nullable String secondId) {
        this.firstId = firstId;
        this.secondId = secondId;
    }
}
