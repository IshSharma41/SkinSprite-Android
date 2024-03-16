package com.example.project_techmind_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.project_techmind_android.AlarmActivity;
import com.example.project_techmind_android.MainActivity;
import com.example.project_techmind_android.MedsOrderActivity;
import com.example.project_techmind_android.PastPrescriptionsActivity;
import com.example.project_techmind_android.SettingsActivity;
import com.example.project_techmind_android.UploadActivity;
import com.example.project_techmind_android.UserHistoryActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class FeaturesPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_features_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Find buttons by their IDs
        ImageButton btn1 = findViewById(R.id.btn1);
        ImageButton btn2 = findViewById(R.id.btn2);
        ImageButton btn3 = findViewById(R.id.btn3);
        ImageButton btn4 = findViewById(R.id.btn4);
        ImageButton btn5 = findViewById(R.id.btn5);
        ImageButton btn6 = findViewById(R.id.btn6);
        ImageButton logoutBtn = findViewById(R.id.logoutBtn);


        // Set click listeners for each button
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Action to be performed when Button 1 is clicked
                Intent intent = new Intent(FeaturesPageActivity.this, UploadActivity.class);
                startActivity(intent);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Action to be performed when Button 2 is clicked
                Intent intent = new Intent(FeaturesPageActivity.this, PastPrescriptionsActivity.class);
                startActivity(intent);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Action to be performed when Button 3 is clicked
                Intent intent = new Intent(FeaturesPageActivity.this, AlarmActivity.class);
                startActivity(intent);
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Action to be performed when Button 4 is clicked
                Intent intent = new Intent(FeaturesPageActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Action to be performed when Button 5 is clicked
                Intent intent = new Intent(FeaturesPageActivity.this, MedsOrderActivity.class);
                startActivity(intent);
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Action to be performed when Button 6 is clicked
                Intent intent = new Intent(FeaturesPageActivity.this, UserHistoryActivity.class);
                startActivity(intent);
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FeaturesPageActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Close the current activity
            }
        });
        FloatingActionButton fabChatbot = findViewById(R.id.floatingActionButtonChatBot);
        fabChatbot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open ChatbotActivity
                Intent intent = new Intent(FeaturesPageActivity.this, ChatbotActivity.class);
                startActivity(intent);
            }
        });
    }}
