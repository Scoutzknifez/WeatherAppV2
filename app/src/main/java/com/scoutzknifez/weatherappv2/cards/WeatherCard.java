package com.scoutzknifez.weatherappv2.cards;

import com.scoutzknifez.weatherappv2.utility.Utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class WeatherCard {
    private long epox;
    private String icon;
    private int currentTemp;
    private int highTemp;
    private int lowTemp;
    private int precipitationChance;
    private int windSpeed;
    private double windBearing;
    private int humidity;

    @Override
    public String toString() {
        String returned = "";

        returned += "<-----[WeatherCard]----->\n";
        returned += "Epox: " + getEpox() + "\n";
        returned += "Icon: " + getIcon() + "\n";
        returned += "Current Temperature: " + getCurrentTemp() + "\n";
        returned += "High Temperature: " + getHighTemp() + "\n";
        returned += "Low Temperature: " + getLowTemp() + "\n";
        returned += "Percipitation Chance: " + getPrecipitationChance() + "\n";
        returned += "Wind: " + getWindSpeed() + " MPH " + Utils.getCardinalDirection(windBearing) + "\n";
        returned += "Humidity: " + getHumidity() + "\n";
        returned += "<---------------->\n";

        return returned;
    }
}