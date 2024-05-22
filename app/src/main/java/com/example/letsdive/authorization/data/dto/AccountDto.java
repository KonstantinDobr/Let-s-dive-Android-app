package com.example.letsdive.authorization.data.dto;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class AccountDto {
    @NonNull
    @SerializedName("username")
    public String username;
    @NonNull
    @SerializedName("password")
    public String password;

    public AccountDto(@NonNull String username, @NonNull String password) {
        this.username = username;
        this.password = password;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    @NonNull
    public String getPassword() {
        return password;
    }
}
