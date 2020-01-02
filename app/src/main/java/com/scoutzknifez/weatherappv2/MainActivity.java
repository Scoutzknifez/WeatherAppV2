package com.scoutzknifez.weatherappv2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;

import com.scoutzknifez.weatherappv2.Fragments.WeatherForecast;
import com.scoutzknifez.weatherappv2.utility.Globals;
import com.scoutzknifez.weatherappv2.utility.Utils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Change Android Properties for app
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

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

        Globals.fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Globals.fragmentTransaction.add(R.id.main_container, new WeatherForecast(), "fragment_weather_forecast");
        Globals.fragmentTransaction.commit();

        setContentView(R.layout.activity_main);
    }
}