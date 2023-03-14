package com.example.inclass_sankara_narayanan_002787959;

// Sankara Narayanan Rajagopal
//InClass02

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.inclass_sankara_narayanan_002787959.InClass02.InClass02;
import com.example.inclass_sankara_narayanan_002787959.InClass03.InClass03;
import com.example.inclass_sankara_narayanan_002787959.InClass04.InClass04;
import com.example.inclass_sankara_narayanan_002787959.InClass05.InClass05;
import com.example.inclass_sankara_narayanan_002787959.InClass06.InClass06;

public class MainActivity extends AppCompatActivity {

    Button button;
    Button InClass01_btn;

    Button InClass02_btn;

    Button InClass03_btn;

    Button InClass04_btn;

    Button InClass05_btn;

    Button InClass06_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.Assignment1);

        InClass01_btn = findViewById(R.id.InClass01_btn);

        InClass02_btn = findViewById(R.id.inClass02);

        InClass03_btn = findViewById(R.id.inClass03);

        InClass04_btn = findViewById(R.id.inClass04);

        InClass05_btn = findViewById(R.id.InClass05);

        InClass06_btn = findViewById(R.id.InClass06);

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

        InClass03_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent = new Intent(MainActivity.this, InClass03.class);
                startActivity(newIntent);
            }
        });

        InClass04_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent = new Intent(MainActivity.this, InClass04.class);
                startActivity(newIntent);
            }
        });

        InClass05_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent = new Intent(MainActivity.this, InClass05.class);
                startActivity(newIntent);
            }
        });

        InClass06_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent = new Intent(MainActivity.this, InClass06.class);
                startActivity(newIntent);
            }
        });

    }
}