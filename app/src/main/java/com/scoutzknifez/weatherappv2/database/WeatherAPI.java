package com.scoutzknifez.weatherappv2.database;

import com.scoutzknifez.weatherappv2.structures.dto.APIResult;
import com.scoutzknifez.weatherappv2.structures.weather.CurrentWeather;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.PUT;

public interface WeatherAPI {
    // TODO Built API for this

    @PUT("/api/subtest")
    @Headers("Accept: application/json")
    Call<APIResult> postWeatherUpdate(@Body CurrentWeather currentWeatherPacket);
    // https://stackoverflow.com/questions/21398598/how-to-post-raw-whole-json-in-the-body-of-a-retrofit-request
}
