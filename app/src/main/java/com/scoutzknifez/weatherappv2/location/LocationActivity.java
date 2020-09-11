package com.scoutzknifez.weatherappv2.location;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.scoutzknifez.weatherappv2.datafetcher.DataConnector;
import com.scoutzknifez.weatherappv2.utility.Constants;
import com.scoutzknifez.weatherappv2.utility.Utils;

import java.util.List;

import lombok.Getter;


// https://github.com/android/location-samples/blob/432d3b72b8c058f220416958b444274ddd186abd/LocationUpdatesForegroundService/app/src/main/java/com/google/android/gms/location/sample/locationupdatesforegroundservice/MainActivity.java
// Dependency for Google
// https://github.com/android/location-samples/blob/432d3b72b8c058f220416958b444274ddd186abd/LocationUpdatesForegroundService/app/build.gradle

@Getter
public class LocationActivity extends AppCompatActivity {
    private static final int REQUEST_COARSE_LOCATION_CODE = 7373;
    private static final int REQUEST_FINE_LOCATION_CODE = 7474;
    public boolean isRequestingPermissions = false;
    public boolean isRequestingNetworkLocation = false;
    public boolean isRequestingGPSLocation = false;

    private LocationManager locationManager = null;

    private LocationListener listener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            DataConnector.lastKnownLocation = location;
            Utils.log("Location updated (Lat: %s, Long: %s)", location.getLatitude(), location.getLongitude());
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    // https://developer.android.com/guide/topics/location/strategies.html
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);

        if (hasLocationPermissions())
            initializeLocationServices();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        isRequestingPermissions = false;

        if (hasLocationPermissions())
            initializeLocationServices();
    }

    private Location getLastKnownLocation() throws SecurityException {
        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location l = locationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }

    public void initializeLocationServices() {
        try {
            if (!isRequestingNetworkLocation() && locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, Constants.MILLIS_IN_MINUTE, 0, listener);
            }
        } catch (SecurityException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (!isRequestingGPSLocation() && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, Constants.MILLIS_IN_MINUTE, 0, listener);
            }
        } catch (SecurityException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        DataConnector.lastKnownLocation = getLastKnownLocation();
    }

    public boolean hasAnyLocationProviderEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    public boolean hasLocationPermissions() {
        return hasPermissions(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION);
    }

    private boolean hasPermissions(String... permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                if (!isRequestingPermissions) {
                    ActivityCompat.requestPermissions(this, new String[] {permission}, (permission.equals(Manifest.permission.ACCESS_COARSE_LOCATION) ? REQUEST_COARSE_LOCATION_CODE : REQUEST_FINE_LOCATION_CODE));
                    isRequestingPermissions = true;
                }
                return false;
            }
        }

        return true;
    }
}
