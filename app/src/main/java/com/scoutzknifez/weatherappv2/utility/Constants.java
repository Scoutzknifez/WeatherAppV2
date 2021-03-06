package com.scoutzknifez.weatherappv2.utility;

public class Constants {
    // Program wide variables
    public static final String TAG = "WEATHER_APP_V2";
    public static final String API_IP_ADDRESS = "http://mikeinca.ddns.net:3210"; // THE IP ADDRESS TO HOST MACHINE

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
    public static final String INTERNET_WAITER_TAG = "fragment_internet_waiter";
}