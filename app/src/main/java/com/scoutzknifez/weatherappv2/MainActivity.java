package com.scoutzknifez.weatherappv2;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.WindowManager;

import com.scoutzknifez.weatherappv2.fragments.LocationWaiter;
import com.scoutzknifez.weatherappv2.fragments.WeatherForecast;
import com.scoutzknifez.weatherappv2.datafetcher.DataConnector;
import com.scoutzknifez.weatherappv2.location.LocationActivity;
import com.scoutzknifez.weatherappv2.structures.Refresher;
import com.scoutzknifez.weatherappv2.utility.Constants;
import com.scoutzknifez.weatherappv2.utility.Globals;
import com.scoutzknifez.weatherappv2.utility.Utils;

public class MainActivity extends LocationActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Change Android Properties for app
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Wait for location to be set TODO load screen here?
        while (DataConnector.lastKnownLocation == null) {}

        Globals.refresher = new Refresher();

        // Wait for data to be retrieved
        while (Globals.recentWeatherData.peek() == null) {}

        if (hasLocationPermissions()) {
            initializeMainWeatherDisplay();
            transitionToFragment(Globals.mainForecastFragment, Constants.WEATHER_FORECAST_TAG);
        } else {
            transitionToFragment(new LocationWaiter(), Constants.LOCATION_WAITER_TAG);
        }

        setContentView(R.layout.activity_main);
    }

    public void initializeMainWeatherDisplay() {
        Globals.mainForecastFragment = new WeatherForecast();
        DataConnector.updatables.add(Globals.mainForecastFragment);
    }

    public void transitionToFragment(Fragment fragment, String tag) {
        Globals.fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Globals.fragmentTransaction.add(R.id.main_container, fragment, tag);
        Globals.fragmentTransaction.commit();
    }
}