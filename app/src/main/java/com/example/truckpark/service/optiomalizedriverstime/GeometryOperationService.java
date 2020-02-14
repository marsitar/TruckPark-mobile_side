package com.example.truckpark.service.optiomalizedriverstime;

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

    public List<Mop> getPotentialStopMops(Polygon polygon) {

        List<Mop> mopsFormRepository = getMopsFromRepository();
        Point currentGpsArcgisPoint = createArcGisPointFromCurrentPosition();

        List<Mop> potentialStopMops = mopsFormRepository.stream()
                .filter(mop -> {
                    Point mopArcgisPoint = createArcGisPointFromMop(mop);
                    return isMopWithinBuffer(mopArcgisPoint, polygon);
                })
                .filter(mop -> {
                    Point mopArcgisPoint = createArcGisPointFromMop(mop);
                    return isMopInsideRadius(mopArcgisPoint, currentGpsArcgisPoint);
                })
                .collect(Collectors.toList());


        return potentialStopMops;
    }

    private List<Mop> getMopsFromRepository() {

        DataGetter<List<Mop>> mopsDataManagement = new MopsDataManagement();

        return mopsDataManagement.getData();
    }

    private Point createArcGisPointFromMop(Mop mop) {

        Double x = mop.getCoordinate().getX();
        Double y = mop.getCoordinate().getY();

        return setCoordinatesToPoint(x, y);
    }

    private boolean isMopWithinBuffer(Point mopCoordinates, Polygon buffer) {

        SpatialReference gpsSpatialReference = SpatialReference.create(4326);

        return OperatorWithin.local().execute(mopCoordinates, buffer, gpsSpatialReference, null);
    }

    private boolean isMopInsideRadius(Point mopArcgisPoint, Point currentGpsPoint) {

        double distanceInMeters = 50000;
        SpatialReference gpsSpatialReference = SpatialReference.create(4326);

        double distance = GeometryEngine.distance(mopArcgisPoint, currentGpsPoint, gpsSpatialReference);

        return distance > distanceInMeters;
    }

    private Point createArcGisPointFromCurrentPosition() {

        Double currentX = CurrentPosition.getCurrentPositionInstance().getCurrentX();
        Double currentY = CurrentPosition.getCurrentPositionInstance().getCurrentY();

        return setCoordinatesToPoint(currentX, currentY);
    }

    private Point setCoordinatesToPoint(Double x, Double y) {
        Point point = new Point();
        point.setXY(x, y);

        return point;
    }

}
