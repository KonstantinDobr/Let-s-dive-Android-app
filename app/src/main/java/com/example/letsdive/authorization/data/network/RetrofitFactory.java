package com.example.letsdive.authorization.data.network;

import com.example.letsdive.authorization.data.source.CredentialsDataSource;
import com.example.letsdive.authorization.data.source.PlaceApi;
import com.example.letsdive.authorization.data.source.RecordApi;
import com.example.letsdive.authorization.data.source.UserApi;
import com.example.letsdive.authorization.data.source.UserRelationshipApi;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory {
    private static RetrofitFactory INSTANCE;

    private RetrofitFactory() {
    }

    public static synchronized RetrofitFactory getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RetrofitFactory();
        }
        return INSTANCE;
    }

    private final OkHttpClient.Builder client = new OkHttpClient.Builder()
            .addInterceptor(chain -> {
                        String authData = CredentialsDataSource.getInstance().getAuthData();
                        if (authData == null) {
                            return chain.proceed(chain.request());
                        } else {
                            Request request = chain.request()
                                    .newBuilder()
                                    .addHeader("Authorization", authData)
                                    .build();
                            return chain.proceed(request);
                        }

                    }
            );

    private final Retrofit retrofit = new Retrofit.Builder()
//            .baseUrl("http://192.168.0.103:8080/")
            .baseUrl("http://45.93.201.23:8080/")
            .client(client.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public UserApi getUserApi() {
        return retrofit.create(UserApi.class);
    }
    public RecordApi getRecordApi() {
        return retrofit.create(RecordApi.class);
    }

    public PlaceApi getPlaceApi() {
        return retrofit.create(PlaceApi.class);
    }
    public UserRelationshipApi getUserRelationshipApi() {
        return retrofit.create(UserRelationshipApi.class);
    }
}
