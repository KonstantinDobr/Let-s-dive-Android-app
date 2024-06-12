package com.example.letsdive.authorization.data;

import androidx.annotation.NonNull;

import com.example.letsdive.authorization.data.dto.UserRelationshipDto;
import com.example.letsdive.authorization.data.dto.UserRelationshipInitDto;
import com.example.letsdive.authorization.data.network.RetrofitFactory;
import com.example.letsdive.authorization.data.source.PlaceApi;
import com.example.letsdive.authorization.data.source.UserRelationshipApi;
import com.example.letsdive.authorization.data.utils.CallToConsumer;
import com.example.letsdive.authorization.domain.entities.Status;
import com.example.letsdive.authorization.domain.entities.UserRelationshipEntity;
import com.example.letsdive.authorization.domain.relationship.RelationshipRepository;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class RelationshipRepositoryImpl implements RelationshipRepository {

    private static RelationshipRepositoryImpl INSTANCE;

    private final UserRelationshipApi relationshipApi = RetrofitFactory.getInstance().getUserRelationshipApi();

    private RelationshipRepositoryImpl() {}

    public static synchronized RelationshipRepositoryImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RelationshipRepositoryImpl();
        }
        return INSTANCE;
    }


    @Override
    public void createRelationship(@NonNull String firstId, @NonNull String secondId, Consumer<Status<UserRelationshipEntity>> callback) {
        relationshipApi.createRelationship(
                new UserRelationshipInitDto(
                firstId,
                secondId
        )).enqueue(new CallToConsumer<>(
                callback,
                userRelationshipDto -> {
                    if (userRelationshipDto.id != null && userRelationshipDto.firstId != null) {
                        return new UserRelationshipEntity(
                                userRelationshipDto.id,
                                userRelationshipDto.firstId,
                                userRelationshipDto.secondId
                        );
                    } else {
                        return null;
                    }
                }
        ));
    }

    @Override
    public void getRelationship(@NonNull String firstId, @NonNull Consumer<Status<Set<UserRelationshipEntity>>> callback) {
        relationshipApi.getByFirstId(firstId).enqueue(new CallToConsumer<>(
                callback,
                userRelationshipsDto -> {
                    Set<UserRelationshipEntity> result = new HashSet<>();
                    if (userRelationshipsDto == null || userRelationshipsDto.isEmpty()) {
                        return null;
                    }
                    for (UserRelationshipDto relationship : userRelationshipsDto) {
                        if (relationship.id != null && relationship.firstId != null) {
                            result.add(new UserRelationshipEntity(
                                    relationship.id,
                                    relationship.firstId,
                                    relationship.secondId
                            ));
                        }
                    }
                    return result;
                }
        ));
    }
}
