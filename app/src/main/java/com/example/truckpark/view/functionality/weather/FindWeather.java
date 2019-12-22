package com.example.truckpark.view.functionality.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.truckpark.R;

public class FindWeather extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_weather);
    }
    public void onFindedWeatherDat(View view) {
        Intent FindedWeatherDat = new Intent(this, FoundWeatherData.class);
        TextView textViewPlace =findViewById(R.id.edittext);
        String text=textViewPlace.getText().toString();
        FindedWeatherDat.putExtra(FoundWeatherData.PLACEWEATHER,text);
        startActivity(FindedWeatherDat);
    }
}
