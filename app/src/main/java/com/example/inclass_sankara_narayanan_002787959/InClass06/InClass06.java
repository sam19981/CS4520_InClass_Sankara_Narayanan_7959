package com.example.inclass_sankara_narayanan_002787959.InClass06;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.inclass_sankara_narayanan_002787959.R;

public class InClass06 extends AppCompatActivity  {
    private String country ="ae";
    private String category ="business";
    private String title = "Head Lines";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_class06);
        setTitle(title);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentId,newsList.newInstance(category,country),"newsList").addToBackStack(null).commit();



    }

}