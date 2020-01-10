package com.example.truckpark.service.positionsender;

import android.content.Context;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.example.truckpark.properties.PropertyManager;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class TruckDriverPositionAndDataSenderAsyncTaskTest {

    private final static String PROPERTY_FILE_NAME = "truckparkserver.properties";
    private static String URI;
    private static TruckDriverPositionAndDataSenderAsyncTask truckDriverPositionAndDataSenderAsyncTask;

    @BeforeClass
    public static void prepareDataForTests() {
        Context context = InstrumentationRegistry.getTargetContext();
        PropertyManager propertyManager = new PropertyManager(PROPERTY_FILE_NAME);
        URI = propertyManager.getProperty("URI", context);
        truckDriverPositionAndDataSenderAsyncTask = new TruckDriverPositionAndDataSenderAsyncTask(PROPERTY_FILE_NAME);
    }

    @Test
    public void buildUrl_inputCorrectValue_assertIsEqual() throws IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        String builtURLMethodResult = getUrlFromMethod(truckDriverPositionAndDataSenderAsyncTask);

        String correctResult = "http://192.168.0.21:8080/rest/api/truckdriverways/truckdriverway";

        assertThat(correctResult, is(not(equalTo(builtURLMethodResult))));
    }

    @Test
    public void buildUrl_inputCorrectValue_assesrtIsEqual() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, IOException {

        String httpAddress = getUrlFromMethod(truckDriverPositionAndDataSenderAsyncTask);
        URL url = new URL(httpAddress);

        HttpURLConnection httpURLConnection = getHttpURLConnectionFromMethod(url);
        int responseCode = httpURLConnection.getResponseCode();
        int correctResponseCode = 200;

        assertThat(correctResponseCode, is(equalTo(responseCode)));
    }

    @Test
    public void buildUrl_inputIncorrectValue_assesrtIsEqual() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, IOException {

        HttpURLConnection httpURLConnection = getHttpURLConnectionFromMethod(null);

        int responseCode = httpURLConnection.getResponseCode();
        int correctResponseCode = 200;

        assertThat(correctResponseCode, is(not(equalTo(responseCode))));
    }

    private String getUrlFromMethod(TruckDriverPositionAndDataSenderAsyncTask truckDriverPositionAndDataSenderAsyncTask) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Class truckDriverPositionAndDataSenderAsyncTaskClass = truckDriverPositionAndDataSenderAsyncTask.getClass();
        Method buildURLMethod = truckDriverPositionAndDataSenderAsyncTaskClass.getDeclaredMethod("buildUrl", String.class);
        buildURLMethod.setAccessible(true);
        return (String) buildURLMethod.invoke(truckDriverPositionAndDataSenderAsyncTask);
    }

    private HttpURLConnection getHttpURLConnectionFromMethod(URL url) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Class truckDriverPositionAndDataSenderAsyncTaskClass = truckDriverPositionAndDataSenderAsyncTask.getClass();
        Method buildURLMethod = truckDriverPositionAndDataSenderAsyncTaskClass.getDeclaredMethod("getHttpURLConnection", URL.class);
        buildURLMethod.setAccessible(true);
        return (HttpURLConnection) buildURLMethod.invoke(truckDriverPositionAndDataSenderAsyncTask, url);
    }

}
