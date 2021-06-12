package com.scoutzknifez.weatherappv2.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Getter;

@Getter
public class WeatherForecast extends Fragment implements Updatable {
    @BindView(R.id.background_container)
    ConstraintLayout parentContainer;

    @BindView(R.id.date_refreshed)
    TextView dateRefreshedText;
    @BindView(R.id.time_updated)
    TextView timeUpdatedText;

    // Today weather information
    @BindView(R.id.current_weather_icon)
    ImageView currentWeatherIcon;
    @BindView(R.id.current_temp)
    TextView currentTempText;
    @BindView(R.id.apparent_temp)
    TextView currentApparentText;
    @BindView(R.id.current_low_temp)
    TextView todayLowTempText;
    @BindView(R.id.current_high_temp)
    TextView todayHighTempText;
    @BindView(R.id.current_precipitation_chance)
    TextView todayPrecipitationChanceText;
    @BindView(R.id.current_humidity)
    TextView todayHumidityText;
    @BindView(R.id.current_wind)
    TextView todayWindText;
    @BindView(R.id.current_sunset_time)
    TextView todaySunsetTimeText;

    @BindView(R.id.card_listView)
    RecyclerView upcomingWeatherCards;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_weather_forecast, container, false);
        ButterKnife.bind(this, view);

        getUpcomingWeatherCards().setLayoutManager(
                new LinearLayoutManager(
                        this.getContext(), LinearLayoutManager.HORIZONTAL, false
                )
        );

        getUpcomingWeatherCards().addItemDecoration(new HorizontalCardSpacer(4));
        update();

        return view;
    }

    @Override
    public void update() {
        final CurrentWeather mostRecent = Globals.recentWeatherData.peek().getCurrentWeather();
        final DayWeather currentDay = Globals.recentWeatherData.peek().getDailyWeather().get(0);
        final TimeAtMoment timeAtMoment = new TimeAtMoment(Utils.getMillisFromEpoch(mostRecent.getTime()));

        getActivity().runOnUiThread(() -> {
            String date = timeAtMoment.toString().split(" at ")[0];
            String time = timeAtMoment.toString().split(" at ")[1];
            dateRefreshedText.setText("Today   (" + date + " " + timeAtMoment.getDayText() + ")");

            String hour = time.split(":")[0];
            String minute = time.split(":")[1];
            String ampm = time.split(" ")[1];
            String simpleTime = hour + ":" + minute + " " + ampm;
            timeUpdatedText.setText("Last updated at " + simpleTime);

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
            todayPrecipitationChanceText.setText("Precipitation Chance: " + ((int) (currentDay.getPrecipitationProbability() * 100)) + "%");
            todayHumidityText.setText("Humidity: " + ((int) (currentDay.getHumidity() * 100)) + "%");
            todayWindText.setText("Wind: " + currentDay.getWindSpeed() + " MPH " + Utils.getCardinalDirection(currentDay.getWindBearing()));
            todaySunsetTimeText.setText("Sunset at " + (new TimeAtMoment(Utils.getMillisFromEpoch(currentDay.getSunsetTime()))).getHourMinuteFormat());

            // RecyclerView for cards
            WeatherCardAdapter weatherCardAdapter = new WeatherCardAdapter(Globals.recentWeatherData.peek().getDailyWeather(), getContext());
            // Set the list view to use card array adapter data
            getUpcomingWeatherCards().setAdapter(weatherCardAdapter);
        });
    }
}