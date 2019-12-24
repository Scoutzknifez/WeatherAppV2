package com.scoutzknifez.weatherappv2.Cards;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.scoutzknifez.weatherappv2.DataFetcher.FetchedData;
import com.scoutzknifez.weatherappv2.DataStructures.DayWeather;
import com.scoutzknifez.weatherappv2.DataStructures.TimeAtMoment;
import com.scoutzknifez.weatherappv2.R;
import com.scoutzknifez.weatherappv2.Utility.AppUtils;
import com.scoutzknifez.weatherappv2.Utility.Utils;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherCardAdapter extends RecyclerView.Adapter<WeatherCardAdapter.CardViewHolder> {
    private List<DayWeather> weatherDayList = new ArrayList<>();
    private List<WeatherCard> weatherCardList = new ArrayList<>();
    private Context context;

    public WeatherCardAdapter(ArrayList<DayWeather> dayWeathers, Context context) {
        setContext(context);
        for(DayWeather dayWeather : dayWeathers)
            add(dayWeather);
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_weather_card, null);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        WeatherCard weatherCard = getItem(position);

        if(position > 0)
            holder.currentTemp.setVisibility(View.INVISIBLE);

        holder.container.getBackground().setColorFilter(AppUtils.getColorFromConditionHigh(weatherDayList.get(position), getContext()), PorterDuff.Mode.SRC_ATOP);

        Resources resources = getContext().getResources();
        int imageID = resources.getIdentifier(Utils.getRealIconName(weatherCard.getIcon()), "drawable", getContext().getPackageName());
        String date = new TimeAtMoment(weatherCard.getEpox() * 1000).getDateFormat();
        String current = "" + weatherCard.getCurrentTemp();
        String high = "" + weatherCard.getHighTemp();
        String low = "" + weatherCard.getLowTemp();
        String precip = weatherCard.getPrecipitationChance() + "%";
        String wind = weatherCard.getWindSpeed() + " MPH " + Utils.getCardinalDirection(weatherCard.getWindBearing());
        String humidity = weatherCard.getHumidity() + "%";

        holder.date.setText(date);
        holder.icon.setImageResource(imageID);
        holder.currentTemp.setText(current);
        holder.highTemp.setText(high);
        holder.lowTemp.setText(low);
        holder.precipitationChance.setText(precip);
        holder.wind.setText(wind);
        holder.humidity.setText(humidity);
    }

    @Override
    public int getItemCount() {
        return weatherCardList != null ? weatherCardList.size() : 0;
    }

    public WeatherCard getItem(int index) {
        return weatherCardList.get(index);
    }

    private void add(DayWeather dayWeather) {
        weatherDayList.add(dayWeather);
        WeatherCard weatherCard = new WeatherCard(dayWeather.getTime(), dayWeather.getIcon(), (int) dayWeather.getTemperature(), (int) dayWeather.getHighTemperature(), (int) dayWeather.getLowTemperature(), (int) (dayWeather.getPrecipitationProbability() * 100), (int) dayWeather.getWindSpeed(), (double) dayWeather.getWindBearing(), (int) (dayWeather.getHumidity() * 100));
        if(weatherCardList.size() == 0) {
            // Edit the first weatherCard which needs info from current weather
            weatherCard.setCurrentTemp((int) FetchedData.currentWeather.getTemperature());
        }
        weatherCardList.add(weatherCard);
    }

    static class CardViewHolder extends RecyclerView.ViewHolder{
        ConstraintLayout container;
        TextView date;
        ImageView icon;
        TextView currentTemp;
        TextView highTemp;
        TextView lowTemp;
        TextView precipitationChance;
        TextView wind;
        TextView humidity;

        private CardViewHolder(View view) {
            super(view);

            this.container = view.findViewById(R.id.cardParent);
            this.date = view.findViewById(R.id.dateLine);
            this.icon = view.findViewById(R.id.iconImage);
            this.currentTemp = view.findViewById(R.id.currentTempLine);
            this.highTemp = view.findViewById(R.id.highTempLine);
            this.lowTemp = view.findViewById(R.id.lowTempLine);
            this.precipitationChance = view.findViewById(R.id.precipitationChanceLine);
            this.wind = view.findViewById(R.id.windLine);
            this.humidity = view.findViewById(R.id.humidityLine);
        }
    }
}