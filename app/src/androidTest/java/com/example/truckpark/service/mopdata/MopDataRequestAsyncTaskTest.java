package com.example.truckpark.service.mopdata;

import android.content.Context;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.example.truckpark.domain.json.mopapi.Mop;
import com.example.truckpark.properties.PropertyManager;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(AndroidJUnit4.class)
public class MopDataRequestAsyncTaskTest {

    private final static String PROPERTY_FILE_NAME = "truckparkserver.properties";
    private static String URI;

    @BeforeClass
    public static void prepareDataForTests() {
        Context context = InstrumentationRegistry.getTargetContext();
        PropertyManager propertyManager = new PropertyManager(PROPERTY_FILE_NAME);
        URI = propertyManager.getProperty("URI", context);
    }

    @Test
    public void doInBackground_inputCorrectValue_assertIsNotNull() throws ExecutionException, InterruptedException {
        //given
        MopDataRequestAsyncTask mopDataRequestAsyncTask = new MopDataRequestAsyncTask(URI, "mops");
        //when
        List<Mop> mopsData = mopDataRequestAsyncTask.execute().get();
        //then
        assertThat(mopsData, is(notNullValue()));
    }

    @Test
    public void doInBackground_inputNullValue_assertIsNull() throws ExecutionException, InterruptedException {
        //given
        MopDataRequestAsyncTask mopDataRequestAsyncTask = new MopDataRequestAsyncTask(URI, null);
        //when
        List<Mop> mopsData = mopDataRequestAsyncTask.execute().get();
        //then
        assertThat(mopsData, is(nullValue()));
    }

    @Test
    public void doInBackground_inputEmptyValue_assertIsNull() throws ExecutionException, InterruptedException {
        //given
        MopDataRequestAsyncTask mopDataRequestAsyncTask = new MopDataRequestAsyncTask(URI, "");
        //when
        List<Mop> mopsData = mopDataRequestAsyncTask.execute().get();
        //then
        assertThat(mopsData, is(nullValue()));
    }

    @Test
    public void doInBackground_inputIncorrectValue_assertIsNull() throws ExecutionException, InterruptedException {
        //given
        MopDataRequestAsyncTask mopDataRequestAsyncTask = new MopDataRequestAsyncTask(URI, "asdasd");
        //when
        List<Mop> mopsData = mopDataRequestAsyncTask.execute().get();
        //then
        assertThat(mopsData, is(nullValue()));
    }

    @Test
    public void buildUrl_inputCorrectValue_assertIsEqual() throws NoSuchMethodException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        //given
        MopDataRequestAsyncTask mopDataRequestAsyncTask = new MopDataRequestAsyncTask(URI, "mops");
        String correctResult = "http://192.168.0.21:8080/rest/api/mops/all";
        //when
        String builtURLMethodResult = getUrlFromMethod(mopDataRequestAsyncTask, "mops");
        //then
        assertThat(builtURLMethodResult,equalTo(correctResult));
    }

    @Test
    public void buildUrl_inputIncorrectValue_assertIsNotEqual() throws NoSuchMethodException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        //given
        MopDataRequestAsyncTask mopDataRequestAsyncTask = new MopDataRequestAsyncTask(URI, "mops");
        String correctResult = "http://192.168.0.21:8080/rest/api/mops/all";
        //when
        String builtURLMethodResult = getUrlFromMethod(mopDataRequestAsyncTask, "mopy");
        //then
        assertThat(builtURLMethodResult,is(not(equalTo(correctResult))));
    }

    private String getUrlFromMethod(MopDataRequestAsyncTask mopDataRequestAsyncTask, String category) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        Class mopDataRequestAsyncTaskClass = mopDataRequestAsyncTask.getClass();
        Method buildURLMethod = mopDataRequestAsyncTaskClass.getDeclaredMethod("buildUrl", String.class);
        buildURLMethod.setAccessible(true);

        return (String) buildURLMethod.invoke(mopDataRequestAsyncTask, category);
    }
}