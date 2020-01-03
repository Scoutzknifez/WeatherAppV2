package com.scoutzknifez.weatherappv2.cards.spacers;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class VerticalCardSpacer extends RecyclerView.ItemDecoration {
    private int spaceBetween;

    public VerticalCardSpacer(int spaceBetween) {
        this.spaceBetween = spaceBetween;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.top = spaceBetween;
        outRect.bottom = spaceBetween;
    }
}