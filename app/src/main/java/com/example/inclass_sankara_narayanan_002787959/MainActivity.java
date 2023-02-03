package com.example.inclass_sankara_narayanan_002787959;

// Sankara Narayanan Rajagopal
//InClass02

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.inclass_sankara_narayanan_002787959.InClass02.InClass02;

public class MainActivity extends AppCompatActivity {

    Button button;
    Button InClass01_btn;

    Button InClass02_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.Assignment1);

        InClass01_btn = findViewById(R.id.InClass01_btn);

        InClass02_btn = findViewById(R.id.inClass02);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent = new Intent(MainActivity.this,PracticeActivity.class);
                startActivity(newIntent);
            }
        });

        InClass01_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent = new Intent(MainActivity.this,InClass01.class);
                startActivity(newIntent);
            }
        });

        InClass02_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent = new Intent(MainActivity.this, InClass02.class);
                startActivity(newIntent);
            }
        });

    }
}