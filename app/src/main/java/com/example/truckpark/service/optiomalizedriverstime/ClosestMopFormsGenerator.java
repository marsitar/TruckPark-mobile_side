package com.example.truckpark.service.optiomalizedriverstime;

import android.content.Context;

import com.example.truckpark.domain.entity.MopForm;
import com.example.truckpark.domain.json.googledirectionsapi.GoogleRoute;
import com.example.truckpark.domain.json.mopapi.Mop;
import com.example.truckpark.repository.CurrentPosition;
import com.example.truckpark.service.route.GoogleRouteService;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ClosestMopFormsGenerator {

    private final String className = this.getClass().getSimpleName();
    private Context context;

    public ClosestMopFormsGenerator(Context context) {
        this.context = context;
    }

    public List<MopForm> generateClosestMopFormsGenerator(List<Mop> mops) {

        List<GoogleRoute> generatedGoogleRouts = generateGoogleRoutesFromExternalService(mops);
        List<MopForm> mopForms = generateFinalMopFormsFromDataCollections(mops, generatedGoogleRouts);

        return mopForms;
    }

    private List<GoogleRoute> generateGoogleRoutesFromExternalService(List<Mop> mops) {
        GoogleRouteService googleRouteService = new GoogleRouteService(this.context);

        String originCoordinates = prepareOriginCoordinatesAsString();
        List<String[]> originDestinationCoordinatesPairs = generateOriginDestinationCoordinatesPairsCollection(mops, originCoordinates);

        return googleRouteService.generateGoogleRouteListFromItineraryPointPairs(originDestinationCoordinatesPairs);
    }

    private List<MopForm> generateFinalMopFormsFromDataCollections(List<Mop> mops, List<GoogleRoute> generatedGoogleRouts) {

        List<Integer> distances = generateDistancesCollection(generatedGoogleRouts);
        List<Integer> durations = generateDurationsCollection(generatedGoogleRouts);
        List<String> destinationMopNames = generateDestinationMopNamesCollection(mops);
        List<Integer> freePlacesForTruck = generateFreePlacesForTruckCollection(mops);

        List<MopForm> mopForms = perpareMopFormsCollection(distances, durations, destinationMopNames, freePlacesForTruck);

        return mopForms;
    }

    private List<String[]> generateOriginDestinationCoordinatesPairsCollection(List<Mop> mops, String originCoordinates) {
        return mops.stream()
                .map(mop -> String.format("%f,%f", mop.getCoordinate().getY(), mop.getCoordinate().getX()))
                .map(destinationCoordinates -> new String[]{originCoordinates, destinationCoordinates})
                .collect(Collectors.toList());
    }

    private List<Integer> generateDistancesCollection(List<GoogleRoute> generatedGoogleRouts) {
        return generatedGoogleRouts.stream().map(GoogleRoute::getRoutes).map(routes ->
                routes.stream()
                        .findFirst()
                        .map(route ->
                                route.getLegs().stream()
                                        .findFirst()
                                        .map(leg -> leg.getDistance().getValue())
                        )
                        .orElse(Optional.empty())
                        .orElse(null)
        ).collect(Collectors.toList());
    }

    private List<Integer> generateFreePlacesForTruckCollection(List<Mop> mops) {
        return mops.stream()
                .map(mop -> mop.getTruckPlaces() - mop.getOccupiedTruckPlaces())
                .collect(Collectors.toList());
    }

    private List<String> generateDestinationMopNamesCollection(List<Mop> mops) {
        return mops.stream()
                .map(Mop::getIdentificationName)
                .collect(Collectors.toList());
    }

    private List<Integer> generateDurationsCollection(List<GoogleRoute> generatedGoogleRouts) {
        return generatedGoogleRouts.stream().map(GoogleRoute::getRoutes).map(routes ->
                routes.stream()
                        .findFirst()
                        .map(route ->
                                route.getLegs().stream()
                                        .findFirst()
                                        .map(leg -> leg.getDuration().getValue())
                        )
                        .orElse(Optional.empty())
                        .orElse(null)
        ).collect(Collectors.toList());
    }

    private String prepareOriginCoordinatesAsString() {
        Double currentX = CurrentPosition.getCurrentPositionInstance().getCurrentX();
        Double currentY = CurrentPosition.getCurrentPositionInstance().getCurrentY();

        return String.format("%f,%f", currentY, currentX);
    }

    private List<MopForm> perpareMopFormsCollection(List<Integer> distances, List<Integer> durations, List<String> destinationMopNames, List<Integer> freePlacesForTruck) {
        return IntStream.range(0, distances.size() - 1)
                .mapToObj(i -> {
                    MopForm mopForm = new MopForm();
                    mopForm.setLeftTime(Duration.ofSeconds(durations.get(i)));
                    mopForm.setLeftKilometers(distances.get(i));
                    mopForm.setMopName(destinationMopNames.get(i));
                    mopForm.setFreePlacesForTrucks(freePlacesForTruck.get(i));
                    return mopForm;
                })
                .collect(Collectors.toList());
    }
}
