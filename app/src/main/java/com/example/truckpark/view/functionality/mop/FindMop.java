package com.example.truckpark.view.functionality.mop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.truckpark.R;

public class FindMop extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_mop);


        // Get reference of widgets from XML layout
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        // Initializing a String Array
        String[] mops = new String[]{
        "Kleszczewko => Gdańsk",
        "Kleszczewko => Łódź",
        "Olsze => Gdańsk",
        "Olsze => Łódź",
        "Gajewo => Gdańsk",
        "Gajewo => Łódź",
        "Malankowo => Gdańsk",
        "Malankowo => Łódź"
        };

        // Initializing an ArrayAdapter
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.search_spinner_item,mops
        );
        spinnerArrayAdapter.setDropDownViewResource(R.layout.search_spinner_item);
        spinner.setAdapter(spinnerArrayAdapter);
    }

    public void onFindedMopDa(View view){
        Intent FindedMopDa = new Intent(this, FoundMopData.class);
        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        String choosenMop = spinner.getSelectedItem().toString();
        FindedMopDa.putExtra(FoundMopData.MOPNAME,choosenMop);
        startActivity(FindedMopDa);
    }
}
