package com.example.truckpark;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class NavigationMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_menu);
    }
    public void onMapsActivityNavigatio(View view) {
        Intent MapsActivityNavigatio = new Intent(this,MapsActivityNavigation.class);
        TextView textViewPlace1 =(TextView)findViewById(R.id.place1);
        TextView textViewPlace2 =(TextView)findViewById(R.id.place2);
        String src=textViewPlace1.getText().toString();
        String dst=textViewPlace2.getText().toString();
        MapsActivityNavigatio.putExtra(MapsActivityNavigation.SRC,src);
        MapsActivityNavigatio.putExtra(MapsActivityNavigation.DST,dst);
        startActivity(MapsActivityNavigatio);
    }


}
