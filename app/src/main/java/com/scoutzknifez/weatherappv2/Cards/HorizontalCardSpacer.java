package com.scoutzknifez.weatherappv2.Cards;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HorizontalCardSpacer extends RecyclerView.ItemDecoration {
    private int spaceBetween;

    public HorizontalCardSpacer(int spaceBetween) {
        this.spaceBetween = spaceBetween;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.left = spaceBetween;
        outRect.right = spaceBetween;
    }
}