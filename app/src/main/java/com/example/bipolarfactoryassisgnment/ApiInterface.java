package com.example.bipolarfactoryassisgnment;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    String URL = "http://api.unsplash.com/collections/";

    @GET("139386/photos/?client_id=ZhwgIjJjgQ42G8MJ2-gMsNW-wnAv-KjpMa_IGaUHbXE")
    Call<String> getPets(@Query("page")Integer page);

    @GET("1580860/photos/?client_id=ZhwgIjJjgQ42G8MJ2-gMsNW-wnAv-KjpMa_IGaUHbXE")
    Call<String> getNature(@Query("page")Integer page);
}
