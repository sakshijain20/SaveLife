package com.example.lifesaver.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lifesaver.MainActivity;
import com.example.lifesaver.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    EditText edt_phone,edt_password;
    Button btn_login;
    ProgressBar progressBar;
    TextView registration_activity_bypass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edt_phone = (EditText) findViewById(R.id.edt_phone);
        edt_password = (EditText) findViewById(R.id.edt_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        registration_activity_bypass = (TextView) findViewById(R.id.registration_activity_bypass);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

    }

    //Toggle on password
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (view.getId())
        {
            case R.id.edt_password:

                switch ( motionEvent.getAction() ) {
                    case MotionEvent.ACTION_DOWN:
                        Toast.makeText(getApplicationContext(),"show",Toast.LENGTH_SHORT).show();
                        edt_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        break;
                    case MotionEvent.ACTION_UP:
                        edt_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        Toast.makeText(getApplicationContext(),"hide",Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
        }
        return true;
    }


    //Registration activity bypass
    public void registration_bypass(View view) {
        startActivity(new Intent(getApplicationContext(),RegistrationActivity.class));
    }

   //Check whether log in is successful or not
    public void check_login_status(View view) {
        progressBar.setVisibility(View.VISIBLE);
        DatabaseReference postRef;
        String number = edt_phone.getText().toString();
        String password = edt_password.getText().toString();
        postRef = FirebaseDatabase.getInstance().getReference("Users").child(number);


        postRef.orderByChild("password").equalTo(password)
                .addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        progressBar.setVisibility(View.GONE);
                        if (dataSnapshot.exists()) {
                            //Toast.makeText(LoginActivity.this, "Number exist", Toast.LENGTH_SHORT).show();
                            Toast.makeText(LoginActivity.this, "Log in successful..", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));

                        }
                        else {
                            //Toast.makeText(LoginActivity.this, "No number found", Toast.LENGTH_SHORT).show();
                            edt_password.setError("Password incorrect!! ");
                            edt_password.requestFocus();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(LoginActivity.this, "Error!! ", Toast.LENGTH_SHORT).show();
                    }

                });


    }

}
