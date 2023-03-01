package com.example.inclass_sankara_narayanan_002787959.InClass04;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.inclass_sankara_narayanan_002787959.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InClass04 extends AppCompatActivity {

    private Button generate_btn;
    private ExecutorService threadPool;
    private ProgressBar progressBar;
    private SeekBar seekBar;
    private Handler messageQueue;
    private int complexcity = 8;
    private TextView complexText;
    private double totalSum = 0;
    private TextView minVal;
    private TextView maxVal;
    private TextView avgVal;

    private String setTitle = "Number Generator";


    private List<Double> generatedValues= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_class04);

        setTitle(setTitle);

        threadPool = Executors.newFixedThreadPool(1);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        progressBar.setMax(100);

        seekBar = findViewById(R.id.seekBar3);
        seekBar.setMin(1);
        seekBar.setMax(10);
        seekBar.setProgress(8);

        complexText =findViewById(R.id.complexVal);
        complexText.setText(String.valueOf(complexcity));

        minVal =findViewById(R.id.minVal);
        minVal.setVisibility(View.INVISIBLE);
        maxVal = findViewById(R.id.maxVal);
        maxVal.setVisibility(View.INVISIBLE);
        avgVal =findViewById(R.id.avgVal);
        avgVal.setVisibility(View.INVISIBLE);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    complexcity = i;
                    complexText.setText(String.valueOf(complexcity));
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

        messageQueue = new Handler(Looper.getMainLooper(),new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message message) {
                switch(message.what)
                {
                    case HeavyWork.STATUS_START:
                        progressBar.setVisibility(View.VISIBLE);
                        break;
                    case HeavyWork.STATUS_PROGRESS:
                        Bundle receivedData = message.getData();
                        int progress = receivedData.getInt(HeavyWork.KEY_PROGRESS);
                        double value = receivedData.getDouble(HeavyWork.GENERATED_VALUE);
                        totalSum+=value;
                        generatedValues.add(value);
                        progressBar.setProgress(progress);
                        break;
                    case HeavyWork.STATUS_END:
                        progressBar.setVisibility(View.INVISIBLE);
                        progressBar.setProgress(0);
                        minVal.setVisibility(View.VISIBLE);
                        maxVal.setVisibility(View.VISIBLE);
                        avgVal.setVisibility(View.VISIBLE);
                        minVal.setText(String.valueOf(Collections.min(generatedValues)));
                        maxVal.setText(String.valueOf(Collections.max(generatedValues)));
                        avgVal.setText(String.valueOf((totalSum/generatedValues.size())));
                        break;
                }
                return false;
            }
        });

        generate_btn =findViewById(R.id.generateId);
        generate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                threadPool.execute(
                        new HeavyWork(messageQueue,complexcity)
                );
            }
        });


    }
}

