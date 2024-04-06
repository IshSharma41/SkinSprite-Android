package com.example.project_techmind_android;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Dictionary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dictionary);

        // Get the TextView
        TextView textView = findViewById(R.id.textView);

        // Get the string resource
        String skinConditionsInfo = getString(R.string.skin_conditions_info);

        // Split the string into individual diseases
        String[] diseases = skinConditionsInfo.split("\n\n"); // Assuming each disease is separated by two line breaks

        // Build the formatted text with two line breaks after every two subsequent diseases
        StringBuilder formattedTextBuilder = new StringBuilder();
        formattedTextBuilder.append("<b>Skin Conditions:</b><br/><br/>"); // Heading with two line breaks
        for (int i = 0; i < diseases.length; i++) {
            if (i > 0 && i % 1 == 0) { // Add two line breaks after every two subsequent diseases
                formattedTextBuilder.append("<br/><br/>");
            }
            formattedTextBuilder.append("<i>").append(diseases[i]).append("</i><br/>"); // Italicize each disease
        }

        // Set the formatted text to the TextView with HTML content
        textView.setText(Html.fromHtml(formattedTextBuilder.toString()));

        // Apply window insets listener
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
