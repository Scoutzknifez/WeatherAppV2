package com.scoutzknifez.weatherappv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.WindowManager;

import com.scoutzknifez.weatherappv2.Fragments.WeatherForecast;
import com.scoutzknifez.weatherappv2.datafetcher.DataConnector;
import com.scoutzknifez.weatherappv2.structures.Refresher;
import com.scoutzknifez.weatherappv2.utility.Constants;
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

        transitionToFragment(Globals.mainForecastFragment, Constants.WEATHER_FORECAST_TAG);

        setContentView(R.layout.activity_main);
    }

    public void transitionToFragment(Fragment fragment, String tag) {
        Globals.fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Globals.fragmentTransaction.add(R.id.main_container, fragment, tag);
        Globals.fragmentTransaction.commit();
    }
}