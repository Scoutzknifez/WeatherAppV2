package com.scoutzknifez.weatherappv2.structures;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CurrentWeather extends WeatherParent {
    @Override
    public String toString() {
        return "Current:" + super.toString();
    }
}