package com.example.truckpark.service.optiomalizedriverstime;

import android.util.Log;

import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.OperatorWithin;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.Polygon;
import com.esri.core.geometry.SpatialReference;
import com.example.truckpark.domain.json.mopapi.Mop;
import com.example.truckpark.localdatamanagment.DataGetter;
import com.example.truckpark.localdatamanagment.MopsDataManagement;
import com.example.truckpark.repository.CurrentPosition;

import java.util.List;
import java.util.stream.Collectors;

public class GeometryOperationService {

    private static final int SPATIAL_REFERENCE_EPSG_CODE = 4326;
    private static final double RADIUS_DISTANCE_IN_WGS84_UNIT = 0.5;

    private final String className = this.getClass().getSimpleName();

    public List<Mop> getPotentialStopMops(Polygon polygon) {

        List<Mop> mopsFormRepository = getMopsFromRepository();
        Point currentGpsGisPoint = createGisPointFromCurrentPosition();

        List<Mop> potentialStopMops = mopsFormRepository.stream()
                .filter(mop -> {
                    Point mopGisPoint = createGisPointFromMop(mop);
                    return isMopWithinBuffer(mopGisPoint, polygon);
                })
                .filter(mop -> {
                    Point mopGisPoint = createGisPointFromMop(mop);
                    return isMopInsideRadius(mopGisPoint, currentGpsGisPoint);
                })
                .collect(Collectors.toList());

        Log.i(className, String.format("Filtered potentialStopMops list: %s.", potentialStopMops));

        return potentialStopMops;
    }

    private List<Mop> getMopsFromRepository() {

        DataGetter<List<Mop>> mopsDataManagement = new MopsDataManagement();
        List<Mop> mopsFromRepository = mopsDataManagement.getData();

        Log.d(className, "Mops have been get from repository.");

        return mopsFromRepository;
    }

    private Point createGisPointFromMop(Mop mop) {

        Double x = mop.getCoordinate().getX();
        Double y = mop.getCoordinate().getY();

        Point gisPointFromMop = setCoordinatesToPoint(x, y);

        Log.d(className, String.format("Created GisPointFromMop = %s.", gisPointFromMop));

        return gisPointFromMop;
    }

    private boolean isMopWithinBuffer(Point mopCoordinates, Polygon buffer) {

        SpatialReference gpsSpatialReference = SpatialReference.create(SPATIAL_REFERENCE_EPSG_CODE);

        boolean mopWithinBuffer = OperatorWithin.local().execute(mopCoordinates, buffer, gpsSpatialReference, null);

        Log.d(className, String.format("Mop = %s is within buffer = %s : %s.", mopCoordinates, buffer, mopWithinBuffer));

        return mopWithinBuffer;
    }

    private boolean isMopInsideRadius(Point mopGisPoint, Point currentGpsPoint) {

        SpatialReference gpsSpatialReference = SpatialReference.create(SPATIAL_REFERENCE_EPSG_CODE);

        double distance = GeometryEngine.distance(mopGisPoint, currentGpsPoint, gpsSpatialReference);

        boolean mopInsideRadius = distance < RADIUS_DISTANCE_IN_WGS84_UNIT;

        Log.d(className, String.format("Mop = %s is within radius of currentGpsPoint = %s : %s.", mopGisPoint, currentGpsPoint, mopInsideRadius));

        return mopInsideRadius;
    }

    private Point createGisPointFromCurrentPosition() {

        Double currentX = CurrentPosition.getCurrentPositionInstance().getCurrentX();
        Double currentY = CurrentPosition.getCurrentPositionInstance().getCurrentY();

        Point gisPointFromCurrentPosition = setCoordinatesToPoint(currentX, currentY);

        Log.d(className, String.format("gisPointFromCurrentPosition = %s.", gisPointFromCurrentPosition));

        return gisPointFromCurrentPosition;
    }

    private Point setCoordinatesToPoint(Double x, Double y) {
        Point point = new Point();
        point.setXY(x, y);

        Log.d(className, String.format("Coordinate x=%f, y=%f have been set to point", x, y));

        return point;
    }

}
