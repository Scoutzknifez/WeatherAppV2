package com.scoutzknifez.weatherappv2.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scoutzknifez.weatherappv2.cards.spacers.HorizontalCardSpacer;
import com.scoutzknifez.weatherappv2.cards.WeatherCardAdapter;
import com.scoutzknifez.weatherappv2.R;
import com.scoutzknifez.weatherappv2.datafetcher.DataConnector;
import com.scoutzknifez.weatherappv2.fragments.interfaces.Updatable;
import com.scoutzknifez.weatherappv2.structures.TimeAtMoment;
import com.scoutzknifez.weatherappv2.structures.weather.CurrentWeather;
import com.scoutzknifez.weatherappv2.structures.weather.DayWeather;
import com.scoutzknifez.weatherappv2.utility.AppUtils;
import com.scoutzknifez.weatherappv2.utility.Globals;
import com.scoutzknifez.weatherappv2.utility.Utils;

import lombok.Getter;

@Getter
public class WeatherForecast extends Fragment implements Updatable {
    private ConstraintLayout parentContainer;

    // TODO Temp
    private TextView refreshTextCount;
    private TextView dateRefreshedText;

    private ImageView currentWeatherIcon;
    private TextView currentTempText;
    private TextView currentApparentText;
    private TextView todayLowTempText;
    private TextView todayHighTempText;
    private TextView todayPrecipitationChanceText;
    private TextView todayHumidityText;
    private TextView todayWindText;
    private TextView todaySunsetTimeText;

    private RecyclerView upcomingWeatherCards;


    public WeatherForecast() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View created = inflater.inflate(R.layout.weather_forecast, container, false);

        // Link the xml list view to the code
        parentContainer = created.findViewById(R.id.background_container);

        // TODO Temp
        refreshTextCount = created.findViewById(R.id.refresh_count);
        dateRefreshedText = created.findViewById(R.id.date_refreshed);

        // Today weather information
        currentWeatherIcon = created.findViewById(R.id.current_weather_icon);
        currentTempText = created.findViewById(R.id.current_temp);
        currentApparentText = created.findViewById(R.id.apparent_temp);
        todayLowTempText = created.findViewById(R.id.current_low_temp);
        todayHighTempText = created.findViewById(R.id.current_high_temp);
        todayPrecipitationChanceText = created.findViewById(R.id.current_precipitation_chance);
        todayHumidityText = created.findViewById(R.id.current_humidity);
        todayWindText = created.findViewById(R.id.current_wind);
        todaySunsetTimeText = created.findViewById(R.id.current_sunset_time);

        // Recycler view for upcoming days
        upcomingWeatherCards = created.findViewById(R.id.card_listView);

        getUpcomingWeatherCards().setLayoutManager(
                new LinearLayoutManager(
                        this.getContext(), LinearLayoutManager.HORIZONTAL, false
                )
        );

        getUpcomingWeatherCards().addItemDecoration(new HorizontalCardSpacer(4));
        update();

        return created;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void update() {
        final CurrentWeather mostRecent = Globals.recentWeatherData.peek().getCurrentWeather();
        final DayWeather currentDay = Globals.recentWeatherData.peek().getDailyWeather().get(0);
        final TimeAtMoment timeAtMoment = new TimeAtMoment(Utils.getMillisFromEpoch(mostRecent.getTime()));

        getActivity().runOnUiThread(() -> {
            // TODO Temp for testing
            refreshTextCount.setText("Successful Update Count: " + DataConnector.updateCount);
            dateRefreshedText.setText(timeAtMoment.toString());

            // Background colors
            parentContainer.setBackgroundColor(AppUtils.getColorFromCurrent(mostRecent, getContext()));
            // Sets the background to a dimmer version of the original color
            parentContainer.getBackground().setAlpha(100);

            // Today weather information
            currentWeatherIcon.setImageResource(AppUtils.getWeatherIcon(mostRecent.getIcon(), getContext()));
            currentTempText.setText("Current Temperature: " + Utils.getRoundedInt(mostRecent.getTemperature()) + "°F");
            currentApparentText.setText("Feels like " + Utils.getRoundedInt(mostRecent.getApparentTemperature()) + "°F");
            todayHighTempText.setText("Expected High: " + Utils.getRoundedInt(currentDay.getHighTemperature()) + "°F");
            todayLowTempText.setText("Expected Low: " + Utils.getRoundedInt(currentDay.getLowTemperature()) + "°F");
            todayPrecipitationChanceText.setText("Precipitation Chance: " + ((int) currentDay.getPrecipitationProbability() * 100) + "%");
            todayHumidityText.setText("Humidity: " + ((int) currentDay.getHumidity() * 100) + "%");
            todayWindText.setText("Wind: " + currentDay.getWindSpeed() + " MPH " + Utils.getCardinalDirection(currentDay.getWindBearing()));
            todaySunsetTimeText.setText("Sunset at " + (new TimeAtMoment(Utils.getMillisFromEpoch(currentDay.getSunsetTime()))).getHourMinuteFormat());

            // RecyclerView for cards
            WeatherCardAdapter weatherCardAdapter = new WeatherCardAdapter(Globals.recentWeatherData.peek().getDailyWeather(), getContext());
            // Set the list view to use card array adapter data
            getUpcomingWeatherCards().setAdapter(weatherCardAdapter);
        });
    }
}