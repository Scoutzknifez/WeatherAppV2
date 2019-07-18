package com.scoutzknifez.weatherappv2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.scoutzknifez.weatherappv2.Fragments.WeatherForecast;
import com.scoutzknifez.weatherappv2.Utility.Constants;

public class MainActivity extends AppCompatActivity {
    public static MainActivity selfRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if the fetch was successful

        getActionBar().setDisplayShowTitleEnabled(false);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        Constants.fragmentTransaction = MainActivity.selfRef.getSupportFragmentManager().beginTransaction();
        Constants.fragmentTransaction.add(R.id.main_container, new WeatherForecast(), "fragment_weather_forecast");
        Constants.fragmentTransaction.commit();

        selfRef = this;
        setContentView(R.layout.activity_main);




        /*Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Utils.initializeApplication();
            }
        });

        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
            System.out.println("Thread crashed");
        }*/
    }
}