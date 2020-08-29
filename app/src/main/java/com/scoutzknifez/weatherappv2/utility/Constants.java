package com.scoutzknifez.weatherappv2.utility;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Constants {
    // Program wide variables
    public static final String TAG = "WEATHER_APP_V2";
    public static final String API_IP_ADDRESS = "http://10.0.2.2:3001"; // THE IP ADDRESS TO HOST MACHINE
    public static final Retrofit.Builder RETROFIT = new Retrofit.Builder()
                                                        .baseUrl(API_IP_ADDRESS)
                                                        .addConverterFactory(GsonConverterFactory.create());

    // Time
    public static final int MILLIS_IN_SECOND = 1000;
    public static final int MILLIS_IN_MINUTE = MILLIS_IN_SECOND * 60;
    public static final int MILLIS_IN_HOUR = MILLIS_IN_MINUTE * 60;
    public static final int MILLIS_IN_DAY = MILLIS_IN_HOUR * 24;
    public static final int EPOCH_DAY = MILLIS_IN_DAY / 1000;

    // Fragment tags
    public static final String WEATHER_FORECAST_TAG = "fragment_weather_forecast";
    public static final String WEATHER_SINGLE_DAY_TAG = "fragment_weather_single_day";
    public static final String LOCATION_WAITER_TAG = "fragment_location_waiter";
}