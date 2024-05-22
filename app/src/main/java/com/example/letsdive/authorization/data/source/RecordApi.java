package com.example.letsdive.authorization.data.source;

import com.example.letsdive.authorization.data.dto.RecordDto;
import com.example.letsdive.authorization.data.dto.RecordInitDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RecordApi {
    @GET("server/v1/user/record/{id}")
    Call<RecordDto> GetById(@Path("id") String id);
    @POST("server/v1/user/record")
    Call<Void> create(@Body RecordInitDto dto);
}
