package com.example.truckpark;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class FindedWeatherData extends AppCompatActivity {
    public static final String PLACEWEATHER = "placeweather";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finded_weather);
        Intent intent =getIntent();
        String messageText = intent.getStringExtra(PLACEWEATHER);
        TextView mopName = (TextView)findViewById(R.id.placename);
        mopName.setText(messageText);
    }
}
