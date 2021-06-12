package com.scoutzknifez.weatherappv2.structures;

import androidx.core.util.Supplier;

import com.scoutzknifez.weatherappv2.database.WeatherAPI;
import com.scoutzknifez.weatherappv2.datafetcher.DataConnector;
import com.scoutzknifez.weatherappv2.datafetcher.FetcherController;
import com.scoutzknifez.weatherappv2.fragments.interfaces.Updatable;
import com.scoutzknifez.weatherappv2.structures.dto.WeatherForTime;
import com.scoutzknifez.weatherappv2.structures.dto.APIResult;
import com.scoutzknifez.weatherappv2.structures.weather.WeatherDataPacket;
import com.scoutzknifez.weatherappv2.utility.Constants;
import com.scoutzknifez.weatherappv2.utility.Globals;
import com.scoutzknifez.weatherappv2.utility.Utils;

import lombok.Getter;
import lombok.Setter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
                    requeue();
                    return;
                }

                Globals.recentWeatherData.push(packet);
                WeatherForTime weatherForTime =
                        new WeatherForTime(
                                packet.getCurrentWeather().getTime(),
                                new Location(FetcherController.lat, FetcherController.lon),
                                packet.getCurrentWeather().getTemperature(),
                                packet.getCurrentWeather().getPrecipitationProbability(),
                                packet.getCurrentWeather().getHumidity(),
                                packet.getCurrentWeather().getWindSpeed(),
                                packet.getCurrentWeather().getWindBearing()
                        );

                WeatherAPI api = new Retrofit.Builder()
                                        .baseUrl(Constants.API_IP_ADDRESS)
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build().create(WeatherAPI.class);

                api.postWeatherUpdate(weatherForTime).enqueue(new Callback<APIResult>() {
                    @Override
                    public void onResponse(Call<APIResult> call, Response<APIResult> response) {
                        Utils.log("The api call to the server was a " + response.body().getResult());
                    }

                    @Override
                    public void onFailure(Call<APIResult> call, Throwable t) {
                        Utils.log("Failure to communicate properly with the API!");
                        t.printStackTrace();
                    }
                });

                try {
                    for (Updatable updatable : DataConnector.updatables)
                        updatable.update();
                } catch (Exception e) {
                    Utils.log("Could not update the screen because %s", e);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Utils.log("Could not wait for queue to refresh on %s", e);
        }

        requeue();
    }

    private void requeue() {
        try {
            Thread.sleep(Constants.MILLIS_IN_MINUTE * 15);
        } catch(Exception e) {
            e.printStackTrace();
            Utils.log("Could not sleep the main thread for the refresher.");
        }

        queueRefresh();
    }
}
