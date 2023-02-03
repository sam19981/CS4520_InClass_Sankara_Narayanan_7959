package com.example.inclass_sankara_narayanan_002787959.InClass02;

// Sankara Narayanan Rajagopal
//InClass02

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.inclass_sankara_narayanan_002787959.R;

public class InClass02 extends AppCompatActivity {

    final private String title = "Edit Profile Activity";
    final static String userkey ="user";
    final static String statusText[] ={"Angry","Sad","Happy","Awesome"};
    private ImageButton avatarButton;
    static String imageResid = "result";
    SeekBar status;
    Button submit;
    private int statusint = 3;
    private int imgResIdval = 0;
    private String operatingSys = "Android";


    ActivityResultLauncher<Intent> getAvatar = registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
            , new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Log.d(Tag,"Came back");
                    if (result.getResultCode()==78)
                    {
                        Intent intent = result.getData();
                        if(intent!=null) {
                            imgResIdval = intent.getIntExtra(imageResid,0);
                            avatarButton.setImageResource(imgResIdval);
                        }
                    }
                }
            });

    String Tag = "incalss02";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_class02);
        setTitle(title);

        EditText email = findViewById(R.id.email02Text);
        EditText name = findViewById(R.id.name02Id);

        RadioButton defaultOption = findViewById(R.id.radioButtonId1);
        defaultOption.setChecked(true);

        RadioGroup radioGroup = findViewById(R.id.radioGrpId);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton optionSelected = findViewById(i);
                operatingSys = String.valueOf(optionSelected.getText());
            }
        });

        submit = findViewById(R.id.submitButton);
        status = findViewById(R.id.seekBar);
        status.setProgress(3);
        status.setMax(4);
        status.setMin(1);

        ImageView img = findViewById(R.id.statusImageId);
        TextView statusView = findViewById(R.id.moodStatusId);
        statusView.setText(statusText[2]);

        status.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                statusint =i;
                statusView.setText(statusText[i-1]);
                img.setImageResource(imageToset(i));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // do nothing
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // do nothing
            }
        });

        avatarButton = findViewById(R.id.imageButtonid);
        avatarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InClass02.this,SelectAvatar.class);
                getAvatar.launch(intent);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(String.valueOf(email.getText()).equals("") || !android.util.Patterns.EMAIL_ADDRESS.matcher(String.valueOf(email.getText())).matches())
                {  email.invalidate();
                    Toast.makeText(InClass02.this, "Invalid email address!!", Toast.LENGTH_SHORT).show();
                }
                else if(String.valueOf(name.getText()).equals(""))
                {
                    Toast.makeText(InClass02.this, "Invalid Name!!", Toast.LENGTH_SHORT).show();
                }
                else if (imgResIdval ==0)
                {
                    Toast.makeText(InClass02.this, "No avatar Selected", Toast.LENGTH_SHORT).show();
                }
                else {
                    String emailVal = String.valueOf(email.getText());
                    String nameVal = String.valueOf(name.getText());
                    Intent intent = new Intent(InClass02.this, display_activity.class);

                    userDetails newUser = new userDetails(emailVal,nameVal,operatingSys,imgResIdval,statusint);

                    intent.putExtra(userkey,newUser);
                    setResult(80);
                    startActivity(intent);
                }

            }
        });


    }

    static int imageToset(int idImg)
    {
        if (idImg==1)
        {
            return R.drawable.angry;
        }
        else if(idImg==2)
        {
            return R.drawable.sad;
        }
        else if(idImg==3)
        {
            return R.drawable.happy;
        }
        else{
            return R.drawable.awesome;
        }
    }
// Practice Work
//    @Override
//    protected void onResume()
//    {
//        super.onResume();
//        Log.d(Tag,"OnRsesume called");
//    }
//    @Override
//    protected void onStart()
//    {
//        super.onStart();
//        Log.d(Tag,"OnStart Called");
//    }
//
//    @Override
//    protected void onPause()
//    {
//        super.onPause();
//        Log.d(Tag,"OnPause Called");
//    }
//
//    @Override
//    protected void onStop()
//    {
//        super.onStop();
//        Log.d(Tag,"OnStop Called");
//    }
}