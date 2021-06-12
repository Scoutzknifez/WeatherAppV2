package com.scoutzknifez.weatherappv2;

import androidx.fragment.app.Fragment;

import android.location.LocationManager;
import android.os.Bundle;
import android.view.WindowManager;

import com.scoutzknifez.weatherappv2.datafetcher.FetcherController;
import com.scoutzknifez.weatherappv2.fragments.LocationWaiter;
import com.scoutzknifez.weatherappv2.fragments.WeatherForecast;
import com.scoutzknifez.weatherappv2.datafetcher.DataConnector;
import com.scoutzknifez.weatherappv2.location.LocationActivity;
import com.scoutzknifez.weatherappv2.structures.Refresher;
import com.scoutzknifez.weatherappv2.utility.Constants;
import com.scoutzknifez.weatherappv2.utility.Globals;
import com.scoutzknifez.weatherappv2.utility.Utils;

public class MainActivity extends LocationActivity {
    int resumeCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Change Android Properties for app
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (!Utils.hasInternetConnection()) {
            transitionToFragment(new LocationWaiter(R.string.internet_disabled), Constants.INTERNET_WAITER_TAG);
            setContentView(R.layout.activity_main);
            return;
        }

        if (FetcherController.useLocationServices && !getLocationManager().isProviderEnabled(LocationManager.NETWORK_PROVIDER) && !getLocationManager().isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            // If we don't have the location services turned on for the device, show that and return
            transitionToFragment(new LocationWaiter(R.string.locations_disabled), Constants.LOCATION_WAITER_TAG);
            setContentView(R.layout.activity_main);
            return;
        }

        startUpdatingWeather();

        if (FetcherController.useLocationServices) {
            if (hasLocationPermissions()) {
                initializeMainWeatherDisplay();
            } else {
                transitionToFragment(new LocationWaiter(), Constants.LOCATION_WAITER_TAG);
            }
        } else {
            initializeMainWeatherDisplay();
        }

        setContentView(R.layout.activity_main);
    }

    public void initializeMainWeatherDisplay() {
        Globals.mainForecastFragment = new WeatherForecast();
        DataConnector.updatables.add(Globals.mainForecastFragment);
        transitionToFragment(Globals.mainForecastFragment, Constants.WEATHER_FORECAST_TAG);
    }

    public void startUpdatingWeather() {
        if (Globals.refresher == null) {
            // Wait for location to be set
            if (FetcherController.useLocationServices)
                while (DataConnector.lastKnownLocation == null) {}

            Globals.refresher = new Refresher();

            // Wait for data to be retrieved
            while (Globals.recentWeatherData.peek() == null) {}
        }
    }

    public void transitionToFragment(Fragment fragment, String tag) {
        Globals.fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Globals.fragmentTransaction.add(R.id.main_container, fragment, tag);
        Globals.fragmentTransaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (resumeCount++ <= 0)
            return;

        if (!Utils.hasInternetConnection() || !FetcherController.useLocationServices)
            return;

        if (getSupportFragmentManager().findFragmentByTag(Constants.LOCATION_WAITER_TAG) != null && getSupportFragmentManager().findFragmentByTag(Constants.LOCATION_WAITER_TAG).isVisible()) {
            initializeLocationServices();
            startUpdatingWeather();
        }
        else if (getSupportFragmentManager().findFragmentByTag(Constants.WEATHER_FORECAST_TAG) != null && getSupportFragmentManager().findFragmentByTag(Constants.WEATHER_FORECAST_TAG).isVisible()) {
            if (!hasAnyLocationProviderEnabled()) {
                Globals.refresher.setContinueRefresh(false);
                Globals.refresher = null;

                transitionToFragment(new LocationWaiter(R.string.locations_disabled), Constants.LOCATION_WAITER_TAG);
            }
        }
    }
}