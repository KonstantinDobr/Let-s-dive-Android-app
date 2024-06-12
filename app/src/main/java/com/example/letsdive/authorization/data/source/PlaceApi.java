package com.example.letsdive.authorization.data.source;

import com.example.letsdive.authorization.data.dto.PlaceDto;
import com.example.letsdive.authorization.data.dto.PlaceInitDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PlaceApi {
    @POST("server/v1/place")
    Call<PlaceDto> createPlace(@Body PlaceInitDto dto);

    @PUT("server/v1/place/{id}")
    Call<Void> updatePlace(@Path("id") String id, @Body PlaceInitDto dto);

    @GET("/server/v1/place/placeName/{placeName}/{userId}")
    Call<PlaceDto> getByPlaceName(@Path("placeName") String placeName, @Path("userId") String userId);

    @DELETE("/server/v1/place/{id}")
    Call<Void> deleteById(@Path("id") String id);
}
