package com.example.truckpark.converter;

import androidx.test.runner.AndroidJUnit4;

import com.example.truckpark.conventer.StepToRouteSegmentConverter;
import com.example.truckpark.domain.entity.RouteSegment;
import com.example.truckpark.domain.json.googledirectionsapi.Data;
import com.example.truckpark.domain.json.googledirectionsapi.LatLng;
import com.example.truckpark.domain.json.googledirectionsapi.Polyline;
import com.example.truckpark.domain.json.googledirectionsapi.Step;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.Duration;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class StepToRouteSegmentConverterTest {

    @Test
    public void convertStepToRouteSegment_inputCorrectValues_assertResultIsCorrect() {
        //given
        Step step = new Step(
                new Data("5 m", 5),
                new Data("1 min", 1),
                new LatLng(54.3520654, 18.6466084),
                "Head <b>north</b> on <b>Wały Jagiellońskie</b>/<wbr/><b>DK91</b> toward <b>Hucisko</b>",
                new Polyline("csvjIm|xpBIB"),
                new LatLng(54.3520236, 18.646629),
                "DRIVING"
        );

        RouteSegment routeSegment = RouteSegment.builder()
                .distance(5)
                .duration(Duration.ofMinutes(1))
                .innerPoints(Arrays.asList(new Double[]{54.35202, 18.646630000000002}, new Double[]{54.352070000000005, 18.646610000000003}))
                .points(new Double[][]{new Double[]{54.3520236, 18.646629}, new Double[]{54.3520654, 18.6466084}})
                .build();

        StepToRouteSegmentConverter stepToRouteSegmentConverter = new StepToRouteSegmentConverter();
        //when
        RouteSegment generatedRouteSegment = stepToRouteSegmentConverter.convertStepToRouteSegment(step);
        //then
        assertThat(generatedRouteSegment, is(equalTo(routeSegment)));
    }

    @Test
    public void convertStepToRouteSegment_inputIncorrectValues_assertResultIsInvalid() {
        //given
        Step step = new Step(
                new Data("5 m", 5),
                new Data("1 min", 1),
                new LatLng(54.3520654, 18.6466084),
                "Head <b>north</b> on <b>Wały Jagiellońskie</b>/<wbr/><b>DK91</b> toward <b>Hucisko</b>",
                new Polyline("csvjIm|xpBIB"),
                new LatLng(54.3520236, 18.646629),
                "DRIVING"
        );

        RouteSegment routeSegment = RouteSegment.builder()
                .distance(6)
                .duration(Duration.ofMinutes(1))
                .innerPoints(Arrays.asList(new Double[]{54.35202, 18.646630000000002}, new Double[]{54.352070000000005, 18.646610000000003}))
                .points(new Double[][]{new Double[]{54.3520236, 18.646629}, new Double[]{66.520654, 18.6466084}})
                .build();

        StepToRouteSegmentConverter stepToRouteSegmentConverter = new StepToRouteSegmentConverter();
        //when
        RouteSegment generatedRouteSegment = stepToRouteSegmentConverter.convertStepToRouteSegment(step);
        //then
        assertThat(generatedRouteSegment, is(not(equalTo(routeSegment))));
    }

    @Test
    public void convertStepToRouteSegment_inputNullArgument_assertResultIsNull() {
        //given
        StepToRouteSegmentConverter stepToRouteSegmentConverter = new StepToRouteSegmentConverter();
        //when
        RouteSegment generatedRouteSegment = stepToRouteSegmentConverter.convertStepToRouteSegment(null);
        //then
        assertThat(generatedRouteSegment, is(nullValue()));
    }


}
