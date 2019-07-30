package com.scoutzknifez.weatherappv2.DataStructures;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public abstract class WeatherParent {
    private long time;
    private String summary;
    private String icon;
    private double temperature;
    private double precipitationProbability;
    private double humidity;
    private int windSpeed;
    private int windBearing;

    @Override
    public String toString() {
        return "{time:" + getTime() + ",summary:" + getSummary() +
                ",icon:" + getIcon() + ",temperature:" + getTemperature() +
                ",precipitationProbability:" + getPrecipitationProbability() +
                ",humidity:" + getHumidity() + ",windSpeed:" + getWindSpeed() +
                ",windBearing:" + getWindBearing() + "}";
    }
}