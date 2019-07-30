package com.scoutzknifez.weatherappv2.Cards;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.scoutzknifez.weatherappv2.DataFetcher.FetchedData;
import com.scoutzknifez.weatherappv2.DataStructures.DayWeather;
import com.scoutzknifez.weatherappv2.MainActivity;
import com.scoutzknifez.weatherappv2.R;
import com.scoutzknifez.weatherappv2.Utility.Utils;

import java.util.ArrayList;
import java.util.List;

public class RecyclerCardArrayAdapter extends RecyclerView.Adapter<RecyclerCardArrayAdapter.CardViewHolder> {
    private List<Card> cardList = new ArrayList<>();

    public RecyclerCardArrayAdapter(ArrayList<DayWeather> dayWeathers) {
        super();
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
        Card card = getItem(position);

        if(position > 0)
            holder.currentTemp.setVisibility(View.INVISIBLE);

        Resources resources = MainActivity.selfRef.getResources();
        int imageID = resources.getIdentifier(Utils.getRealIconName(card.getIcon()), "drawable", MainActivity.selfRef.getPackageName());
        String current = "" + card.getCurrentTemp();
        String high = "" + card.getHighTemp();
        String low = "" + card.getLowTemp();
        String precip = card.getPrecipitationChance() + "%";
        String wind = card.getWindSpeed() + " MPH " + Utils.getCardinalDirection(card.getWindBearing());
        String humidity = card.getHumidity() + "%";

        holder.date.setText(card.getDate());
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
        return cardList != null ? cardList.size() : 0;
    }

    public Card getItem(int index) {
        return cardList.get(index);
    }

    private void add(DayWeather dayWeather) {
        Card card = new Card("Time: " + dayWeather.getTime(), dayWeather.getIcon(), (int) dayWeather.getTemperature(), (int) dayWeather.getHighTemperature(), (int) dayWeather.getLowTemperature(), (int) (dayWeather.getPrecipitationProbability() * 100), (int) dayWeather.getWindSpeed(), (double) dayWeather.getWindBearing(), (int) (dayWeather.getHumidity() * 100));
        if(cardList.size() == 0) {
            // Edit the first card which needs info from current weather
            card.setCurrentTemp((int) FetchedData.currentWeather.getTemperature());
        }
        cardList.add(card);
    }

    static class CardViewHolder extends RecyclerView.ViewHolder{
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