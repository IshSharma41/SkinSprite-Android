package com.example.project_techmind_android;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Use a Handler to delay transition to MainActivity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Create an Intent to start MainActivity
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);
                finish(); // Finish the current activity so that it's not accessible via the back button
            }
        }, 2000);
    }
}
