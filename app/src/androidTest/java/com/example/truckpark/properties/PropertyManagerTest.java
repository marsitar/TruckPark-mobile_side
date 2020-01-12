package com.example.truckpark.properties;

import android.content.Context;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

@RunWith(AndroidJUnit4.class)
public class PropertyManagerTest {

    private final static String PROPERTY_FILE_NAME = "openweathermap.properties";
    private static Context context;

    @BeforeClass
    public static void prepareDataForTests() {
        context = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void getPropertyCheck_getKnownProperty_assertIsEqual(){
        //given
        String URI = getURI("URI");
        //when
        String correctURI = "https://api.openweathermap.org/data/2.5/weather?";
        //then
        assertThat(correctURI, equalTo(URI));
    }

    @Test
    public void getPropertyCheck_getUnknownProperty_assertIsNull(){
        //given
        String URI = getURI("URIA");
        //then
        assertThat(URI , is(nullValue()));
    }

    private String getURI(String uri) {
        PropertyManager propertyManager = new PropertyManager(PROPERTY_FILE_NAME);
        return propertyManager.getProperty(uri, context);
    }

}
