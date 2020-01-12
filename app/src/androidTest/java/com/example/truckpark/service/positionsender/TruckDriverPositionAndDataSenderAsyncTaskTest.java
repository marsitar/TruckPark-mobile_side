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
import java.net.NoRouteToHostException;
import java.net.URL;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

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
        truckDriverPositionAndDataSenderAsyncTask = new TruckDriverPositionAndDataSenderAsyncTask(URI);
    }

    @Test
    public void buildUrl_inputCorrectValue_assertIsEqual() throws IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        //given
        String builtURLMethodResult = getUrlFromMethod(truckDriverPositionAndDataSenderAsyncTask);
        //when
        String correctResult = "http://192.168.0.21:8080/rest/api/truckdriverways/truckdriverway";
        //then
        assertThat(correctResult, is(equalTo(builtURLMethodResult)));
    }

    @Test
    public void getHttpURLConnection_inputCorrectValue_assertIsEqual() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, IOException {
        //given
        URL url = new URL("http://192.168.0.21:8080/rest/api/truckdriverways/truckdriverway");
        HttpURLConnection httpURLConnection = getHttpURLConnectionFromMethod(url);
        String correctRequestMethod = "POST";
        //when
        String requestMethod = httpURLConnection.getRequestMethod();
        //then
        assertThat(correctRequestMethod, is(equalTo(requestMethod)));
    }

    @Test(expected = NoRouteToHostException.class)
    public void getHttpURLConnection_inputIncorrectValue_throwException() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, IOException {
        //given
        URL url = new URL("http://192.168.0.22:8080/rest/api/truckdriverways/truckdriverway");
        HttpURLConnection httpURLConnection = getHttpURLConnectionFromMethod(url);
        //when
        httpURLConnection.getResponseCode();
    }

    private String getUrlFromMethod(TruckDriverPositionAndDataSenderAsyncTask truckDriverPositionAndDataSenderAsyncTask) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        Class truckDriverPositionAndDataSenderAsyncTaskClass = truckDriverPositionAndDataSenderAsyncTask.getClass();
        Method buildURLMethod = truckDriverPositionAndDataSenderAsyncTaskClass.getDeclaredMethod("buildUrl");
        buildURLMethod.setAccessible(true);

        return (String) buildURLMethod.invoke(truckDriverPositionAndDataSenderAsyncTask);
    }

    private HttpURLConnection getHttpURLConnectionFromMethod(URL url) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {

        Class truckDriverPositionAndDataSenderAsyncTaskClass = truckDriverPositionAndDataSenderAsyncTask.getClass();
        Method getHttpURLConnectionMethod = truckDriverPositionAndDataSenderAsyncTaskClass.getDeclaredMethod("getHttpURLConnection", URL.class);
        getHttpURLConnectionMethod.setAccessible(true);

        return (HttpURLConnection) getHttpURLConnectionMethod.invoke(truckDriverPositionAndDataSenderAsyncTask, url);
    }

}
