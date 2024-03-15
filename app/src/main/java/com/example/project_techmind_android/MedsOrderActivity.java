package com.example.project_techmind_android;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class MedsOrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meds_order);

        Button button1 = findViewById(R.id.option_button1);
        Button button2 = findViewById(R.id.option_button2);
        Button button3 = findViewById(R.id.option_button3);
        Button button4 = findViewById(R.id.option_button4);
        Button button5 = findViewById(R.id.option_button5);
        Button button6 = findViewById(R.id.option_button6);
        Button button7 = findViewById(R.id.option_button7);
        Button button8 = findViewById(R.id.option_button8);
        Button button9 = findViewById(R.id.option_button9);

        button1.setOnClickListener(v -> gotoUrl(getString(R.string.Option_1mg_url)));
        button2.setOnClickListener(v -> gotoUrl(getString(R.string.Option_PharmEasy_url)));
        button3.setOnClickListener(v -> gotoUrl(getString(R.string.Option_Netmeds_url)));
        button4.setOnClickListener(v -> gotoUrl(getString(R.string.Option_Medlife_url)));
        button5.setOnClickListener(v -> gotoUrl(getString(R.string.Option_Practo_url)));
        button6.setOnClickListener(v -> gotoUrl(getString(R.string.Option_Amazon_Pharmacy_url)));
        button7.setOnClickListener(v -> gotoUrl(getString(R.string.Option_MedPlus_Mart_url)));
        button8.setOnClickListener(v -> gotoUrl(getString(R.string.Option_CVS_Pharmacy_url)));
        button9.setOnClickListener(v -> gotoUrl(getString(R.string.Option_Apollo_To_Name_A_Few_url)));
    }

    private void gotoUrl(String url) {
        Uri uri = Uri.parse(url);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }
}
