package com.example.truckpark.view.functionality.mop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.truckpark.R;

public class FoundMopData extends AppCompatActivity {
    public static final String MOPNAME = "mopname";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finded_mop_data);
        Intent intent =getIntent();
        String messageText = intent.getStringExtra(MOPNAME);
        TextView mopName = (TextView)findViewById(R.id.mopname);
        mopName.setText(messageText);



    }
}
