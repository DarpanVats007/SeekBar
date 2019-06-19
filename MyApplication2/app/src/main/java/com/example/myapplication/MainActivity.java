package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((PackageTradingPriceSlider) findViewById(R.id.smooth_seek_bar)).setOnChangeListener(new SmoothSeekBarChangeListener() {
            @Override
            public void valueChanged(int newValue) {
                Toast.makeText(getApplicationContext(), "Value Selected is " + newValue, Toast.LENGTH_LONG).show();
            }
        });

    }

}

