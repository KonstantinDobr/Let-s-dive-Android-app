package com.example.letsdive.authorization.domain.entities;

import androidx.annotation.Nullable;

public class Status<T> {
    private final int statusCode;

    @Nullable
    private final T value;

    @Nullable
    private final Throwable errors;

    public Status(int statusCode, @Nullable T value, @Nullable Throwable errors) {
        this.statusCode = statusCode;
        this.value = value;
        this.errors = errors;
    }

    public int getStatusCode() {
        return statusCode;
    }

    @Nullable
    public T getValue() {
        return value;
    }

    @Nullable
    public Throwable getErrors() {
        return errors;
    }
}

