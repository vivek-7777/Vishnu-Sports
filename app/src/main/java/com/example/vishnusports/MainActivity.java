package com.example.vishnusports;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import java.lang.*;

import androidx.appcompat.app.AppCompatActivity;

//import com.example.sample.R;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    //#After completion of 2000 ms, the next activity will get started.
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //This method is used so that your splash activity
        //can cover the entire screen.
        Objects.requireNonNull(getSupportActionBar()).hide(); // hide the title bar
        setContentView(R.layout.activity_main);

        //this will bind your MainActivity.class file with activity_main.
        int SPLASH_SCREEN_TIME_OUT = 1000;
        new Handler().postDelayed(() -> {
            //Intent i = new Intent(MainActivity.this,
              //      LoginActivity.class);
            //Intent is used to switch from one activity to another.

            //startActivity(i);
            if(FirebaseAuth.getInstance().getCurrentUser() != null){
                startActivity(new Intent(MainActivity.this,ClientHome.class));
            }else{
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
            }
            //invoke the SecondActivity.

            finish();
            //the current activity will get finished.
        }, SPLASH_SCREEN_TIME_OUT);


    }

    @Override
    public void onStart() {
        super.onStart();
        // Initialize Firebase Auth
       // mAuth = FirebaseAuth.getInstance();
        // Check if user is signed in (non-null) and update UI accordingly.
        //FirebaseUser currentUser = mAuth.getCurrentUser();

    }
}
