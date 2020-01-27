package com.scoutzknifez.weatherappv2.datafetcher;

import android.location.Location;

import com.scoutzknifez.weatherappv2.fragments.interfaces.Updatable;

import java.util.ArrayList;
import java.util.List;

public class DataConnector {
    public static Location lastKnownLocation = null;
    public static int updateCount = 0;
    public static List<Updatable> updatables = new ArrayList<>();
}