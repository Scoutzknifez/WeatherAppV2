package com.scoutzknifez.weatherappv2.Cards;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class Card {
    private String date;
    private String icon;
    private int currentTemp;
    private int highTemp;
    private int lowTemp;
    private int precipitationChance;
    private int wind;
    private int humidity;

    @Override
    public String toString() {
        String returned = "";

        returned += "<-----[Card]----->\n";
        returned += "Date: " + getDate() + "\n";
        returned += "Icon: " + getIcon() + "\n";
        returned += "Current Temperature: " + getCurrentTemp() + "\n";
        returned += "High Temperature: " + getHighTemp() + "\n";
        returned += "Low Temperature: " + getLowTemp() + "\n";
        returned += "Percipitation Chance: " + getPrecipitationChance() + "\n";
        returned += "Wind: " + getWind() + "\n";
        returned += "Humidity: " + getHumidity() + "\n";
        returned += "<---------------->\n";

        return returned;
    }
}