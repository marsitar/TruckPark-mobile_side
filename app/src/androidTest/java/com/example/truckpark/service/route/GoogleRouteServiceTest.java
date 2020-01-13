package com.example.truckpark.service.route;

import android.content.Context;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.example.truckpark.domain.json.googledirectionsapi.GoogleRoute;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(AndroidJUnit4.class)
public class GoogleRouteServiceTest {

    private static Context context;

    @BeforeClass
    public static void prepareDataForTests() {
        context = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void getGoogleRoute_inputCorrectParameter_assertIsEqual() {
        //given
        GoogleRouteService googleRouteService = new GoogleRouteService(context);
        String correctStatus = "OK";
        //when
        GoogleRoute googleRoute = googleRouteService.getGoogleRoute("Gdansk", "Gdynia");
        String resultStatus = googleRoute.getStatus();
        //then
        assertThat(resultStatus, equalTo(correctStatus));
    }

    @Test
    public void getGoogleRoute_inputNullParameter_assertIsEqual() {
        //given
        GoogleRouteService googleRouteService = new GoogleRouteService(context);
        String correctStatus = "ZERO_RESULTS";
        //when
        GoogleRoute googleRoute = googleRouteService.getGoogleRoute("Gdansk", null);
        String resultStatus = googleRoute.getStatus();
        //then
        assertThat(resultStatus, equalTo(correctStatus));
    }

    @Test
    public void getGoogleRoute_inputIncorrectParameter_assertIsEqual() {
        //given
        GoogleRouteService googleRouteService = new GoogleRouteService(context);
        String correctStatus = "NOT_FOUND";
        //when
        GoogleRoute googleRoute = googleRouteService.getGoogleRoute("Gdansk", "Dyssxxxasas");
        String resultStatus = googleRoute.getStatus();
        //then
        assertThat(resultStatus, equalTo(correctStatus));
    }
}