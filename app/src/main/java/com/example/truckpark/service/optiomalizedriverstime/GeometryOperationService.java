package com.example.truckpark.service.optiomalizedriverstime;

import com.esri.core.geometry.OperatorWithin;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.Polygon;
import com.esri.core.geometry.SpatialReference;
import com.example.truckpark.domain.json.mopapi.Mop;
import com.example.truckpark.localdatamanagment.DataGetter;
import com.example.truckpark.localdatamanagment.MopsDataManagement;

import java.util.List;
import java.util.stream.Collectors;

public class GeometryOperationService {

    public List<Mop> getPotentialStopMops(Polygon polygon) {

        List<Mop> mopsFormRepository = getMopsFromRepository();

        List<Mop> potentialStopMops = mopsFormRepository.stream()
                .filter(mop -> {
                    Point mopPoint = createArcGisPoint(mop);
                    return isMopWithinBuffer(mopPoint, polygon);
                })
                .collect(Collectors.toList());

        return potentialStopMops;
    }

    private List<Mop> getMopsFromRepository() {

        DataGetter<List<Mop>> mopsDataManagement = new MopsDataManagement();

        return mopsDataManagement.getData();
    }

    private Point createArcGisPoint(Mop mop) {

        Double x = mop.getCoordinate().getX();
        Double y = mop.getCoordinate().getY();

        Point point = new Point();
        point.setXY(x,y);

        return point;
    }

    private boolean isMopWithinBuffer(Point mopCoordinates, Polygon buffer) {

        SpatialReference gpsSpatialReference = SpatialReference.create(4326);

        return OperatorWithin.local().execute(mopCoordinates, buffer, gpsSpatialReference, null);
    }

}
