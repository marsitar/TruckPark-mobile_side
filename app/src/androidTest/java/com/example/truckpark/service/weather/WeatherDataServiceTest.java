package com.example.truckpark.service.weather;

import android.content.Context;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.example.truckpark.domain.json.weatherapi.Weather;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class WeatherDataServiceTest {

    private static Context context;

    @BeforeClass
    public static void prepareDataForTests() {
        context = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void getWeatherByCityName_inputCorrectCityName_assertResponseIsEqualToProper() {
        //given
        WeatherDataService weatherDataService = new WeatherDataService(context);
        String correctCityName = "Gdansk";
        //when
        String resultCityName = Optional.of(weatherDataService)
                .map(weatherDataServiceArg -> weatherDataService.getWeatherByCityName("Gdansk"))
                .map(Weather::getName)
                .orElse(null);
        //then
        assertThat(resultCityName, is(equalTo(correctCityName)));

    }

    @Test
    public void getWeatherByCityName_inputNullCityName_assertResponseIsNull() {
        //given
        WeatherDataService weatherDataService = new WeatherDataService(context);
        //when
        String resultCityName = Optional.of(weatherDataService)
                .map(weatherDataServiceArg -> weatherDataService.getWeatherByCityName("Gdynsk"))
                .map(Weather::getName)
                .orElse(null);
        //then
        assertThat(resultCityName, is(nullValue(String.class)));
    }

    @Test
    public void getWeatherByCityName_inputEmptyCityName_assertResponseIsNull() {
        //given
        WeatherDataService weatherDataService = new WeatherDataService(context);
        String correctCityName = "Gdansk";
        //when
        String resultCityName = Optional.of(weatherDataService)
                .map(weatherDataServiceArg -> weatherDataService.getWeatherByCityName(""))
                .map(Weather::getName)
                .orElse(null);
        //then
        assertThat(resultCityName, is(not(equalTo(correctCityName))));
    }

    @Test
    public void getWeatherByCityName_inputInCorrectCityName_assertResponseIsEqualToProper() {
        //given
        WeatherDataService weatherDataService = new WeatherDataService(context);
        String correctCityName = "Gdansk";
        //when
        String resultCityName = Optional.of(weatherDataService)
                .map(weatherDataServiceArg -> weatherDataService.getWeatherByCityName("Gdynsk"))
                .map(Weather::getName)
                .orElse(null);
        //then
        assertThat(resultCityName, is(not(equalTo(correctCityName))));
    }
}
