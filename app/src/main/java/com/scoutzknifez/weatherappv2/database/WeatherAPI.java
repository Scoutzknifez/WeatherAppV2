package com.scoutzknifez.weatherappv2.database;

import com.scoutzknifez.weatherappv2.structures.weather.WeatherDataPacket;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface WeatherAPI {
    // TODO Built API for this

    @POST
    @Headers("Accept: application/json")
    Call postWeatherUpdate(@Body WeatherDataPacket packet);
}
