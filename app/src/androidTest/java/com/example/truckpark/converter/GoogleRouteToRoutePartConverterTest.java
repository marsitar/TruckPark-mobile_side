package com.example.truckpark.converter;

import androidx.test.runner.AndroidJUnit4;

import com.example.truckpark.conventer.GoogleRouteToRoutePartConventer;
import com.example.truckpark.domain.entity.RoutePart;
import com.example.truckpark.domain.entity.RouteSegment;
import com.example.truckpark.domain.json.googledirectionsapi.Bounds;
import com.example.truckpark.domain.json.googledirectionsapi.Data;
import com.example.truckpark.domain.json.googledirectionsapi.GeocodedWayPoint;
import com.example.truckpark.domain.json.googledirectionsapi.GoogleRoute;
import com.example.truckpark.domain.json.googledirectionsapi.LatLng;
import com.example.truckpark.domain.json.googledirectionsapi.Leg;
import com.example.truckpark.domain.json.googledirectionsapi.Polyline;
import com.example.truckpark.domain.json.googledirectionsapi.Route;
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
public class GoogleRouteToRoutePartConverterTest {

    @Test
    public void convertGoogleRouteToRouteSegment_inputCorrectValues_assertResultIsCorrect() {
        //given
        GoogleRoute googleRoute = new GoogleRoute(
            Arrays.asList(new GeocodedWayPoint("OK", "ChIJix1kStfPAkcRobU0Nf-St2w"), new GeocodedWayPoint("OK", "ChIJY4yat9DPAkcRME4zUu17XSM")),
            Arrays.asList(new Route(
                new Bounds(new LatLng(53.4834919,18.7613914), new LatLng(53.4826965,18.7610288)),
                    Arrays.asList(new Leg(
                        new Data("92 m", 92),
                        new Data("1 min", 19),
                        "Królewska, 86-300 Grudziądz, Poland",
                        new LatLng(53.4834919, 18.7613914),
                        "Dworcowa, 86-300 Grudziądz, Poland",
                        new LatLng(53.4826965,18.7610288),
                        Arrays.asList(
                            new Step(
                                new Data("92 m", 92),
                                new Data("1 min", 19),
                                new LatLng(53.4834919, 18.7613914),
                                "Head <b>north</b> on <b>Królewska</b> toward <b>Wyzwolenia</b>",
                                new Polyline("{yleImgoqBkC_AQG"),
                                new LatLng(53.4826965,18.7610288),
                                "DRIVING"
                            )
                        )
                    )),
                    new Polyline("{yleImgoqB}CgA")
            )),
                "OK"
        );

        RouteSegment routeSegment = new RouteSegment.Builder()
                .withDistance(92)
                .withDuration(Duration.ofSeconds(19))
                .withInnerPoints(Arrays.asList(new Double[]{53.4827, 18.76103}, new Double[]{53.4834, 18.76135}, new Double[]{18.761390000000002}))
                .withPoints(new Double[][]{new Double[]{53.4826965, 18.7610288}, new Double[]{53.4834919, 18.7613914}})
                .build();

        RoutePart routePart = new RoutePart.Builder()
                .withDuration(Duration.ofSeconds(19))
                .withDistance(92)
                .withOrigin("Dworcowa, 86-300 Grudziądz, Poland")
                .withDestination("Królewska, 86-300 Grudziądz, Poland")
                .withRouteSegments(Arrays.asList(routeSegment))
                .build();


        GoogleRouteToRoutePartConventer googleRouteToRoutePartConverter = new GoogleRouteToRoutePartConventer();
        //when
        RoutePart generatedRoutePart = googleRouteToRoutePartConverter.convertGoogleRouteToRouteSegment(googleRoute);
        //then
        assertThat(generatedRoutePart, is(equalTo(routePart)));
    }

    @Test
    public void convertGoogleRouteToRouteSegment_inputInCorrectValues_assertResultIsNotCorrect() {
        //given
        GoogleRoute googleRoute = new GoogleRoute(
                Arrays.asList(new GeocodedWayPoint("OK", "ChIJix1kStfPAkcRobU0Nf-St2w"), new GeocodedWayPoint("OK", "ChIJY4yat9DPAkcRME4zUu17XSM")),
                Arrays.asList(new Route(
                        new Bounds(new LatLng(53.4834919,18.7613914), new LatLng(53.4826965,18.7610288)),
                        Arrays.asList(new Leg(
                                new Data("92 m", 92),
                                new Data("1 min", 19),
                                "Królewska, 86-300 Grudziądz, Poland",
                                new LatLng(53.4834919, 18.7613914),
                                "Dworcowa, 86-300 Grudziądz, Poland",
                                new LatLng(53.4826965,18.7610288),
                                Arrays.asList(
                                        new Step(
                                                new Data("92 m", 92),
                                                new Data("1 min", 19),
                                                new LatLng(53.4834919, 18.7613914),
                                                "Head <b>north</b> on <b>Królewska</b> toward <b>Wyzwolenia</b>",
                                                new Polyline("{yleImgoqBkC_AQG"),
                                                new LatLng(53.4826965,18.7610288),
                                                "DRIVING"
                                        )
                                )
                        )),
                        new Polyline("{yleImgoqB}CgA")
                )),
                "OK"
        );

        RouteSegment routeSegment = new RouteSegment.Builder()
                .withDistance(92)
                .withDuration(Duration.ofSeconds(19))
                .withInnerPoints(Arrays.asList(new Double[]{53.4827, 18.76103}, new Double[]{53.4834, 18.76135}, new Double[]{18.761390000000002}))
                .withPoints(new Double[][]{new Double[]{53.4826965, 18.7610288}, new Double[]{53.4834919, 18.7613914}})
                .build();

        RoutePart routePart = new RoutePart.Builder()
                .withDuration(Duration.ofSeconds(19))
                .withDistance(92)
                .withOrigin("Dworcowe, 86-300 Grudziądz, Poland")
                .withDestination("Królewska, 86-300 Grudziądz, Poland")
                .withRouteSegments(Arrays.asList(routeSegment))
                .build();


        GoogleRouteToRoutePartConventer googleRouteToRoutePartConverter = new GoogleRouteToRoutePartConventer();
        //when
        RoutePart generatedRoutePart = googleRouteToRoutePartConverter.convertGoogleRouteToRouteSegment(googleRoute);
        //then
        assertThat(generatedRoutePart, is(not(equalTo(routePart))));
    }

    @Test
    public void convertGoogleRouteToRouteSegment_inputNullArgument_assertResultIsNull() {
        //given
        GoogleRouteToRoutePartConventer googleRouteToRoutePartConverter = new GoogleRouteToRoutePartConventer();
        //when
        RoutePart generatedRoutePart = googleRouteToRoutePartConverter.convertGoogleRouteToRouteSegment(null);
        //then
        assertThat(generatedRoutePart, is(nullValue()));
    }
}
