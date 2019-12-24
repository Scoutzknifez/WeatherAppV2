package com.scoutzknifez.weatherappv2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.scoutzknifez.weatherappv2.Fragments.WeatherForecast;
import com.scoutzknifez.weatherappv2.Utility.Globals;
import com.scoutzknifez.weatherappv2.Utility.Utils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final MainActivity activity = this;

        // Check if the fetch was successful
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Utils.initializeFetcher(activity.getApplicationContext());
            }
        });
        thread.start();

        // find a way to hide the top and bottom bars on android OS default

        try {
            thread.join();
        } catch (Exception e) {
            System.out.println("Thread crashed");
        }

        Globals.fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Globals.fragmentTransaction.add(R.id.main_container, new WeatherForecast(), "fragment_weather_forecast");
        Globals.fragmentTransaction.commit();

        setContentView(R.layout.activity_main);
    }
}