package com.example.exam1final;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    private LocationManager locationManager;
    private LocationListener locationListener;
    private Handler locationUpdateHandler;

    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private final static int REQUEST_CODE=100;
    private static final long LOCATION_UPDATE_INTERVAL = 6000; // 1 minute

    TextView latlong;
    String savedLatLong = "";

    boolean saved = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        latlong = findViewById(R.id.textViewLatLong);

        Button start = findViewById(R.id.buttonStart);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLocationUpdates();
            }
        });

        Button stop = findViewById(R.id.buttonStop);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopLocationUpdates();
            }
        });

        Button save = findViewById(R.id.buttonSave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveLocation();
            }
        });

        Button back = findViewById(R.id.buttonBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToMainActivity();
            }
        });

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationUpdateHandler = new Handler();
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // Handle location updates here
                updateLocationText(location);
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
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save the location data to the bundle
        outState.putString("location", latlong.getText().toString());
        outState.putString("saved", savedLatLong);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore the location data from the bundle
        String savedLocation = savedInstanceState.getString("location");
        latlong.setText(savedLocation);

        String savedsaved = savedInstanceState.getString("saved");
        savedLatLong = savedsaved;
    }

    private void switchToMainActivity() {
        // Create an Intent to switch to the SecondActivity
        Intent intent = new Intent(MainActivity2.this, MainActivity.class);

        if(!savedLatLong.equals(""))
        {
            intent.putExtra("MESSAGE_KEY", savedLatLong);
        }
        startActivity(intent);
    }

    private void startLocationUpdates() {
        if(ContextCompat.checkSelfPermission(MainActivity2.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(MainActivity2.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(MainActivity2.this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
        }
        else {
            // Request location updates once a minute
            locationUpdateHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (locationManager != null)
                    {
                        locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, locationListener, null);
                        updateLocationText(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER));
                        //savedLatLong = (String) latlong.getText();
                        locationUpdateHandler.postDelayed(this, LOCATION_UPDATE_INTERVAL);
                    }
                }
            }, LOCATION_UPDATE_INTERVAL);
        }
    }

    private void stopLocationUpdates() {
        locationUpdateHandler.removeCallbacksAndMessages(null);
        if (locationManager != null && locationListener != null) {
            locationManager.removeUpdates(locationListener);
        }
    }

    private void updateLocationText(Location location)
    {
        if (location != null) {
            String locationText = "Location: " + location.getLatitude() + ", " + location.getLongitude();
            latlong.setText(locationText);
        }
        else {
            latlong.setText("No Location");
        }
    }


    private void saveLocation()
    {
        savedLatLong = (String) latlong.getText();
    }

}