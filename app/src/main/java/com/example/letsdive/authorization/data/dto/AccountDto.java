package com.example.letsdive.authorization.data.dto;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class AccountDto {
    @NonNull
    @SerializedName("username")
    public String name;
    @NonNull
    @SerializedName("password")
    public String password;

    public AccountDto(@NonNull String name, @NonNull String password) {
        this.name = name;
        this.password = password;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getPassword() {
        return password;
    }
}
