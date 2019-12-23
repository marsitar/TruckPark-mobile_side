package com.example.truckpark.view.login;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.truckpark.view.main.MainMenu;
import com.example.truckpark.R;


public class LoginActivity extends Activity {

    private String className = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
        }
    }

    public void onLog(View view) {
        TextView textViewLog = findViewById(R.id.login);
        TextView textViewPass = findViewById(R.id.password);
        String login = "m";
        String pass = "m";
        if (textViewLog.getText().toString().equals(login) && textViewPass.getText().toString().equals(pass)) {
            Intent mainMenu = new Intent(this, MainMenu.class);
            Log.i(className, String.format("User- login:%s,pass:%s has been logged in.",login,pass));
            startActivity(mainMenu);
        } else {
            String text = String.format("logowanie z nastepujacymi poswiadczeniami: user-%s, password-%s nieudalo się, wprowadź poprawne poswiadczenia", login, pass);
            Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
            toast.show();
            Log.w(className, "Bad logging attempt.");
        }
    }
}
