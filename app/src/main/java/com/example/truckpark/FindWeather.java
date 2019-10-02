package com.example.truckpark;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class FindWeather extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_weather);
    }
    public void onFindedWeatherDat(View view) {
        Intent FindedWeatherDat = new Intent(this,FindedWeatherData.class);
        TextView textViewPlace =(TextView)findViewById(R.id.edittext);
        String text=textViewPlace.getText().toString();
        FindedWeatherDat.putExtra(FindedWeatherData.PLACEWEATHER,text);
        startActivity(FindedWeatherDat);
    }
}
