package com.example.inclass_sankara_narayanan_002787959;

// Sankara Narayanan Rajagopal
//InClass02

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class InClass01 extends AppCompatActivity {


    EditText weight;
    EditText height_feet;
    EditText height_inches;
    TextView BMI_status_dispaly;
    TextView BMI_value_display;
    Button calculate;
    String BMI = "Your BMI: %s";
    String BMI_Status = "You are %s";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_class01);
        setTitle("BMI Calculator");

        weight = findViewById(R.id.weight_lb);
        height_feet = findViewById(R.id.height_feet_id);
        height_inches = findViewById(R.id.height_inches_id);
        calculate = findViewById(R.id.bmi_calculation);
        BMI_status_dispaly = findViewById(R.id.resul_val);
        BMI_value_display = findViewById(R.id.result_id);

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float weightVal;
                int feet=0;
                int inches = 0;
                float heightTotal;
                if (String.valueOf(weight.getText()).equals("")) {
                    Toast.makeText(InClass01.this, "Empty Value for Weight !!", Toast.LENGTH_SHORT).show();
                }
                else if (String.valueOf(height_feet.getText()).equals("") && String.valueOf(height_inches.getText()).equals("") )
                {
                    Toast.makeText(InClass01.this, "Empty Value for for Height !!", Toast.LENGTH_SHORT).show();

                }
                else {
                    weightVal = Float.parseFloat(String.valueOf(weight.getText()));
                    String inches_text =String.valueOf(height_inches.getText());
                    String feet_text = String.valueOf(height_feet.getText());

                    if(!inches_text.equals(""))
                    {
                        inches = Integer.parseInt(inches_text);
                    }

                    if(!feet_text.equals(""))
                    {
                        feet = Integer.parseInt(feet_text);
                    }

                    heightTotal = (feet * 12) + inches;

                    if (weightVal <= 0) {
                        Toast.makeText(InClass01.this, "Invalid Weight Value !!", Toast.LENGTH_SHORT).show();
                    } else if (heightTotal <= 0) {
                        Toast.makeText(InClass01.this, "Invalid Height Value!!", Toast.LENGTH_SHORT).show();
                    } else {
                        double BMI_index = (weightVal / Math.pow(heightTotal, 2)) * 703;
                        String message = "";
                        if (BMI_index >= 30) {
                            message = "Obese.";
                        } else if (BMI_index >= 25) {
                            message = "Overweight.";
                        } else if (BMI_index >= 18.5) {
                            message = "Normal weight. ";
                        } else {
                            message = "Underweight. ";
                        }
                        BMI_value_display.setText(String.format(BMI, BMI_index));
                        BMI_status_dispaly.setText(String.format(BMI_Status, message));
                        Toast.makeText(InClass01.this, "BMI Calculated.", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

    }
}