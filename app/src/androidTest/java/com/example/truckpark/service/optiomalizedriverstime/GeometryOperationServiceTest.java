package com.example.truckpark.service.optiomalizedriverstime;

import androidx.test.runner.AndroidJUnit4;

import com.esri.core.geometry.OperatorBuffer;
import com.esri.core.geometry.Polygon;
import com.esri.core.geometry.Polyline;
import com.esri.core.geometry.SpatialReference;
import com.example.truckpark.domain.json.mopapi.ExtendedMopData;
import com.example.truckpark.domain.json.mopapi.Mop;
import com.example.truckpark.domain.json.positionapi.Coordinate;
import com.example.truckpark.localdatamanagment.DataGetter;
import com.example.truckpark.localdatamanagment.DataSaver;
import com.example.truckpark.localdatamanagment.MopsDataManagement;
import com.example.truckpark.repository.CurrentPosition;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class GeometryOperationServiceTest {

    private Polygon polygon;

    @BeforeClass
    public void prepareData() {

        Mop mop1 = new Mop(
                1L,
                "MOP I Jeżewo",
                "MOP I",
                "Jeżewo",
                new Coordinate(53.765858, 18.767238),
                "S8",
                10,
                0,
                "1",
                new ExtendedMopData()
        );

        Mop mop2 = new Mop(
                2L,
                "MOP II Radule",
                "MOP II",
                "Radule",
                new Coordinate(53.144893, 22.793501),
                "S8",
                250,
                0,
                "2",
                new ExtendedMopData()
        );

        DataSaver<List<Mop>> routerScheduleDataSaver = new MopsDataManagement();
        routerScheduleDataSaver.save(Arrays.asList(mop1, mop2));

        CurrentPosition.getCurrentPositionInstance().setCurrentLat(54.398917);
        CurrentPosition.getCurrentPositionInstance().setCurrentLng(18.572389);


        Polyline sourcePolyline = new Polyline();
        sourcePolyline.startPath(54.015708, 18.662519);
        sourcePolyline.lineTo(53.765858, 18.767238);
        sourcePolyline.lineTo(53.223778, 18.727951);

        this.polygon = (Polygon) OperatorBuffer.local().execute(sourcePolyline, SpatialReference.create(4326), 0.5, null);

    }

    @Test
    public void getPotentialStopMops_inputCorrectValues_assertQuantityOfFeaturesIsProper() {
        //given
        GeometryOperationService geometryOperationService = new GeometryOperationService();
        //when
        List<Mop> potentialStopMops = geometryOperationService.getPotentialStopMops(polygon);
        //then
        assertThat(potentialStopMops.size(), is(greaterThan(0)));
    }

    @Test
    public void getPotentialStopMops_inputCorrectValues_assertNameOfPlaceIsProper() {
        //given
        GeometryOperationService geometryOperationService = new GeometryOperationService();
        //when
        List<Mop> potentialStopMops = geometryOperationService.getPotentialStopMops(polygon);
        //then
        assertThat(potentialStopMops.get(0).getPlace(), is(equalTo("Jeżewo")));
    }

    @Test
    public void getPotentialStopMops_inputInCorrectValues_assertResultIsEmptyList() {
        //given
        GeometryOperationService geometryOperationService = new GeometryOperationService();
        //when
        List<Mop> potentialStopMops = geometryOperationService.getPotentialStopMops(new Polygon());
        //then
        assertThat(potentialStopMops.size(), is(equalTo(0)));
    }

    @Test
    public void getPotentialStopMops_inputNullValues_assertNameOfPlaceIsProper() {
        //given
        GeometryOperationService geometryOperationService = new GeometryOperationService();
        //when
        List<Mop> potentialStopMops = geometryOperationService.getPotentialStopMops(null);
        //then
        assertThat(potentialStopMops, is(nullValue()));
    }


    @AfterClass
    public void clearData() {
        DataGetter<List<Mop>> routerScheduleDataGetter = new MopsDataManagement();
        routerScheduleDataGetter.getData().clear();

        CurrentPosition.getCurrentPositionInstance().setCurrentLat(0);
        CurrentPosition.getCurrentPositionInstance().setCurrentLng(0);
    }
}
