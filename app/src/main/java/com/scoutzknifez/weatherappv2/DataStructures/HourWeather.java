package com.scoutzknifez.weatherappv2.DataStructures;

public class HourWeather extends WeatherParent {
    public HourWeather() {
        super();
    }

    public HourWeather(long time, String summary, String icon, double temperature, double precipitationProbability, double humidity, int windSpeed, int windBearing) {
        super(time, summary, icon, temperature, precipitationProbability, humidity, windSpeed, windBearing);
    }

    @Override
    public String toString() {
        return "Hour:" + super.toString();
    }
}