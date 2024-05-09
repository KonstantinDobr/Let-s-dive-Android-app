package com.example.letsdive.authorization.data.dto;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class UserDto {
    @Nullable
    @SerializedName("id")
    public String id;
    @Nullable
    @SerializedName("username")
    public String name;
    @Nullable
    @SerializedName("photoUrl")
    public String photoUrl;
    @Nullable
    @SerializedName("email")
    public String email;
}
