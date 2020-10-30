package com.ews.workdaytest.network;

import com.ews.workdaytest.model.TrendingModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface Methods {
    @Headers({"Content-Type: application/json"})
    @GET("trending")
    Call<TrendingModel> getImageList(@Query("api_key") String apiKey);
}