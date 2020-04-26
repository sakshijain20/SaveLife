package com.example.lifesaver.UI;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.lifesaver.MainActivity;
import com.example.lifesaver.Model.DemoDataClass;
import com.example.lifesaver.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {
    EditText edt_name,edt_password,edt_number,edt_secondary_number;
    Button btn_register;
    String name,password,secondary_number,number;
    ProgressBar progressBar;
    FirebaseDatabase database;
    DatabaseReference dbReference;
    protected Location location;
    protected LocationRequest locationRequest;
    protected LocationCallback locationCallback;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_PERMISSION_REQUEST_CODE=1;
    Bundle extras=new Bundle();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        edt_name = (EditText) findViewById(R.id.edt_name);
        edt_number = (EditText) findViewById(R.id.edt_number);
        edt_secondary_number = (EditText) findViewById(R.id.edt_secondary_number);
        edt_password = (EditText) findViewById(R.id.edt_password);
        btn_register = (Button)findViewById(R.id.btn_register);
        progressBar = (ProgressBar)findViewById(R.id.progress_bar);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000).
                setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY).
                setFastestInterval(1000).setFastestInterval(1000);


        fusedLocationProviderClient.requestLocationUpdates(locationRequest,new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

            }
        },getMainLooper());


    }


    //Toggle button for password
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (view.getId())
        {
            case R.id.edt_password:

                switch ( motionEvent.getAction() ) {
                    case MotionEvent.ACTION_DOWN:
                        Toast.makeText(getApplicationContext(),"show",Toast.LENGTH_SHORT).show();
                        edt_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        break;
                    case MotionEvent.ACTION_UP:
                        edt_password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                        Toast.makeText(getApplicationContext(),"hide",Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
        }
        return true;
    }


    //Check if registration is successful or not
    public void check_registration_status(View view) {
        progressBar.setVisibility(View.VISIBLE);

        name=edt_name.getText().toString().trim();
        password=edt_password.getText().toString().trim();
        number=edt_number.getText().toString().trim();
        secondary_number=edt_secondary_number.getText().toString().trim();




        //Required fields are available
        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(number) && number.length() == 10){

            database=FirebaseDatabase.getInstance();
            dbReference=database.getReference("Users");


            //Secondary number not provided
            if(TextUtils.isEmpty(secondary_number)){
                DemoDataClass demo=new DemoDataClass(name,password,location.getLatitude(),location.getLongitude());
                register_user(demo, number);
            }
            //secondary number provided
            else{
                DemoDataClass demo=new DemoDataClass(name,password,secondary_number,location.getLatitude(),location.getLongitude());
                register_user(demo,number);

            }

        }

        //If any required field data is missing
        else{
            progressBar.setVisibility(View.GONE);
            if(TextUtils.isEmpty(name)){
                edt_name.setError("Name is required!");
                edt_name.requestFocus();
            }

            if(TextUtils.isEmpty(password)){
                edt_name.setError("Password is required!");
                edt_name.requestFocus();
            }

            if(TextUtils.isEmpty(number)){
                edt_name.setError("Number is required!");
                edt_name.requestFocus();
            }

            if(number.length()!=10){
                edt_number.setError("Please enter a valid number!");
                edt_number.requestFocus();
            }

            else{
                Toast.makeText(this, "All fields are required!!", Toast.LENGTH_SHORT).show();
            }
        }

    }



    private void register_user(DemoDataClass demo, String number) {
        progressBar.setVisibility(View.GONE);
        location = getLastLocation();
        dbReference.child(this.number).push().setValue(demo, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if(databaseError!=null){
                    Toast.makeText(getApplicationContext(), "Some error occurred", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "User registered successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("latitude",location.getLatitude());
                    intent.putExtra("longitude",location.getLongitude());
                    startActivity(intent);
                }
            }
        });
    }




    @Override
    protected void onStart() {
        super.onStart();

        if(!checkPermission() ){
            requestPermissions();
        }
        else{
            getLastLocation();
        }
    }


    private boolean checkPermission(){
        int permissionState = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 1){
            if(grantResults.length <=0 ){
                Toast.makeText(this, "User denied", Toast.LENGTH_SHORT).show();

            }
            else if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getLastLocation();

            }
            else{
                Toast.makeText(getApplicationContext(),"Permission denied",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private Location getLastLocation() {
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(this, new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if(task.isSuccessful() && task.getResult() != null)
                {
                    location = task.getResult();
                }
                else{
                    Log.d("tracker","got exception");
                }
            }
        });
        return location;
    }



    private void startLocationPermissionRequest(){
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_PERMISSION_REQUEST_CODE);
    }

    private void requestPermissions(){
        boolean shouldProvide = ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION);

        if(shouldProvide)
        {
            startLocationPermissionRequest();
        }
        else{
            Toast.makeText(getApplicationContext(), "Requesting permissions", Toast.LENGTH_SHORT).show();
            startLocationPermissionRequest();
        }
    }


}
