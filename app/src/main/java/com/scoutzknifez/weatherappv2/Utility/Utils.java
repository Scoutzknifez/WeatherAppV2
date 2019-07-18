package com.scoutzknifez.weatherappv2.Utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.scoutzknifez.weatherappv2.DataFetcher.FetcherController;
import com.scoutzknifez.weatherappv2.MainActivity;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.content.ContentValues.TAG;

public class Utils {
    public static long getMillisFromEpoch(long epoch) {
        return epoch * Constants.MILLIS_IN_SECOND;
    }

    public static void initializeApplication() {
        Constants.hasInternet = hasInternetConnection();
        if(Constants.hasInternet) {
            FetcherController.fetchWeather();
        } else {
            System.out.println("No internet connection!");
        }
    }

    public static boolean isEmptyJsonString(String string) {
        return (string == null || string.equalsIgnoreCase("") || string.equalsIgnoreCase("{}"));
    }
    private static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
    public static boolean hasInternetConnection() {
        if (isNetworkAvailable(MainActivity.selfRef)) {
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