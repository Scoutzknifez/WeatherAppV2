package com.scoutzknifez.weatherappv2.structures.dto;

import com.scoutzknifez.weatherappv2.structures.Location;
import com.scoutzknifez.weatherappv2.utility.exceptions.ParseFailureException;

import java.sql.ResultSet;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class WeatherForTime {
    // PK (Time, Lat, Long)
    private long time;
    private Location location;

    // Data for weather
    private double temperature;
    private double precipitationProbability;
    private double humidity;
    private int windSpeed;
    private double windBearing;

    public static WeatherForTime createInstance(ResultSet set) {
        try {
            long time = set.getLong("time");
            Location location = new Location(
                    set.getDouble("latitude"), set.getDouble("longitude")
            );

            double temperature = set.getDouble("temperature");
            double precipitationProbability = set.getDouble("precipitationProbability");
            double humidity = set.getDouble("humidity");
            int windSpeed = set.getInt("windSpeed");
            double windBearing = set.getDouble("windBearing");

            return new WeatherForTime(time, location, temperature,
                    precipitationProbability, humidity, windSpeed, windBearing);
        } catch (Exception e) {
            throw new ParseFailureException(set, WeatherForTime.class);
        }
    }
}