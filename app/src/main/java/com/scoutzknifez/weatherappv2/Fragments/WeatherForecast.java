package com.scoutzknifez.weatherappv2.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scoutzknifez.weatherappv2.Cards.RecyclerCardArrayAdapter;
import com.scoutzknifez.weatherappv2.DataFetcher.FetchedData;
import com.scoutzknifez.weatherappv2.MainActivity;
import com.scoutzknifez.weatherappv2.R;

public class WeatherForecast extends Fragment {
    public WeatherForecast() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View created = inflater.inflate(R.layout.weather_forecast, container, false);

        // Link the xml list view to the code
        RecyclerView recyclerView = created.findViewById(R.id.card_listView);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.selfRef, LinearLayoutManager.HORIZONTAL, false));
        RecyclerCardArrayAdapter recyclerCardArrayAdapter = new RecyclerCardArrayAdapter(FetchedData.dayWeathers);

        // Set the list view to use card array adapter data
        recyclerView.setAdapter(recyclerCardArrayAdapter);

        return created;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}