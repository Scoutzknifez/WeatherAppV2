package com.scoutzknifez.weatherappv2.datafetcher;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scoutzknifez.weatherappv2.structures.weather.CurrentWeather;
import com.scoutzknifez.weatherappv2.structures.weather.DayWeather;
import com.scoutzknifez.weatherappv2.structures.weather.HourWeather;
import com.scoutzknifez.weatherappv2.structures.TimeAtMoment;
import com.scoutzknifez.weatherappv2.structures.weather.WeatherDataPacket;
import com.scoutzknifez.weatherappv2.structures.weather.WeatherParent;
import com.scoutzknifez.weatherappv2.utility.HiddenConstants;
import com.scoutzknifez.weatherappv2.utility.Utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;

public class FetcherController {
    // Temecula, CA
    // private static String lat = "33.513833";
    // private static String lon = "-117.103338";

    // Pahrump, NV
    private static String lat = "36.3087267";
    private static String lon = "-116.0230096";

    private static String url = HiddenConstants.WEB_SERVER + HiddenConstants.API_KEY + "/" + lat + "," + lon + HiddenConstants.ADDITIONAL_ARGS;

    public static WeatherDataPacket fetchWeather() {
        try {
            URL darkSkyURL = new URL(url);
            URLConnection connection = darkSkyURL.openConnection();
            DataConnector.updateCount++;
            BufferedReader fetched = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String receivedData = fetched.readLine();

            if(Utils.isEmptyJsonString(receivedData))
                return null;

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(receivedData);

            WeatherDataPacket weatherPacket = new WeatherDataPacket();

            // Setting up all the fetched data into objects
            weatherPacket.setCurrentWeather(doCurrent(root));
            weatherPacket.setHourlyWeather(doHourly(root));
            weatherPacket.setDailyWeather(doDaily(root));

            delegateHours(weatherPacket);

            return weatherPacket;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static CurrentWeather doCurrent(JsonNode root) {
        JsonNode currentNode = root.path("currently");
        return getCurrentWeather(currentNode);
    }

    private static CurrentWeather getCurrentWeather(JsonNode currentNode) {
        return getWeatherByNode(currentNode, CurrentWeather.class);
    }

    private static ArrayList<HourWeather> doHourly(JsonNode root) {
        JsonNode hourlyNode = root.path("hourly");
        JsonNode hourlyDataNode = hourlyNode.path("data");
        return getHourlyWeather(hourlyDataNode);
    }

    @SuppressWarnings("unchecked")
    private static ArrayList<HourWeather> getHourlyWeather(JsonNode hourlyNode) {
        return (ArrayList<HourWeather>) makeWeatherForNode(hourlyNode, HourWeather.class);
    }

    private static ArrayList<DayWeather> doDaily(JsonNode root) {
        JsonNode dailyNode = root.path("daily");
        JsonNode dailyDataNode = dailyNode.path("data");
        return getDailyWeather(dailyDataNode);
    }

    @SuppressWarnings("unchecked")
    private static ArrayList<DayWeather> getDailyWeather(JsonNode dailyNode) {
        return (ArrayList<DayWeather>) makeWeatherForNode(dailyNode, DayWeather.class);
    }

    private static void delegateHours(WeatherDataPacket weatherDataPacket) {
        int dayIndex = 0;
        int hourIndex = 0;

        // Check to ensure that the indexes are contained in the lists of fetched data.
        while((dayIndex < weatherDataPacket.getDailyWeather().size()) && (hourIndex < weatherDataPacket.getHourlyWeather().size())) {
            TimeAtMoment hourTime = new TimeAtMoment(Utils.getMillisFromEpoch(weatherDataPacket.getHourlyWeather().get(hourIndex).getTime()));
            TimeAtMoment dayStartTime = new TimeAtMoment(Utils.getMillisFromEpoch(weatherDataPacket.getDailyWeather().get(dayIndex).getTime()));

            // First index of hour will ALWAYS be the same day you are in at index 0
            if(!hourTime.isSameDay(dayStartTime))
                dayIndex++;

            int indexOfHour = hourTime.getHour();
            weatherDataPacket.getDailyWeather().get(dayIndex).getHourlyWeather()[indexOfHour] = weatherDataPacket.getHourlyWeather().get(hourIndex);

            hourIndex++;
        }
    }

    private static ArrayList<? extends WeatherParent> makeWeatherForNode(JsonNode weatherTypeNode, Class<? extends WeatherParent> clazz) {
        Iterator<JsonNode> nodes = weatherTypeNode.elements();
        ArrayList<WeatherParent> weatherParents = new ArrayList<>();

        while(nodes.hasNext()) {
            JsonNode node = nodes.next();
            weatherParents.add(getWeatherByNode(node, clazz));
        }

        return weatherParents;
    }

    @SuppressWarnings("unchecked")
    private static <T extends WeatherParent> T getWeatherByNode(JsonNode jsonNode, Class<? extends WeatherParent> clazz) {
        try {
            WeatherParent weatherType = clazz.newInstance();

            weatherType.setTime(jsonNode.path("time").asLong());
            weatherType.setSummary(jsonNode.path("summary").asText());
            weatherType.setIcon(jsonNode.path("icon").asText());
            weatherType.setTemperature(jsonNode.path(
                    weatherType instanceof DayWeather ? "temperatureHigh" : "temperature").asDouble());
            weatherType.setPrecipitationProbability(jsonNode.path("precipProbability").asDouble());
            weatherType.setHumidity(jsonNode.path("humidity").asDouble());
            weatherType.setWindSpeed(jsonNode.path("windSpeed").asInt());
            weatherType.setWindBearing(jsonNode.path("windBearing").asInt());

            if (weatherType instanceof DayWeather)
                ((DayWeather) weatherType).setSunsetTime(jsonNode.path("sunsetTime").asLong());

            if (weatherType instanceof CurrentWeather)
                ((CurrentWeather) weatherType).setApparentTemperature(jsonNode.path("apparentTemperature").asDouble());

            return (T) weatherType;
        } catch (Exception e) {
            Utils.log("Could not parse Weather into type %s", clazz.getName());
            return null;
        }
    }
}