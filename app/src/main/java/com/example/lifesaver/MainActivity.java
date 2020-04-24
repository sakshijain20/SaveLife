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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btn_displayAddress = (Button) findViewById(R.id.btn_displayAddress);
        txt_address = (TextView) findViewById(R.id.txt_address);

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

        if (isGpsEnabled) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.d("gpsTracker","Does not match the requirements");

                return;
            }

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            if(location != null)
            {
                Log.d("gpsTracker","Success");
                txt_address.setText(" Latitude = " +location.getLatitude() + "\n Longitude = " +location.getLongitude());
            }
            else{
                Log.d("gpsTracker","Location is null");
                Toast.makeText(this, "Location is null", Toast.LENGTH_SHORT).show();
            }


        }
        else{
            Toast.makeText(this, "Make sure your GPS is on..!", Toast.LENGTH_SHORT).show();
        }




    }
}
