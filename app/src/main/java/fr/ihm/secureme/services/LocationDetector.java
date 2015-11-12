package fr.ihm.secureme.services;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by pierrebonhoure on 11/11/2015.
 */
public class LocationDetector extends Service {
    private Location init;
    private Boolean initfound=false;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }

    @Override
    public void onCreate() {
        Log.e("Service", "started");
        final LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        final String locationProvider = LocationManager.GPS_PROVIDER;
        Location lastKnownLocation;

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sp.edit();
        locationManager.requestSingleUpdate(locationProvider, new myListener(), null);

        Log.e("localisation", "demandée");


        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        if (Build.VERSION.SDK_INT >= 23) {
                            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                return;
                            }
                        }
                        while (!initfound){
                            sleep(5000);
                        }
                        Looper.prepare();
                        while (init.distanceTo(locationManager.getLastKnownLocation(locationProvider)) < sp.getInt("dist_req", 3000)) {
                            sleep(5000);
                            locationManager.requestSingleUpdate(locationProvider, new myListener(), null);
                            Log.e("Distance actuelle", "" + init.distanceTo(locationManager.getLastKnownLocation(locationProvider)));
                        }
                        if(sp.getBoolean("mode_dist",false)) {
                            Intent i = new Intent();
                            i.setClassName("fr.ihm.secureme", "fr.ihm.secureme.activity.AlarmActivity");
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            getBaseContext().startActivity(i);
                        }
                        editor.putBoolean("mode_dist",false);
                        stopSelf();
                        }
                }
                catch(InterruptedException ex){
                }

            }
        };
        Log.d("Thread", "started");
        thread.start();
    }

    private class myListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {

            Log.d("coordonnées", location.getLatitude() + " / " + location.getLongitude());
            if (!initfound) {
                Toast.makeText(getApplicationContext(), "Location set", Toast.LENGTH_SHORT).show();
                initfound=true;
                Log.d("location", "setted");
                init = location;
            }
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
    }
}
