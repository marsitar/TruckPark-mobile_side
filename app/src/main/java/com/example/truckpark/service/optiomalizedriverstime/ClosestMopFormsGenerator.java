package com.example.truckpark.service.optiomalizedriverstime;

import android.content.Context;
import android.util.Log;

import com.example.truckpark.domain.entity.MopForm;
import com.example.truckpark.domain.json.googledirectionsapi.GoogleRoute;
import com.example.truckpark.domain.json.mopapi.Mop;
import com.example.truckpark.repository.CurrentPosition;
import com.example.truckpark.service.route.GoogleRouteService;

import java.time.Duration;
import java.util.Comparator;
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

        Log.i(className, String.format("Generated ClosestMopForms from ClosestMopFormsGenerator: %s.", mopForms));

        return mopForms;
    }

    private List<GoogleRoute> generateGoogleRoutesFromExternalService(List<Mop> mops) {
        GoogleRouteService googleRouteService = new GoogleRouteService(this.context);

        String originCoordinates = prepareOriginCoordinatesAsString();
        List<String[]> originDestinationCoordinatesPairs = generateOriginDestinationCoordinatesPairsCollection(mops, originCoordinates);

        List<GoogleRoute> googleRoutes = googleRouteService.generateGoogleRouteListFromItineraryPointPairs(originDestinationCoordinatesPairs);

        Log.i(className, String.format("GoogleRoutes generated from external service: %s.", googleRoutes));

        return googleRoutes;
    }

    private List<MopForm> generateFinalMopFormsFromDataCollections(List<Mop> mops, List<GoogleRoute> generatedGoogleRouts) {

        List<Integer> distances = generateDistancesCollection(generatedGoogleRouts);
        List<Integer> durations = generateDurationsCollection(generatedGoogleRouts);
        List<String> destinationMopNames = generateDestinationMopNamesCollection(mops);
        List<Integer> freePlacesForTruck = generateFreePlacesForTruckCollection(mops);

        List<MopForm> mopForms = prepareSortedMopFormsCollection(distances, durations, destinationMopNames, freePlacesForTruck);

        Log.i(className, String.format("Generated MopForms: %s.", mopForms));

        return mopForms;
    }

    private List<String[]> generateOriginDestinationCoordinatesPairsCollection(List<Mop> mops, String originCoordinates) {

        List<String[]> originDestinationCoordinatesPairs = mops.stream()
                .map(mop -> String.format("%f,%f", mop.getCoordinate().getLat(), mop.getCoordinate().getLng()))
                .map(destinationCoordinates -> new String[]{originCoordinates, destinationCoordinates})
                .collect(Collectors.toList());

        Log.d(className, String.format("Distances generated from GoogleRoutes: %s.", originDestinationCoordinatesPairs));

        return originDestinationCoordinatesPairs;
    }

    private List<Integer> generateDistancesCollection(List<GoogleRoute> generatedGoogleRouts) {

        List<Integer> distances = generatedGoogleRouts.stream().map(GoogleRoute::getRoutes).map(routes ->
                routes.stream()
                        .findFirst()
                        .flatMap(route ->
                                route.getLegs().stream()
                                        .findFirst()
                                        .map(leg -> leg.getDistance().getValue())
                        ).orElse(1000000)
        ).collect(Collectors.toList());

        Log.d(className, String.format("Distances generated from GoogleRoutes: %s.", distances));

        return distances;
    }

    private List<Integer> generateFreePlacesForTruckCollection(List<Mop> mops) {

        List<Integer> freeTrucksPlaces = mops.stream()
                .map(mop -> mop.getTruckPlaces() - mop.getOccupiedTruckPlaces())
                .collect(Collectors.toList());

        Log.d(className, String.format("List of free truck places generated from list of Mops: %s.", freeTrucksPlaces));

        return freeTrucksPlaces;
    }

    private List<String> generateDestinationMopNamesCollection(List<Mop> mops) {

        List<String> destinationMopNames = mops.stream()
                .map(Mop::getIdentificationName)
                .collect(Collectors.toList());

        Log.d(className, String.format("Destinations generated from list of Mops: %s.", destinationMopNames));

        return destinationMopNames;
    }

    private List<Integer> generateDurationsCollection(List<GoogleRoute> generatedGoogleRouts) {

        List<Integer> generatedDurations = generatedGoogleRouts.stream().map(GoogleRoute::getRoutes).map(routes ->
                routes.stream()
                        .findFirst()
                        .map(route ->
                                route.getLegs().stream()
                                        .findFirst()
                                        .map(leg -> leg.getDuration().getValue())
                        )
                        .orElse(Optional.empty())
                        .orElse(100000)
        ).collect(Collectors.toList());

        Log.d(className, String.format("Durations generated from GoogleRoutes: %s.", generatedDurations));

        return generatedDurations;
    }

    private String prepareOriginCoordinatesAsString() {
        Double currentX = CurrentPosition.getCurrentPositionInstance().getCurrentLat();
        Double currentY = CurrentPosition.getCurrentPositionInstance().getCurrentLng();

        String originCoordinatesAsString = String.format("%f,%f", currentX, currentY);

        Log.d(className, String.format("originCoordinatesAsString has been generated from current position stored in repository. Generated originCoordinates: %s.", originCoordinatesAsString));

        return originCoordinatesAsString;
    }

    private List<MopForm> prepareSortedMopFormsCollection(List<Integer> distances, List<Integer> durations, List<String> destinationMopNames, List<Integer> freePlacesForTruck) {

        List<MopForm> mopFormsCollection = IntStream.range(0, distances.size() - 1)
                .mapToObj(i -> {
                    MopForm mopForm = new MopForm();
                    mopForm.setLeftTime(Duration.ofSeconds(durations.get(i)));
                    mopForm.setLeftKilometers(distances.get(i));
                    mopForm.setMopName(destinationMopNames.get(i));
                    mopForm.setFreePlacesForTrucks(freePlacesForTruck.get(i));
                    return mopForm;
                })
                .sorted(Comparator.comparing(MopForm::getLeftTime))
                .collect(Collectors.toList());

        Log.i(className, String.format("Mops prepared from given collections: %s.", mopFormsCollection));

        return mopFormsCollection;
    }
}
