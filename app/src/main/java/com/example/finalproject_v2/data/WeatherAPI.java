package com.example.finalproject_v2.data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherAPI {
    @GET("weather")
    Call<Response> getWeather(@Query("q") String cityName,
                             @Query("appid") String apiKey);
}

