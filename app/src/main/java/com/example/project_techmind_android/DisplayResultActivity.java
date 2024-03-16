package com.example.project_techmind_android;// DisplayResultActivity.java
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DisplayResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_result);

        TextView textViewJson = findViewById(R.id.textViewJson);

        // Receive JSON string from intent extra
        String jsonString = getIntent().getStringExtra("jsonString");

        // Display JSON string in TextView
        textViewJson.setText(jsonString);
    }
}
