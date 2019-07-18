package com.scoutzknifez.weatherappv2.Cards;

import android.content.Context;
import android.widget.ArrayAdapter;

public class CardArrayAdapter extends ArrayAdapter<Card> {
    public CardArrayAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }
}