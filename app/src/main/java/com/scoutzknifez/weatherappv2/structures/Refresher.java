package com.scoutzknifez.weatherappv2.structures;

import androidx.core.util.Supplier;

import com.scoutzknifez.weatherappv2.datafetcher.DataConnector;
import com.scoutzknifez.weatherappv2.datafetcher.FetcherController;
import com.scoutzknifez.weatherappv2.Fragments.interfaces.Updatable;
import com.scoutzknifez.weatherappv2.structures.weather.WeatherDataPacket;
import com.scoutzknifez.weatherappv2.utility.Constants;
import com.scoutzknifez.weatherappv2.utility.Globals;
import com.scoutzknifez.weatherappv2.utility.Utils;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Refresher {
    private Supplier<WeatherDataPacket> weatherRefresher = FetcherController::fetchWeather;

    public Refresher() {
        Thread thread = new Thread(this::queueRefresh);

        thread.start();
    }

    private void queueRefresh() {
        try {
            if (Utils.hasInternetConnection()) {
                Globals.recentWeatherData.push(weatherRefresher.get());

                try {
                    for (Updatable updatable : DataConnector.updatables)
                        updatable.update();
                } catch (Exception e) {
                    Utils.log("Could not update the screen because %s", e);
                }

                Thread.sleep(Constants.MILLIS_IN_MINUTE * 15);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Utils.log("Could not wait for queue to refresh on %s", e);
        }

        queueRefresh();
    }
}
