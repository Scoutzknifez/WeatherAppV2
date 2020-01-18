package com.scoutzknifez.weatherappv2.utility;

import android.content.Context;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.scoutzknifez.weatherappv2.structures.weather.CurrentWeather;
import com.scoutzknifez.weatherappv2.structures.weather.DayWeather;
import com.scoutzknifez.weatherappv2.MainActivity;
import com.scoutzknifez.weatherappv2.utility.enums.Colors;

public class AppUtils {
    public static MainActivity getMainActivity(Fragment fragment) {
        return (fragment.getActivity() instanceof MainActivity ? ((MainActivity) fragment.getActivity()) : null);
    }

    public static int getWeatherIcon(String icon, Context context) {
        return context.getResources().getIdentifier(Utils.getRealIconName(icon), "drawable", context.getPackageName());
    }

    public static int getColor(Colors color, Context context) {
        return ResourcesCompat.getColor(context.getResources(), color.getColorID(), null);
    }

    public static int getColorFromConditionHigh(DayWeather dayWeather, Context context) {
        if (dayWeather.getPrecipitationProbability() >= .75)
            return getColor(Colors.LIGHT_GRAY, context);
        else {
            if (dayWeather.getHighTemperature() >= 100)
                return getColor(Colors.RED, context);
            else if (dayWeather.getHighTemperature() >= 90)
                return getColor(Colors.LIGHT_RED, context);
            else if (dayWeather.getHighTemperature() >= 80)
                return getColor(Colors.ORANGE, context);
            else if (dayWeather.getHighTemperature() >= 72)
                return getColor(Colors.LIGHT_ORANGE, context);
            else if (dayWeather.getHighTemperature() >= 64)
                return getColor(Colors.YELLOW, context);
            else if (dayWeather.getHighTemperature() >= 56)
                return getColor(Colors.BLUE, context);
            else
                return getColor(Colors.LIGHT_BLUE, context);
        }
    }

    public static int getColorFromCurrent(CurrentWeather currentWeather, Context context) {
        if (currentWeather.getPrecipitationProbability() >= .75)
            return getColor(Colors.LIGHT_GRAY, context);
        else {
            if (currentWeather.getTemperature() >= 100)
                return getColor(Colors.RED, context);
            else if (currentWeather.getTemperature() >= 90)
                return getColor(Colors.LIGHT_RED, context);
            else if (currentWeather.getTemperature() >= 80)
                return getColor(Colors.ORANGE, context);
            else if (currentWeather.getTemperature() >= 72)
                return getColor(Colors.LIGHT_ORANGE, context);
            else if (currentWeather.getTemperature() >= 64)
                return getColor(Colors.YELLOW, context);
            else if (currentWeather.getTemperature() >= 56)
                return getColor(Colors.BLUE, context);
            else
                return getColor(Colors.LIGHT_BLUE, context);
        }
    }
}
