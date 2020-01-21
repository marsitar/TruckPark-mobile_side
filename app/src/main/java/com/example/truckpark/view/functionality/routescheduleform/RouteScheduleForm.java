package com.example.truckpark.view.functionality.routescheduleform;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.truckpark.R;

public class RouteScheduleForm extends AppCompatActivity {

//    public List<Integer> placeIds = new ArrayList<>(Arrays.asList(1000029, 1000033, 1000027, 1000028));

    AutoCompleteTextView inputText;
    Button addButton;
    LinearLayout rowContainer;

    private static final String[] PLACES = new String[] {
            "place1", "place2", "place3", "place4", "place5",
            "place6", "place7", "place8", "place9", "place10"
    };
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routeschedule);

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, PLACES);

        inputText = findViewById(R.id.input_text);
        inputText.setAdapter(adapter);

        addButton = findViewById(R.id.add);
        rowContainer = findViewById(R.id.row_container);

        addButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View addView = layoutInflater.inflate(R.layout.routeschedule_row, null);
                AutoCompleteTextView nextRow = addView.findViewById(R.id.next_row);
                nextRow.setAdapter(adapter);
                nextRow.setText(inputText.getText().toString());
                Button buttonRemove = addView.findViewById(R.id.remove_row);

                final View.OnClickListener thisListener = new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
//                        info.append("thisListener called:\t" + this + "\n");
//                        info.append("Remove addView: " + addView + "\n\n");
                        ((LinearLayout)addView.getParent()).removeView(addView);

                        listAllAddView();
                    }
                };

                buttonRemove.setOnClickListener(thisListener);
                rowContainer.addView(addView);

//                info.append(
//                        "thisListener:\t" + thisListener + "\n"
//                                + "addView:\t" + addView + "\n\n"
//                );

                listAllAddView();
            }
        });
    }

    private void listAllAddView(){
        int childCount = rowContainer.getChildCount();
        for(int i=0; i<childCount; i++){
            View thisChild = rowContainer.getChildAt(i);
            AutoCompleteTextView childTextView = thisChild.findViewById(R.id.next_row);
            String childTextViewValue = childTextView.getText().toString();
        }
    }

    public void saveRouteScheduleData(View view){

//        StringBuilder stringBuilder= new StringBuilder();
//
//        GoogleRouteService googleRouteService= new GoogleRouteService(getApplicationContext());
//
//        List<String> allNotNullPlaces= placeIds.stream()
//                .map(id ->(TextView) findViewById(id))
//                .map(TextView::getText)
//                .map(CharSequence::toString)
//                .filter(value -> !value.isEmpty())
//                .collect(Collectors.toList());
//
//        final List<GoogleRoute> googleRouteList = IntStream.range(0, allNotNullPlaces.size() - 1)
//                .mapToObj(i -> new String[]{allNotNullPlaces.get(i), allNotNullPlaces.get(i+1)})
//                .map(originDestinationArray -> googleRouteService.getGoogleRoute(originDestinationArray[0],originDestinationArray[1]))
//                .collect(Collectors.toList());
//


    }
}
