package com.example.letsdive.authorization.data.source;

import com.example.letsdive.authorization.data.dto.AccountDto;
import com.example.letsdive.authorization.data.dto.Container;
import com.example.letsdive.authorization.data.dto.RecordDto;
import com.example.letsdive.authorization.data.dto.UserDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserApi {
    @GET("server/v1/user")
    Call<List<UserDto>> getAll();
    @GET("server/v1/user/{id}")
    Call<UserDto> getById(@Path("id") String id);
    @GET("/server/v1/user/username/{username}")
    Call<Void> isExist(@Path("username") String username);
    @POST("server/v1/user/register")
    Call<Void> register(@Body AccountDto dto);
    @GET("server/v1/user/login")
    Call<UserDto> login();

    @PUT("server/v1/user/record/{userId}/{recordId}")
    Call<RecordDto> addRecord(@Path("userId") String userId, @Path("recordId") String recordId);

    @PUT("server/v1/user/record/delete/{userId}/{recordId}")
    Call<UserDto> deleteRecord(@Path("userId") String userId, @Path("recordId") String recordId);

    @PUT("server/v1/user/place/{userId}/{placeId}")
    Call<UserDto> addPlace(@Path("userId") String userId, @Path("placeId") String placeId);

    @PUT("server/v1/user/place/delete/{userId}/{placeId}")
    Call<UserDto> deletePlace(@Path("userId") String userId, @Path("placeId") String placeId);

    @GET("/server/v1/user/username/{username}")
    Call<UserDto> getByUsername(@Path("username") String username);

    @PUT("server/v1/user/update/{id}")
    Call<UserDto> update(@Path("id") String id, @Body Container container);
}
