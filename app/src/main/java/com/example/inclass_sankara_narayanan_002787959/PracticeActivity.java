package com.example.inclass_sankara_narayanan_002787959;

// Sankara Narayanan Rajagopal
//InClass02

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class PracticeActivity extends AppCompatActivity {

    Button logButton,toastButton;

    final String TAG = "LOGGER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);
        setTitle("Assignment1");

        logButton = findViewById(R.id.logButton);
        toastButton =findViewById(R.id.toastButton);

        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Practice!Practice!!Practice!!!");
            }
        });

        toastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PracticeActivity.this,"Now push to GitHub and Submit!!",Toast.LENGTH_LONG).show();
            }
        });
    }
}