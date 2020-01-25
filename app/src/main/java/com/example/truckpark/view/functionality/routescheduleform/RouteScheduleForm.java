package com.example.truckpark.view.functionality.routescheduleform;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.truckpark.R;
import com.example.truckpark.service.routeschedule.RouteScheduleService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RouteScheduleForm extends AppCompatActivity {

    private AutoCompleteTextView inputText;
    private ImageButton addButton;
    private LinearLayout rowContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routeschedule);

        findViewsByIdsAndSetFieldsAdapters();

        addButton.setOnClickListener(view -> {

            LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            final View addView = layoutInflater.inflate(R.layout.routeschedule_row, null);
            AutoCompleteTextView nextRow = addView.findViewById(R.id.next_row);
            nextRow.setText(inputText.getText().toString());

            inputText.setText(null);

            ImageButton buttonRemove = addView.findViewById(R.id.remove_row);
            final View.OnClickListener thisListener = view1 -> ((LinearLayout) addView.getParent()).removeView(addView);
            buttonRemove.setOnClickListener(thisListener);

            rowContainer.addView(addView);
        });
    }

    private void findViewsByIdsAndSetFieldsAdapters() {
        inputText = findViewById(R.id.input_text);
        addButton = findViewById(R.id.add);
        rowContainer = findViewById(R.id.row_container);
    }

    public void saveRouteScheduleData(View view) {

        final List<String> allNotNullPlaces = IntStream.range(0, rowContainer.getChildCount())
                .boxed()
                .map(rowContainer::getChildAt)
                .map(particularView -> (AutoCompleteTextView) particularView.findViewById(R.id.next_row))
                .map(rowTextView -> rowTextView.getText().toString())
                .filter(value -> !value.isEmpty())
                .collect(Collectors.toList());

        RouteScheduleService routeScheduleService = new RouteScheduleService(getApplicationContext());
        routeScheduleService.saveRouteScheduler(allNotNullPlaces);

    }
}
