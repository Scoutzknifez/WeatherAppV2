package com.scoutzknifez.weatherappv2.utility;

import androidx.fragment.app.FragmentTransaction;

import com.scoutzknifez.weatherappv2.Fragments.WeatherForecast;
import com.scoutzknifez.weatherappv2.structures.Queue;
import com.scoutzknifez.weatherappv2.structures.Refresher;
import com.scoutzknifez.weatherappv2.structures.weather.WeatherDataPacket;

public class Globals {
    // Program wide variables
    public static Refresher refresher;

    // Android variables
    public static FragmentTransaction fragmentTransaction;

    // Custom Variables
    public static WeatherForecast mainForecastFragment;
    public static Queue<WeatherDataPacket> recentWeatherData = new Queue<>();
}
