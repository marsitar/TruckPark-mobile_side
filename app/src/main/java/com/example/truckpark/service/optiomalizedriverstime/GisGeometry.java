package com.example.truckpark.service.optiomalizedriverstime;

import android.util.Log;

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


public class GisGeometry {

    private final String className = this.getClass().getSimpleName();

    public Polygon generateBufferOnTheRightSideOfRoad() {

        List<List<Double[]>> geometrySections = getGeometrySections();

        Polyline gisPolyline = generateGisPolylineFormGeometrySections(geometrySections);
        Polyline offsetGisPolyline = generateOffsetGisPolyline(gisPolyline);
        Polygon bufferPolygone = generateGisPolygon(offsetGisPolyline);

        Log.i(className, String.format("Generated BufferOnTheRightSideOfRoad: %s.", bufferPolygone));

        return bufferPolygone;
    }

    private List<List<Double[]>> getGeometrySections() {

        DisplayOnMapService displayOnMapService = new DisplayOnMapService();
        DataGetter<RouteSchedule> routerScheduleDataManagement = new RouterScheduleDataManagement();

        List<List<Double[]>> geometrySections = displayOnMapService.getGeometrySectionsFromRouterScheduleData(routerScheduleDataManagement);

        Log.d(className, "GeometrySections have been get.");

        return geometrySections;
    }

    private Polyline generateGisPolylineFormGeometrySections(List<List<Double[]>> geometrySections) {

        List<Double[]> listOfGenuinePolylinePoints = geometrySections.stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        Double[] firstPointOfGenuinePolyline = listOfGenuinePolylinePoints.stream()
                .findFirst()
                .orElse(null);

        Polyline generatedGisPolyline = new Polyline();

        generatedGisPolyline.startPath(firstPointOfGenuinePolyline[0], firstPointOfGenuinePolyline[1]);

        listOfGenuinePolylinePoints.stream()
                .forEach(point ->
                        generatedGisPolyline.lineTo(point[0], point[1])
                );

        Log.d(className, String.format("Generated GisPolyline: %s.", generatedGisPolyline));

        return generatedGisPolyline;
    }

    private Polyline generateOffsetGisPolyline(Polyline polyline) {

        OperatorOffset.JoinType typeOfShapeEnding = OperatorOffset.JoinType.Miter;
        SpatialReference gpsSpatialReference = SpatialReference.create(4326);
        double offsetDistance = 0.0003;
        double bevelRatio = 1;
        double numberOfPointsOnShapeEnding = 10.0;

        Polyline generatedOffsetGisPolyline = (Polyline) OperatorOffset.local().execute(polyline, gpsSpatialReference, offsetDistance, typeOfShapeEnding, bevelRatio, numberOfPointsOnShapeEnding, null);

        Log.d(className, String.format("Generated Offset Gis Polyline: %s.", generatedOffsetGisPolyline.toString()));

        return generatedOffsetGisPolyline;
    }

    private Polygon generateGisPolygon(Polyline sourcePolyline) {

        SpatialReference gpsSpatialReference = SpatialReference.create(4326);
        double polygoneRadius = 0.5;

        Polygon generatedPolygon = (Polygon) OperatorBuffer.local().execute(sourcePolyline, gpsSpatialReference, polygoneRadius, null);

        Log.d(className, String.format("Generated Gis Polygon: %s.", generatedPolygon));

        return generatedPolygon;
    }

}
