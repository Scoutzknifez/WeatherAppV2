package com.scoutzknifez.weatherappv2.utility;

import android.util.Log;

import com.scoutzknifez.weatherappv2.structures.TimeAtMoment;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

public class Utils {
    public static String getRealIconName(String s) {
        StringBuilder sb = new StringBuilder(s);

        for(int i = 0; i < sb.length(); i++) {
            if(sb.charAt(i) == '-')
                sb.deleteCharAt(i);
        }

        String almostDone = sb.toString();
        if(almostDone.contains("night"))
            almostDone = almostDone.replaceAll("night","day");

        return almostDone;
    }

    public static String getCardinalDirection(double bearingInDegrees) {
        if (bearingInDegrees >= 11.25 && bearingInDegrees < 33.75) {
            return "NNE";
        } else if (bearingInDegrees >= 33.75 && bearingInDegrees < 56.25) {
            return "NE";
        } else if (bearingInDegrees >= 56.25 && bearingInDegrees < 78.75) {
            return "ENE";
        } else if (bearingInDegrees >= 78.75 && bearingInDegrees < 101.25) {
            return "E";
        } else if (bearingInDegrees >= 101.25 && bearingInDegrees < 123.75) {
            return "ESE";
        } else if (bearingInDegrees >= 123.75 && bearingInDegrees < 146.25) {
            return "SE";
        } else if (bearingInDegrees >= 146.25 && bearingInDegrees < 168.75) {
            return "SSE";
        } else if (bearingInDegrees >= 168.75 && bearingInDegrees < 191.25) {
            return "S";
        } else if (bearingInDegrees >= 191.25 && bearingInDegrees < 213.75) {
            return "SSW";
        } else if (bearingInDegrees >= 213.75 && bearingInDegrees < 236.25) {
            return "SW";
        } else if (bearingInDegrees >= 236.25 && bearingInDegrees < 258.75) {
            return "WSW";
        } else if (bearingInDegrees >= 258.75 && bearingInDegrees < 281.25) {
            return "W";
        } else if (bearingInDegrees >= 281.25 && bearingInDegrees < 303.75) {
            return "WNW";
        } else if (bearingInDegrees >= 303.75 && bearingInDegrees < 326.25) {
            return "NW";
        } else if (bearingInDegrees >= 326.25 && bearingInDegrees < 348.75) {
            return "NNW";
        } else {
            return "N";
        }
    }

    public static double getRoundedInt(String string) {
        return getRoundedInt(getDouble(string));
    }

    public static double getDouble(String string) {
        try {
            return Double.parseDouble(string);
        } catch (Exception e) {
            Log.e(Constants.TAG, "Can not parse String into double.");
            return -1.0;
        }
    }

    public static int getInt(String string) {
        try {
            return Integer.parseInt(string);
        } catch (Exception e) {
            Log.e(Constants.TAG, "Can not parse String into int.");
            return -1;
        }
    }

    /**
     * Sends a message out to console with time stamp of log execution
     *
     * NOTE: use %s to replace part of string with object
     *
     * @param message   message to display with replaceable characters for objects
     * @param objects   Objects to replace inside of the message string
     */
    public static void log(String message, Object... objects) {
        TimeAtMoment timeAtMoment = new TimeAtMoment(System.currentTimeMillis());

        System.out.println("[" + timeAtMoment + "] " + String.format(message, objects));
    }

    public static void log(Object object) {
        log(object.toString(), (Object) null);
    }

    public static int getRoundedInt(double input) {
        return (int) Math.round(input);
    }

    public static long getMillisFromEpoch(long epoch) {
        return epoch * Constants.MILLIS_IN_SECOND;
    }

    public static boolean isEmptyJsonString(String string) {
        return (string == null || string.equalsIgnoreCase("") || string.equalsIgnoreCase("{}"));
    }

    public static boolean hasInternetConnection() {
        final CountDownLatch latch = new CountDownLatch(1);
        final boolean[] hasInternet = {false};

        new Thread() {
            @Override
            public void run() {
                try (Socket socket = new Socket()) {
                    InetSocketAddress address = new InetSocketAddress("www.google.com", 80);
                    socket.connect(address, 5000);
                    if (socket.isConnected())
                        hasInternet[0] = true;
                } catch (Exception e) {
                    Utils.log("No internet connection!");
                }
                latch.countDown();
            }
        }.start();

        try {
            latch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return hasInternet[0];
    }
}