package com.scoutzknifez.weatherappv2.Utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.scoutzknifez.weatherappv2.DataFetcher.FetcherController;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.content.ContentValues.TAG;

public class Utils {
    public static void initializeFetcher(Context context) {
        FetcherController.fetchWeather();
        if (true) return; // TODO Temp

        Globals.hasInternet = hasInternetConnection(context);
        if(Globals.hasInternet) {
            FetcherController.fetchWeather();
        } else {
            System.out.println("No internet connection!");
        }
    }

    public static String getRealIconName(String s)
    {
        StringBuilder sb = new StringBuilder(s);

        for(int i = 0; i < sb.length(); i++)
        {
            if(sb.charAt(i) == '-')
            {
                sb.deleteCharAt(i);
            }
        }

        String almostDone = sb.toString();
        if(almostDone.contains("night"))
        {
            almostDone = almostDone.replaceAll("night","day");
        }
        return almostDone;
    }

    public static String getCardinalDirection(double bearingInDegrees)
    {
        if (bearingInDegrees > 11.25 && bearingInDegrees < 33.75) {
            return "NNE";
        } else if (bearingInDegrees > 33.75 && bearingInDegrees < 56.25) {
            return "NE";
        } else if (bearingInDegrees > 56.25 && bearingInDegrees < 78.75) {
            return "ENE";
        } else if (bearingInDegrees > 78.75 && bearingInDegrees < 101.25) {
            return "E";
        } else if (bearingInDegrees > 101.25 && bearingInDegrees < 123.75) {
            return "ESE";
        } else if (bearingInDegrees > 123.75 && bearingInDegrees < 146.25) {
            return "SE";
        } else if (bearingInDegrees > 146.25 && bearingInDegrees < 168.75) {
            return "SSE";
        } else if (bearingInDegrees > 168.75 && bearingInDegrees < 191.25) {
            return "S";
        } else if (bearingInDegrees > 191.25 && bearingInDegrees < 213.75) {
            return "SSW";
        } else if (bearingInDegrees > 213.75 && bearingInDegrees < 236.25) {
            return "SW";
        } else if (bearingInDegrees > 236.25 && bearingInDegrees < 258.75) {
            return "WSW";
        } else if (bearingInDegrees > 258.75 && bearingInDegrees < 281.25) {
            return "W";
        } else if (bearingInDegrees > 281.25 && bearingInDegrees < 303.75) {
            return "WNW";
        } else if (bearingInDegrees > 303.75 && bearingInDegrees < 326.25) {
            return "NW";
        } else if (bearingInDegrees > 326.25 && bearingInDegrees < 348.75) {
            return "NNW";
        } else {
            return "N";
        }
    }

    public static double getRoundedInt(String string) {
        return getRoundedInt(getDouble(string));
    }

    public static double getDouble(String string) {
        try {
            return Double.parseDouble(string);
        } catch (Exception e) {
            Log.e(Constants.TAG, "Can not parse String into double.");
            return -1.0;
        }
    }

    public static int getInt(String string) {
        try {
            return Integer.parseInt(string);
        } catch (Exception e) {
            Log.e(Constants.TAG, "Can not parse String into int.");
            return -1;
        }
    }

    public static int getRoundedInt(double input) {
        return (int) Math.round(input);
    }

    public static long getMillisFromEpoch(long epoch) {
        return epoch * Constants.MILLIS_IN_SECOND;
    }

    public static boolean isEmptyJsonString(String string) {
        return (string == null || string.equalsIgnoreCase("") || string.equalsIgnoreCase("{}"));
    }

    private static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    public static boolean hasInternetConnection(Context context) {
        if (isNetworkAvailable(context)) {
            try {
                HttpURLConnection urlc = (HttpURLConnection) (new URL("http://clients3.google.com/generate_204").openConnection());
                urlc.setRequestProperty("User-Agent", "Android");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(1500);
                urlc.connect();
                return (urlc.getResponseCode() == 204 && urlc.getContentLength() == 0);
            } catch (IOException e) {
                Log.e(TAG, "Error checking internet connection", e);
            }
        } else {
            Log.d(TAG, "No network available!");
        }
        return false;
    }
}