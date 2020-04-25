package com.example.lifesaver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lifesaver.UI.LoginActivity;

public class MainActivity extends AppCompatActivity implements LocationListener {

    Button btn_displayAddress;
    TextView txt_address;
    LocationListener locationListener;
    LocationManager locationManager;
    Location location;
    Boolean isGpsEnabled = false;
    String provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btn_displayAddress = (Button) findViewById(R.id.btn_displayAddress);
        txt_address = (TextView) findViewById(R.id.txt_address);
        provider = LocationManager.GPS_PROVIDER;

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("gpsTracker","onLocationChanged");

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("gpsTracker","OnProviderEnabled");

    }


    @Override
    public void onProviderDisabled(String provider) {
        Log.d("gpsTracker","OnProviderDisbaled");

    }

    public void getCurrentLocation(View view) {
        Log.d("gpsTracker","button clicked");

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        try{
            Log.d("gpsTracker","In try block");
            if(isGpsEnabled)
            {
                String provider = LocationManager.GPS_PROVIDER;
                location=new Location(provider);

                Log.d("gpsTracker","Location provided");

                location = locationManager.getLastKnownLocation(provider);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
                
                Log.d("gpsTracker","after request");
                if(location==null)
                {
                    Log.d("gpsTracker","Location is null!");
                }
                else{
                    Double latitude= location.getLatitude();
                    Double longitude = location.getLongitude();
                    txt_address.setText("Latitude = " +latitude + "\nLongitude = " +longitude);
                }

            }
            else{
                Toast.makeText(this, "Make sure your GPS is on..!", Toast.LENGTH_SHORT).show();
            }
        }
        catch(SecurityException e)
        {
            Log.d("gpsTracker","In catch block");
            e.printStackTrace();
        }


    }
}
