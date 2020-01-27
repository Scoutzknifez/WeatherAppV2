package com.scoutzknifez.weatherappv2.location;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

// https://github.com/android/location-samples/blob/432d3b72b8c058f220416958b444274ddd186abd/LocationUpdatesForegroundService/app/src/main/java/com/google/android/gms/location/sample/locationupdatesforegroundservice/LocationUpdatesService.java

public class LocationService extends Service {
    private final IBinder mBinder = new LocalBinder();

    // private LocationRequest mLocationRequest;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        stopForeground(true);
        return null;
    }

    public class LocalBinder extends Binder {
        LocationService getService() {
            return LocationService.this;
        }
    }


}
