package com.scoutzknifez.weatherappv2.datafetcher;

import com.scoutzknifez.weatherappv2.Fragments.interfaces.Updatable;

import java.util.ArrayList;
import java.util.List;

public class DataConnector {
    public static int updateCount = 0;
    public static List<Updatable> updatables = new ArrayList<>();
}