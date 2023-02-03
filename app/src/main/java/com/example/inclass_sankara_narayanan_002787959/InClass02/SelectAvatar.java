package com.example.inclass_sankara_narayanan_002787959.InClass02;

// Sankara Narayanan Rajagopal
//InClass02

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.inclass_sankara_narayanan_002787959.R;

public class SelectAvatar extends AppCompatActivity implements View.OnClickListener {

   ImageButton avatar1;
   ImageButton avatar2;
   ImageButton avatar3;
   ImageButton avatar4;
   ImageButton avatar5;
   ImageButton avatar6;

   String Tag = "selectAvatar";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_avatar);
        setTitle("Select Avatar");
        avatar1 = findViewById(R.id.avatar1);
        avatar1.setOnClickListener(this);
        avatar2 =findViewById(R.id.avatar2);
        avatar2.setOnClickListener(this);
        avatar3=findViewById(R.id.avatar3);
        avatar3.setOnClickListener(this);
        avatar4 = findViewById(R.id.avatar4);
        avatar4.setOnClickListener(this);
        avatar5 = findViewById(R.id.avatar5);
        avatar5.setOnClickListener(this);
        avatar6 = findViewById(R.id.avatar6);
        avatar6.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        int imgId = 0;
        if(id == R.id.avatar1)
        {
            imgId = R.drawable.avatar_f_1;
        }
        else if(id == R.id.avatar2)
        {
            imgId = R.drawable.avatar_f_2;
        }
        else if(id == R.id.avatar3)
        {
            imgId = R.drawable.avatar_f_3;
        }
        else if(id == R.id.avatar4)
        {
            imgId = R.drawable.avatar_m_1;
        }
        else if(id == R.id.avatar5)
        {
            imgId = R.drawable.avatar_m_2;
        }
        else
        {
            imgId = R.drawable.avatar_m_3;
        }

        Intent intent = new Intent();
        intent.putExtra(InClass02.imageResid,imgId);
        setResult(78,intent);
        finish();
    }
}