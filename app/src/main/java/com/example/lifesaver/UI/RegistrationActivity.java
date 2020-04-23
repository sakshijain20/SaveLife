package com.example.lifesaver.UI;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.lifesaver.MainActivity;
import com.example.lifesaver.Model.DemoDataClass;
import com.example.lifesaver.R;
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
        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(number)){

            database=FirebaseDatabase.getInstance();
            dbReference=database.getReference("Users");

            //Secondary number not provided
            if(TextUtils.isEmpty(secondary_number)){
                DemoDataClass demo=new DemoDataClass(name,password);
                register_user(demo, number);
            }
            //secondary number provided
            else{
                DemoDataClass demo=new DemoDataClass(name,password,secondary_number);
                register_user(demo,number);

            }

        }

        //If any required field data is missing
        else{
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

            else{
                Toast.makeText(this, "All fields are required!!", Toast.LENGTH_SHORT).show();
            }
        }

    }



    private void register_user(DemoDataClass demo, String number) {
        progressBar.setVisibility(View.GONE);
        dbReference.child(this.number).push().setValue(demo, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if(databaseError!=null){
                    Toast.makeText(getApplicationContext(), "Some error occurred", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "User registered successfully!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
            }
        });
    }

}
