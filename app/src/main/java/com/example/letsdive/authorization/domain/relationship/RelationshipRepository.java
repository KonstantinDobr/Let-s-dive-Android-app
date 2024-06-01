package com.example.letsdive.authorization.domain.relationship;

import androidx.annotation.NonNull;

import com.example.letsdive.authorization.domain.entities.Status;
import com.example.letsdive.authorization.domain.entities.UserRelationshipEntity;

import java.util.Set;
import java.util.function.Consumer;

public interface RelationshipRepository {
    void createRelationship(
            @NonNull String firstId,
            @NonNull String secondId,
            Consumer<Status<UserRelationshipEntity>> callback
    );

    void getRelationship(@NonNull String firstId, @NonNull Consumer<Status<Set<UserRelationshipEntity>>> callback);
}
