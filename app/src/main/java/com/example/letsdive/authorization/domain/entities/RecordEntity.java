package com.example.letsdive.authorization.domain.entities;

import androidx.annotation.NonNull;

public class RecordEntity {
    @NonNull
    public String id;
    @NonNull
    public String placeName;
    @NonNull
    public String date;
    @NonNull
    public String startDate;
    @NonNull
    public String endDate;
    @NonNull
    public int depth;

    public RecordEntity(
            @NonNull String id,
            @NonNull String placeName,
            @NonNull String date,
            @NonNull String startDate,
            @NonNull String endDate,
            @NonNull int depth
    ) {
        this.id = id;
        this.placeName = placeName;
        this.date = date;
        this.startDate = startDate;
        this.endDate = endDate;
        this.depth = depth;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getPlaceName() {
        return placeName;
    }

    @NonNull
    public String getDate() {
        return date;
    }

    @NonNull
    public String getStartDate() {
        return startDate;
    }

    @NonNull
    public String getEndDate() {
        return endDate;
    }

    @NonNull
    public int getDepth() {
        return depth;
    }
}
