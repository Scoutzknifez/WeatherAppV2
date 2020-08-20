package com.scoutzknifez.weatherappv2.structures;

import androidx.core.util.Supplier;

import com.scoutzknifez.weatherappv2.database.sql.SQLHelper;
import com.scoutzknifez.weatherappv2.database.sql.Table;
import com.scoutzknifez.weatherappv2.datafetcher.DataConnector;
import com.scoutzknifez.weatherappv2.datafetcher.FetcherController;
import com.scoutzknifez.weatherappv2.fragments.interfaces.Updatable;
import com.scoutzknifez.weatherappv2.structures.dto.WeatherForTime;
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
    private boolean continueRefresh = true;

    public Refresher() {
        Thread thread = new Thread(this::queueRefresh);

        thread.start();
    }

    private void queueRefresh() {
        try {
            if (Utils.hasInternetConnection()) {
                WeatherDataPacket packet = weatherRefresher.get();
                if (packet == null) {
                    Utils.log("Packet received from Weather API was empty!");
                    return;
                }

                Globals.recentWeatherData.push(packet);
                WeatherForTime weatherForTime =
                        new WeatherForTime(packet.getCurrentWeather().getTime(),
                                new Location(FetcherController.lat, FetcherController.lon),
                                packet.getCurrentWeather().getTemperature(),
                                packet.getCurrentWeather().getPrecipitationProbability(),
                                packet.getCurrentWeather().getHumidity(),
                                packet.getCurrentWeather().getWindSpeed(),
                                packet.getCurrentWeather().getWindBearing());
                SQLHelper.insertIntoTable(Table.WEATHER_FOR_TIME, weatherForTime);

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
