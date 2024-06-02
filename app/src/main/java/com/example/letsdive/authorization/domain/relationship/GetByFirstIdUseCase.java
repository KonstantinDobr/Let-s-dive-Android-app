package com.example.letsdive.authorization.domain.relationship;

import androidx.annotation.NonNull;

import com.example.letsdive.authorization.domain.entities.Status;
import com.example.letsdive.authorization.domain.entities.UserRelationshipEntity;

import java.util.Set;
import java.util.function.Consumer;

public class GetByFirstIdUseCase {
    private final RelationshipRepository repo;

    public GetByFirstIdUseCase(RelationshipRepository repo) {
        this.repo = repo;
    }

    public void execute(@NonNull String firstId, @NonNull Consumer<Status<Set<UserRelationshipEntity>>> callback) {
        repo.getRelationship(firstId, callback);
    }
}
