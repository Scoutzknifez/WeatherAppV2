package com.scoutzknifez.weatherappv2.Utility.enums;

import com.scoutzknifez.weatherappv2.R;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Colors {
    RED(R.color.red),
    LIGHT_RED(R.color.light_red),
    ORANGE(R.color.orange),
    LIGHT_ORANGE(R.color.light_orange),
    YELLOW(R.color.yellow),
    // TODO Greens
    LIGHT_BLUE(R.color.light_blue),
    BLUE(R.color.blue),
    DARK_BLUE(R.color.dark_blue),
    // TODO Purples???

    BLACK(R.color.black),
    LIGHT_GRAY(R.color.light_gray),
    GRAY(R.color.gray),
    WHITE(R.color.white);

    int colorID;
}