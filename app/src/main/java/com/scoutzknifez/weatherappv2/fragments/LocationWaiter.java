package com.scoutzknifez.weatherappv2.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.scoutzknifez.weatherappv2.R;
import com.scoutzknifez.weatherappv2.datafetcher.DataConnector;
import com.scoutzknifez.weatherappv2.utility.AppUtils;
import com.scoutzknifez.weatherappv2.utility.Constants;
import com.scoutzknifez.weatherappv2.utility.Globals;

public class LocationWaiter extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_location_waiter, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        new Thread(() -> {
            while (DataConnector.lastKnownLocation == null) {}

            getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
            AppUtils.getMainActivity(this).initializeMainWeatherDisplay();
            AppUtils.getMainActivity(this).transitionToFragment(
                    Globals.mainForecastFragment, Constants.WEATHER_FORECAST_TAG
            );
        }).start();
    }
}
