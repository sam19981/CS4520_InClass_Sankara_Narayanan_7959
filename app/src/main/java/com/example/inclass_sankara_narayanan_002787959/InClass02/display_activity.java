package com.example.inclass_sankara_narayanan_002787959.InClass02;

// Sankara Narayanan Rajagopal
//InClass02

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.inclass_sankara_narayanan_002787959.R;

public class display_activity extends AppCompatActivity {

    private String title = "Display Acivity";

    private String moodText = "I am %s!";

    private String opSysText = "I use %s!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        setTitle(title);

        ImageView bioDp = findViewById(R.id.userAvatarId);
        ImageView statusIcon = findViewById(R.id.statusIconId);
        TextView name = findViewById(R.id.dispName);
        TextView email = findViewById(R.id.dispEmail);
        TextView operatingSys = findViewById(R.id.optSysId);
        TextView mood = findViewById(R.id.moodStatus);

        Intent intent = getIntent();

        if (intent!=null && intent.getExtras()!=null)
        {
            userDetails newUser = intent.getParcelableExtra(InClass02.userkey);

//            bioDp.setImageResource(intent.getIntExtra("bioDp",0));
            bioDp.setImageResource(newUser.getAvatarId());
//            statusIcon.setImageResource(InClass02.imageToset(intent.getIntExtra("mood",0)));
            statusIcon.setImageResource(InClass02.imageToset(newUser.getMood()));
//            name.setText(intent.getStringExtra("nameVal"));
            name.setText(newUser.getName());
//            email.setText(intent.getStringExtra("emailVal"));
            email.setText(newUser.getEmailAddress());
//            mood.setText(InClass02.statusText[intent.getIntExtra("mood",0)-1]);
            mood.setText(String.format(moodText,InClass02.statusText[newUser.getMood()-1]));
//            operatingSys.setText(intent.getStringExtra("opSys"));
            operatingSys.setText(String.format(opSysText,newUser.getOpSys()));
        }
    }
}