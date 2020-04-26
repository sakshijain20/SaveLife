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
public class MainActivity extends AppCompatActivity{

    Intent intent = getIntent();
    Bundle extras = intent.getExtras();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        double latitude = extras.getDouble("latitude");
        double longitude = extras.getDouble("longitude");
        Toast.makeText(getApplicationContext(),"Latitude = " +latitude+ "\nLongitude = " +longitude,Toast.LENGTH_SHORT).show();


    }

}
