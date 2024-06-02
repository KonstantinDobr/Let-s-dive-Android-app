package com.example.letsdive.authorization.data.source;

import com.example.letsdive.authorization.data.dto.PlaceDto;
import com.example.letsdive.authorization.data.dto.PlaceInitDto;
import com.example.letsdive.authorization.data.dto.UserRelationshipDto;
import com.example.letsdive.authorization.data.dto.UserRelationshipInitDto;

import java.util.Set;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserRelationshipApi {
    @GET("/server/v1/userRelationship/firstId/{firstId}")
    Call<Set<UserRelationshipDto>> getByFirstId(@Path("firstId") String firstId);
    @POST("server/v1/userRelationship")
    Call<UserRelationshipDto> createRelationship(@Body UserRelationshipInitDto dto);
}
