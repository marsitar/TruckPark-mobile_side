package com.example.truckpark;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},100);
        }
    }

    public void onLog(View view){
        TextView textViewLog =(TextView)findViewById(R.id.login);
        TextView textViewPass =(TextView)findViewById(R.id.password);
        String login = "m";
        String pass = "m";
        if(textViewLog.getText().toString().equals(login) && textViewPass.getText().toString().equals(pass)){
            Intent mainMenu = new Intent(this,MainMenu.class);
            startActivity(mainMenu);
        } else {
            String text="logowanie nieudane: pass login marek:"+login+"\n, password marek1:"+pass;
            Toast toast = Toast.makeText(getApplicationContext(),text ,Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
