package com.scoutzknifez.weatherappv2.Cards;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.scoutzknifez.weatherappv2.R;

import java.util.ArrayList;
import java.util.List;

public class CardArrayAdapter extends ArrayAdapter<Card> {
    private List<Card> cardList = new ArrayList<>();


    public CardArrayAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    @Override
    public void clear() {
        cardList.clear();
        notifyDataSetChanged();
        super.clear();
    }

    @Override
    public void add(Card card) {
        super.add(card);
        cardList.add(card);
    }

    @Override
    public int getCount() {
        return cardList.size();
    }
    @Override
    public Card getItem(int index) {
        return cardList.get(index);
    }

    @Override @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        CardViewHolder viewHolder;

        if(row == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.list_weather_card, parent, false);

            viewHolder = new CardViewHolder();
            viewHolder.date = row.findViewById(R.id.dateLine);
            //viewHolder.icon = row.findViewById(R.id.iconImage);
            viewHolder.currentTemp = row.findViewById(R.id.currentTempLine);
            viewHolder.highTemp = row.findViewById(R.id.highTempLine);
            viewHolder.lowTemp = row.findViewById(R.id.lowTempLine);
            viewHolder.precipitationChance = row.findViewById(R.id.precipitationChanceLine);
            viewHolder.wind = row.findViewById(R.id.windLine);
            viewHolder.humidity = row.findViewById(R.id.humidityLine);
        } else {
            viewHolder = (CardViewHolder) row.getTag();
        }
        Card card = getItem(position);

        // This allows it to draw but does not draw the right stuff
        // It crashes here because viewHolder == null sometimes.
        /*if(card == null || viewHolder == null)
            return row;*/

        String current = "" + card.getCurrentTemp();
        String high = "" + card.getHighTemp();
        String low = "" + card.getLowTemp();
        String precip = card.getPrecipitationChance() + "%";
        String wind = card.getWind() + " MPH";
        String humid = card.getHumidity() + "%";

        viewHolder.date.setText(card.getDate());
        //viewHolder.icon.setImage
        viewHolder.currentTemp.setText(current);
        viewHolder.highTemp.setText(high);
        viewHolder.lowTemp.setText(low);
        viewHolder.precipitationChance.setText(precip);
        viewHolder.wind.setText(wind);
        viewHolder.humidity.setText(humid);

        return row;
    }

    static class CardViewHolder {
        TextView date;
        ImageView icon;
        TextView currentTemp;
        TextView highTemp;
        TextView lowTemp;
        TextView precipitationChance;
        TextView wind;
        TextView humidity;
    }
}