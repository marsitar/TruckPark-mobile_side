package com.example.truckpark.view.functionality.weather;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.truckpark.R;
import com.example.truckpark.domain.json.weatherapi.Cloud;
import com.example.truckpark.domain.json.weatherapi.MainWeatherData;
import com.example.truckpark.domain.json.weatherapi.Phenomenon;
import com.example.truckpark.domain.json.weatherapi.Weather;
import com.example.truckpark.domain.json.weatherapi.Wind;
import com.example.truckpark.service.weather.WeatherDataService;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

public class FoundWeatherData extends AppCompatActivity {
    public static final String PLACEWEATHER = "placeweather";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found_weather);
        Intent intent = getIntent();

        WeatherDataService weatherDataService = new WeatherDataService(this);
        Weather weather = weatherDataService.getWeatherByCityName(intent.getStringExtra(PLACEWEATHER));

        TextView weatherContent = findViewById(R.id.weathercontent);

        String temp = String.format("☐ TEMPERATURA: %.1f°C\n", Optional.ofNullable(weather)
                .map(Weather::getMainWeatherData)
                .map(MainWeatherData::getTemp)
                .orElse(null));

        String phenomena = String.format("☐ ZJAWISKA ATMOSFERYCZNE: %s\n", Optional.ofNullable(weather)
                .map(Weather::getWeathers)
                .orElse(Collections.emptyList())
                .stream()
                .map(Phenomenon::getMain)
                .collect(Collectors.joining(", ")));

        String visibility = String.format("☐ WIDOCZNOSC: %d km\n", Optional.ofNullable(weather)
                .map(Weather::getVisibility)
                .map(visabilityInMeters -> Integer.valueOf(visabilityInMeters/1000))
                .orElse(null));

        String pressure = String.format("☐ CISNIENIE: %d hPa\n", Optional.ofNullable(weather)
                .map(Weather::getMainWeatherData)
                .map(MainWeatherData::getPressure)
                .orElse(null));

        String windSpeed = String.format("☐ PREDKOSC WIATRU: %d m/s\n", Optional.ofNullable(weather)
                .map(Weather::getWind)
                .map(Wind::getSpeed)
                .orElse(null));

        String clouds = String.format("☐ ZACHMURZENIE: %s%%\n", Optional.ofNullable(weather)
                .map(Weather::getCloud)
                .map(Cloud::getAll)
                .orElse(null));

        SpannableStringBuilder tempBold = getSpannableStringBuilder(temp, 0, 14);
        SpannableStringBuilder phenomenaBold = getSpannableStringBuilder(phenomena, 0, 25);
        SpannableStringBuilder visibilityBold = getSpannableStringBuilder(visibility, 0, 13);
        SpannableStringBuilder pressureBold = getSpannableStringBuilder(pressure, 0, 12);
        SpannableStringBuilder windSpeedBold = getSpannableStringBuilder(windSpeed, 0, 18);
        SpannableStringBuilder cloudsBold = getSpannableStringBuilder(clouds, 0, 15);

        weatherContent.setText(tempBold
                .append(phenomenaBold)
                .append(visibilityBold)
                .append(pressureBold)
                .append(windSpeedBold)
                .append(cloudsBold));


        TextView mopName = findViewById(R.id.placename);
        mopName.setText(Optional.ofNullable(weather)
                            .map(Weather::getName)
                            .orElse(null));
    }

    private SpannableStringBuilder getSpannableStringBuilder(String stringToBeBold, int start, int end) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(stringToBeBold);
        spannableStringBuilder.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableStringBuilder;
    }
}
