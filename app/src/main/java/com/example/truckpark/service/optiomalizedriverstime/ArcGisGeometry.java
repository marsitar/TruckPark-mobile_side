package com.example.truckpark.service.optiomalizedriverstime;

import com.esri.core.geometry.OperatorBuffer;
import com.esri.core.geometry.OperatorOffset;
import com.esri.core.geometry.Polygon;
import com.esri.core.geometry.Polyline;
import com.esri.core.geometry.SpatialReference;
import com.example.truckpark.domain.entity.RouteSchedule;
import com.example.truckpark.localdatamanagment.DataGetter;
import com.example.truckpark.localdatamanagment.RouterScheduleDataManagement;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


public class ArcGisGeometry {

    public Polygon generateBufferOnTheRightSideOfRoad() {

        List<List<Double[]>> geometrySections = getGeometrySections();

        Polyline arcgisPolyline = generateArcgisPolylineFormGeometrySections(geometrySections);
        Polyline offsetArcgisPolyline = generateOffsetArcgisPolyline(arcgisPolyline);
        Polygon bufferPolygone = generatePolygon(offsetArcgisPolyline);

        return bufferPolygone;
    }

    private List<List<Double[]>> getGeometrySections() {

        DisplayOnMapService displayOnMapService = new DisplayOnMapService();
        DataGetter<RouteSchedule> routerScheduleDataManagement = new RouterScheduleDataManagement();

        return displayOnMapService.getGeometrySectionsFromRouterScheduleData(routerScheduleDataManagement);
    }

    private Polyline generateArcgisPolylineFormGeometrySections(List<List<Double[]>> geometrySections) {

        List<Double[]> listOfGenuinePolylinePoints = geometrySections.stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        Double[] firstPointOfGenuinePolyline = listOfGenuinePolylinePoints.stream()
                .findFirst()
                .orElse(null);

        Polyline generatedArcgisPolyline = new Polyline();

        generatedArcgisPolyline.startPath(firstPointOfGenuinePolyline[0], firstPointOfGenuinePolyline[1]);

        listOfGenuinePolylinePoints.stream()
                .forEach(point ->
                        generatedArcgisPolyline.lineTo(point[0], point[1])
                );

        return generatedArcgisPolyline;
    }

    private Polyline generateOffsetArcgisPolyline(Polyline polyline) {

        OperatorOffset.JoinType typeOfShapeEnding = OperatorOffset.JoinType.Square;
        SpatialReference gpsSpatialReference = SpatialReference.create(4326);
        double offsetDistance = 7.5;
        double bevelRatio = 0.0;
        double numberOfPointsOnShapeEnding = 10.0;

        return (Polyline) OperatorOffset.local().execute(polyline, gpsSpatialReference, offsetDistance, typeOfShapeEnding, bevelRatio, numberOfPointsOnShapeEnding, null);
    }

    private Polygon generatePolygon(Polyline sourcePolyline) {

        SpatialReference gpsSpatialReference = SpatialReference.create(4326);
        double polygoneRadius = 7.5;

        return (Polygon) OperatorBuffer.local().execute(sourcePolyline, gpsSpatialReference, polygoneRadius, null);
    }

}
