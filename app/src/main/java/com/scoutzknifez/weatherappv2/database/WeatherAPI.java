package com.scoutzknifez.weatherappv2.database;

import com.scoutzknifez.weatherappv2.structures.dto.APIResult;
import com.scoutzknifez.weatherappv2.structures.dto.WeatherForTime;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PUT;

public interface WeatherAPI {
    @PUT("/api/submitweather")
    @Headers("Accept: application/json")
    // https://stackoverflow.com/questions/21398598/how-to-post-raw-whole-json-in-the-body-of-a-retrofit-request
    Call<APIResult> postWeatherUpdate(@Body WeatherForTime weatherForTime);

    // TODO
    @GET("/api/")
    @Headers("Accept: application/json")
    Call<APIResult> getWeatherDay();
}
