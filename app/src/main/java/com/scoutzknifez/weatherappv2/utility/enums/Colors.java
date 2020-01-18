package com.scoutzknifez.weatherappv2.utility.enums;

import com.scoutzknifez.weatherappv2.R;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Colors {
    // Reds
    DARK_RED(R.color.dark_red),
    RED(R.color.red),
    LIGHT_RED(R.color.light_red),

    // Oranges
    DARK_ORANGE(R.color.dark_orange),
    ORANGE(R.color.orange),
    LIGHT_ORANGE(R.color.light_orange),

    // Yellows
    GOLD(R.color.gold),
    DARK_YELLOW(R.color.dark_yellow),
    YELLOW(R.color.yellow),

    // Greens
    LIGHT_GREEN(R.color.light_green),
    GREEN(R.color.green),
    DARK_GREEN(R.color.dark_green),

    // Blues
    LIGHT_BLUE(R.color.light_blue),
    BLUE(R.color.blue),
    DARK_BLUE(R.color.dark_blue),

    // Purples
    PINK(R.color.pink),
    PURPLE(R.color.purple),
    DARK_PURPLE(R.color.dark_purple),

    // Gray scale
    BLACK(R.color.black),
    DARK_GRAY(R.color.dark_gray),
    GRAY(R.color.gray),
    LIGHT_GRAY(R.color.light_gray),
    WHITE(R.color.white);

    private int colorID;
}