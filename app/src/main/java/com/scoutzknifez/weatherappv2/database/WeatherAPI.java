package com.scoutzknifez.weatherappv2.database;

import com.scoutzknifez.weatherappv2.structures.weather.WeatherDataPacket;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface WeatherAPI {
    // TODO Built API for this

    @POST("/api/subtest")
    @Headers("Accept: application/json")
    Call<String> postWeatherUpdate(@Body WeatherDataPacket packet);
    // https://stackoverflow.com/questions/21398598/how-to-post-raw-whole-json-in-the-body-of-a-retrofit-request
}
