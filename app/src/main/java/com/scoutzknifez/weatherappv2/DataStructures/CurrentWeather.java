package com.scoutzknifez.weatherappv2.DataStructures;

public class CurrentWeather extends WeatherParent {
    public CurrentWeather(long time, String summary, String icon, double temperature, double precipitationProbability, double humidity, int windSpeed, int windBearing) {
        super(time, summary, icon, temperature, precipitationProbability, humidity, windSpeed, windBearing);
    }

    @Override
    public String toString() {
        return "Current:" + super.toString();
    }
}