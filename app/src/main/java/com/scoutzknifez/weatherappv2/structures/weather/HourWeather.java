package com.scoutzknifez.weatherappv2.structures.weather;

import androidx.annotation.NonNull;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class HourWeather extends WeatherParent {
    @NonNull
    @Override
    public String toString() {
        return "Hour:" + super.toString();
    }
}