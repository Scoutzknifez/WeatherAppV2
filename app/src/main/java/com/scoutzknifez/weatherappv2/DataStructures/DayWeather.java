package com.scoutzknifez.weatherappv2.DataStructures;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class DayWeather extends WeatherParent {
    private HourWeather[] hourlyWeather = new HourWeather[24];

    public DayWeather(long time, String summary, String icon, double temperature, double precipitationProbability, double humidity, int windSpeed, int windBearing) {
        super(time, summary, icon, temperature, precipitationProbability, humidity, windSpeed, windBearing);
    }

    public double getHighTemperature() {
        double high = getTemperature();
        for(HourWeather h : getHourlyWeather()) {
            if(h != null && high < h.getTemperature())
                high = h.getTemperature();
        }
        return high;
    }

    public double getLowTemperature() {
        double low = getTemperature();
        for(HourWeather h : getHourlyWeather()) {
            if(h != null && low > h.getTemperature())
                low = h.getTemperature();
        }
        return low;
    }

    @Override
    public String toString() {
        return "Day:" + super.toString();
    }
}