package com.scoutzknifez.weatherappv2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;

import com.scoutzknifez.weatherappv2.Fragments.WeatherForecast;
import com.scoutzknifez.weatherappv2.datafetcher.DataConnector;
import com.scoutzknifez.weatherappv2.structures.Refresher;
import com.scoutzknifez.weatherappv2.utility.Globals;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Change Android Properties for app
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Globals.refresher = new Refresher();
        while (Globals.recentWeatherData.peek() == null) {}

        Globals.mainForecastFragment = new WeatherForecast();
        DataConnector.updatables.add(Globals.mainForecastFragment);

        Globals.fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Globals.fragmentTransaction.add(R.id.main_container, Globals.mainForecastFragment, "fragment_weather_forecast");
        Globals.fragmentTransaction.commit();

        setContentView(R.layout.activity_main);
    }
}