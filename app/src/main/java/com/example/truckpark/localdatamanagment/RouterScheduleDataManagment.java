package com.example.truckpark.localdatamanagment;

import com.example.truckpark.domain.entity.RouteSchedule;
import com.example.truckpark.repository.CurrentRouteSchedule;

public class RouterScheduleDataManagment implements DataGetter<RouteSchedule>, DataSaver<RouteSchedule> {

    @Override
    public RouteSchedule getData() {
        return CurrentRouteSchedule.getCurrentRouteSchedule().getRouteSchedule();
    }

    @Override
    public void save(RouteSchedule routeSchedule) {
        CurrentRouteSchedule.getCurrentRouteSchedule().setRouteSchedule(routeSchedule);
    }
}
