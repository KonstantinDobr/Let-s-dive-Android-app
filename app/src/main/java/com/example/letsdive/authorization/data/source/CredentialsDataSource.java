package com.example.letsdive.authorization.data.source;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import okhttp3.Credentials;

public class CredentialsDataSource {
    private static CredentialsDataSource INSTANCE;

    private CredentialsDataSource() {}

    public static synchronized CredentialsDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CredentialsDataSource();
        }
        return INSTANCE;
    }

    @Nullable
    private String authData = null;

    @Nullable
    public String getAuthData() {
        return authData;
    }

    public void updateLogin(@NonNull String username, @NonNull String password) {
        authData = Credentials.basic(username, password);
    }

    public void logout() {
        authData = null;
    }
}
