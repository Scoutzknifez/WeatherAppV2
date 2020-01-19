package com.scoutzknifez.weatherappv2.structures.weather;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CurrentWeather extends WeatherParent {
    private double apparentTemperature;

    @Override
    public String toString() {
        return "Current:" + super.toString() + ",apparentTemperature:" + getApparentTemperature() + "}";
    }
}