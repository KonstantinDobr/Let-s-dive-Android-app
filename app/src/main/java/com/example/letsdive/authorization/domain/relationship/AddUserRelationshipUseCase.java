package com.example.letsdive.authorization.domain.relationship;

import androidx.annotation.NonNull;

import com.example.letsdive.authorization.domain.UserRepository;
import com.example.letsdive.authorization.domain.entities.FullUserEntity;
import com.example.letsdive.authorization.domain.entities.Status;
import com.example.letsdive.authorization.domain.entities.UserRelationshipEntity;

import java.util.function.Consumer;

public class AddUserRelationshipUseCase {
    private final RelationshipRepository repo;

    public AddUserRelationshipUseCase(RelationshipRepository repo) {
        this.repo = repo;
    }

    public void execute(
            @NonNull String firstId,
            @NonNull String secondId,
            Consumer<Status<UserRelationshipEntity>> callback
    ) {
        repo.createRelationship(firstId, secondId, callback);
    }
}
