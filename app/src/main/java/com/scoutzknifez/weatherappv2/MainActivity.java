package com.scoutzknifez.weatherappv2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.scoutzknifez.weatherappv2.DataFetcher.FetchedData;
import com.scoutzknifez.weatherappv2.DataStructures.DayWeather;
import com.scoutzknifez.weatherappv2.Fragments.WeatherForecast;
import com.scoutzknifez.weatherappv2.Utility.Constants;
import com.scoutzknifez.weatherappv2.Utility.Utils;

public class MainActivity extends AppCompatActivity {
    public static MainActivity selfRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selfRef = this;

        // Check if the fetch was successful
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Utils.initializeFetcher();
            }
        });
        thread.start();

        // find a way to hide the top and bottom bars on android OS default

        try {
            thread.join();
        } catch (Exception e) {
            System.out.println("Thread crashed");
        }

        Constants.fragmentTransaction = MainActivity.selfRef.getSupportFragmentManager().beginTransaction();
        Constants.fragmentTransaction.add(R.id.main_container, new WeatherForecast(), "fragment_weather_forecast");
        Constants.fragmentTransaction.commit();

        setContentView(R.layout.activity_main);
    }
}