package com.example.truckpark.view.functionality.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.widget.TextView;

import com.example.truckpark.R;
import com.example.truckpark.domain.json.weatherapi.Phenomenon;
import com.example.truckpark.domain.json.weatherapi.Weather;
import com.example.truckpark.service.weather.RequestWeatherDataService;

import java.util.Optional;

public class FoundWeatherData extends AppCompatActivity {
    public static final String PLACEWEATHER = "placeweather";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found_weather);
        Intent intent =getIntent();
        String messageText = intent.getStringExtra(PLACEWEATHER);

        //THINK ABOUT IT LATER,async attitute would be better here in future
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ///////////////////////////////////////////////////////////////////////////////////////////

        RequestWeatherDataService requestWeatherDataService = new RequestWeatherDataService(this);
        Weather weather = requestWeatherDataService.getWeatherByCityName(intent.getStringExtra(PLACEWEATHER));

        TextView weatherContent = (TextView) findViewById(R.id.weathercontent);

        String temp = String.format("☐ TEMPERATURA: %f\n", weather.getMainWeatherData().getTemp());
        String phenomena = String.format("☐ OPADY: %s\n", Optional.of(weather).map(Weather::getWeathers).orElse(null).stream().findFirst().map(Phenomenon::getMain).orElse("brak"));
        String visibility = String.format("☐ WIDOCZNOSC: %d\n", weather.getVisibility());
        String pressure = String.format("CISNIENIE: %d\n", weather.getMainWeatherData().getPressure());
        String windSpeed = String.format("☐ PREDKOSC WIATRU: %d X\n", weather.getWind().getSpeed());
        String clouds = String.format("☐ ZACHMURZENIE: %s X\n", weather.getCloud().getAll());

        SpannableStringBuilder tempBold = getSpannableStringBuilder(temp, 0, 14);
        SpannableStringBuilder phenomenaBold = getSpannableStringBuilder(phenomena, 0, 8);
        SpannableStringBuilder visibilityBold = getSpannableStringBuilder(visibility, 0, 13);
        SpannableStringBuilder pressureBold = getSpannableStringBuilder(pressure, 0, 10);
        SpannableStringBuilder windSpeedBold = getSpannableStringBuilder(windSpeed, 0, 17);
        SpannableStringBuilder cloudsBold = getSpannableStringBuilder(clouds, 0, 15);

        weatherContent.setText(tempBold
                .append(phenomenaBold)
                .append(visibilityBold)
                .append(pressureBold)
                .append(windSpeedBold)
                .append(cloudsBold));



        TextView mopName = (TextView)findViewById(R.id.placename);
        mopName.setText(messageText);
    }

    private SpannableStringBuilder getSpannableStringBuilder(String stringToBeBold, int start, int end) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(stringToBeBold);
        spannableStringBuilder.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableStringBuilder;
    }
}
