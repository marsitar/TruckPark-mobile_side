package com.example.truckpark.view.functionality.mop;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.truckpark.R;
import com.example.truckpark.domain.json.mopapi.Mop;
import com.example.truckpark.service.mopdata.RequestMopDataService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FindMop extends AppCompatActivity {

    Long mopId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_mop);

        //THINK ABOUT IT LATER,async attitute would be better here in future
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ///////////////////////////////////////////////////////////////////////////////////////////

        RequestMopDataService requestMopDataService = new RequestMopDataService(this);

        List<Mop> mopList = requestMopDataService.getAllMopsData()
                .stream()
                .sorted(Comparator.comparing(Mop::getPlace))
                .collect(Collectors.toList());

        String[] mopsLabels = mopList
                .stream()
                .map(mop -> String.format("%s => %s", mop.getPlace(), mop.getExtendedMopData().getDirection()))
                .toArray(String[]::new);

        final Long[] mopsIds = mopList
                .stream()
                .map(Mop::getId)
                .toArray(Long[]::new);


        // Get reference of widgets from XML layout
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        // Initializing an ArrayAdapter
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, R.layout.search_spinner_item, mopsLabels
        );

        spinnerArrayAdapter.setDropDownViewResource(R.layout.search_spinner_item);
        spinner.setAdapter(spinnerArrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mopId = mopsIds[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void onFindedMopDa(View view) {
        Intent FindedMopDa = new Intent(this, FoundMopData.class);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        String choosenMop = spinner.getSelectedItem().toString();
        FindedMopDa.putExtra(FoundMopData.MOPNAME, choosenMop);
        FindedMopDa.putExtra(FoundMopData.MOPID, mopId.toString());
        startActivity(FindedMopDa);
    }
}
