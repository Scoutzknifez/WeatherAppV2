package com.scoutzknifez.weatherappv2.DataFetcher;

import com.scoutzknifez.weatherappv2.DataStructures.CurrentWeather;
import com.scoutzknifez.weatherappv2.DataStructures.DayWeather;
import com.scoutzknifez.weatherappv2.DataStructures.HourWeather;

import java.util.ArrayList;

public class FetchedData {
    // Last fetched and parsed data
    public static CurrentWeather currentWeather = null; // The current weather at the location
    public static ArrayList<HourWeather> hourWeathers = null; // Appears to be always length 169 aka index 0 - 168 inclusive | 0 is the hour you are in stopping at the top of hour, 1 is the coming up hour
    public static ArrayList<DayWeather> dayWeathers = null; // Index 0 is today, index 7 is one week from today
}