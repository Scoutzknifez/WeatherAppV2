package com.scoutzknifez.weatherappv2.Cards;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.scoutzknifez.weatherappv2.DataStructures.DayWeather;
import com.scoutzknifez.weatherappv2.R;

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

        String current = "" + card.getCurrentTemp();
        String high = "" + card.getHighTemp();
        String low = "" + card.getLowTemp();
        String precip = card.getPrecipitationChance() + "%";
        String wind = card.getWind() + " MPH";
        String humid = card.getHumidity() + "%";

        holder.date.setText(card.getDate());
        //hwHolder.icon.setImage
        holder.currentTemp.setText(current);
        holder.highTemp.setText(high);
        holder.lowTemp.setText(low);
        holder.precipitationChance.setText(precip);
        holder.wind.setText(wind);
        holder.humidity.setText(humid);
    }

    @Override
    public int getItemCount() {
        return cardList != null ? cardList.size() : 0;
    }

    public Card getItem(int index) {
        return cardList.get(index);
    }

    private void add(DayWeather dayWeather) {
        Card card = new Card("In long: " + dayWeather.getTime(), dayWeather.getIcon(), (int) dayWeather.getTemperature(), (int) dayWeather.getHighTemperature(), (int) dayWeather.getLowTemperature(), 0, 0, (int) dayWeather.getHumidity());
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
            //this.icon = view.findViewById(R.id.iconImage);
            this.currentTemp = view.findViewById(R.id.currentTempLine);
            this.highTemp = view.findViewById(R.id.highTempLine);
            this.lowTemp = view.findViewById(R.id.lowTempLine);
            this.precipitationChance = view.findViewById(R.id.precipitationChanceLine);
            this.wind = view.findViewById(R.id.windLine);
            this.humidity = view.findViewById(R.id.humidityLine);
        }
    }
}