package com.scoutzknifez.weatherappv2.structures.weather;

import java.util.ArrayList;

import lombok.Data;

@Data
public class WeatherDataPacket {
    // Last fetched and parsed data
    private CurrentWeather currentWeather = null; // The current weather at the location
    private ArrayList<HourWeather> hourlyWeather = null; // Appears to be always length 169 aka index 0 - 168 inclusive | 0 is the hour you are in stopping at the top of hour, 1 is the coming up hour
    private ArrayList<DayWeather> dailyWeather = null; // Index 0 is today, index 7 is one week from today
}
