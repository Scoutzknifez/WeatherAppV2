package com.scoutzknifez.weatherappv2.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.scoutzknifez.weatherappv2.Cards.Card;
import com.scoutzknifez.weatherappv2.Cards.CardArrayAdapter;
import com.scoutzknifez.weatherappv2.DataFetcher.FetchedData;
import com.scoutzknifez.weatherappv2.DataStructures.DayWeather;
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
        ListView listView = created.findViewById(R.id.card_listView);
        CardArrayAdapter cardArrayAdapter = new CardArrayAdapter(MainActivity.selfRef.getApplicationContext(), R.layout.list_weather_card);

        // Feed the card adapter a list to display
        for(DayWeather dayWeather : FetchedData.dayWeathers) {
            Card card = new Card("In long: " + dayWeather.getTime(), dayWeather.getIcon(), (int) dayWeather.getTemperature(), (int) dayWeather.getHighTemperature(), (int) dayWeather.getLowTemperature(), 0, 0, (int) dayWeather.getHumidity());
            cardArrayAdapter.add(card);
        }

        // Set the list view to use card array adapter data
        listView.setAdapter(cardArrayAdapter);

        return created;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}