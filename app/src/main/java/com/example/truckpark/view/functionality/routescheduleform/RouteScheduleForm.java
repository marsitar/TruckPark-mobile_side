package com.example.truckpark.view.functionality.routescheduleform;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.truckpark.R;
import com.example.truckpark.domain.json.googledirectionsapi.GoogleRoute;
import com.example.truckpark.service.route.GoogleRouteService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RouteScheduleForm extends AppCompatActivity {

    public List<Integer> placeIds = new ArrayList<>(Arrays.asList(1000029, 1000033, 1000027, 1000028));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routeschedule);
    }

    public void saveRouteScheduleData(View view){

        StringBuilder stringBuilder= new StringBuilder();

        GoogleRouteService googleRouteService= new GoogleRouteService(getApplicationContext());

        List<String> allNotNullPlaces= placeIds.stream()
                .map(id ->(TextView) findViewById(id))
                .map(TextView::getText)
                .map(CharSequence::toString)
                .collect(Collectors.toList());

        final List<String[]> result = IntStream.range(0, allNotNullPlaces.size() - 1)
                .mapToObj(i -> new String[]{allNotNullPlaces.get(i), allNotNullPlaces.get(i+1)})
                .collect(Collectors.toList());

        final List<GoogleRoute> result2 = IntStream.range(0, allNotNullPlaces.size() - 1)
                .mapToObj(i -> new String[]{allNotNullPlaces.get(i), allNotNullPlaces.get(i+1)})
                .map(originDestinationArray -> googleRouteService.getGoogleRoute(originDestinationArray[0],originDestinationArray[1]))
                .collect(Collectors.toList());

    }
}
