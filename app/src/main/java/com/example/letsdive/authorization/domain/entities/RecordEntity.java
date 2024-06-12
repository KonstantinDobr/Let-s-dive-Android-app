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
    private final String endDate;;
    @NonNull
    private final String information;
    private final long depth;
    private boolean expanded = false;

    public RecordEntity(
            @NonNull String id,
            @NonNull String placeName,
            @NonNull String date,
            @NonNull String startDate,
            @NonNull String endDate,
            @NonNull String information,
            long depth
    ) {
        this.id = id;
        this.placeName = placeName;
        this.date = date;
        this.startDate = startDate;
        this.endDate = endDate;
        this.information = information;
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
    public String getInformation() {
        return information;
    }

    public long getDepth() {
        return depth;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
}
