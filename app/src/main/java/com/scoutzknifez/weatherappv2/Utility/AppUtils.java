package com.scoutzknifez.weatherappv2.Utility;

import android.content.Context;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.scoutzknifez.weatherappv2.DataStructures.DayWeather;
import com.scoutzknifez.weatherappv2.MainActivity;
import com.scoutzknifez.weatherappv2.Utility.enums.Colors;

public class AppUtils {
    public static MainActivity getMainActivity(Fragment fragment) {
        return (fragment.getActivity() instanceof MainActivity ? ((MainActivity) fragment.getActivity()) : null);
    }

    public static int getColor(Colors color, Context context) {
        return ResourcesCompat.getColor(context.getResources(), color.getColorID(), null);
    }

    public static int getColorFromConditionHigh(DayWeather dayWeather, Context context) {
        if (dayWeather.getPrecipitationProbability() >= .75)
            return getColor(Colors.DARK_BLUE, context);
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
}
