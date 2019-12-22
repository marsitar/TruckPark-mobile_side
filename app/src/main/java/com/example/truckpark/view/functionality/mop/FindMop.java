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
import com.example.truckpark.repository.CurrentMops;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FindMop extends AppCompatActivity {

    Long mopId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_mop);

        while (CurrentMops.getCurrentMopsInstance().getCurrentMopsList().size() == 0) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        List<Mop> mopList = CurrentMops.getCurrentMopsInstance().getCurrentMopsList()
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


        Spinner spinner = findViewById(R.id.spinner);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(
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

    public void onFoundMopDa(View view) {

        Intent foundMopDa = new Intent(this, FoundMopData.class);

        foundMopDa.putExtra(FoundMopData.MOPID, mopId.toString());

        startActivity(foundMopDa);
    }

}
