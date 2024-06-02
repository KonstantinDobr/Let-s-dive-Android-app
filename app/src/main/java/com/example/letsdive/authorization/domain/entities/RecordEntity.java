package com.example.letsdive.authorization.domain.entities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class RecordEntity {
    @NonNull
    private final String id;
    @NonNull
    private final String placeName;
    @NonNull
    private final String date;
    @NonNull
    private final String startDate;
    @NonNull
    private final String endDate;
    private final long depth;

    public RecordEntity(
            @NonNull String id,
            @NonNull String placeName,
            @NonNull String date,
            @NonNull String startDate,
            @NonNull String endDate,
            long depth
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

    public long getDepth() {
        return depth;
    }
}
