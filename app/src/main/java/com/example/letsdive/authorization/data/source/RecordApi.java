package com.example.letsdive.authorization.data.source;

import com.example.letsdive.authorization.data.dto.AccountDto;
import com.example.letsdive.authorization.data.dto.RecordDto;
import com.example.letsdive.authorization.data.dto.RecordInitDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RecordApi {
    @GET("server/v1/record/{id}")
    Call<RecordDto> getById(@Path("id") String id);
    @POST("server/v1/record")
    Call<RecordDto> createRecord(@Body RecordInitDto dto);

    @DELETE("server/v1/record/{id}")
    Call<Void> deleteRecord(@Path("id") String id);

    @PUT("server/v1/record/{id}")
    Call<RecordDto> updateRecord(@Path("id") String id, @Body RecordInitDto dto);
}
