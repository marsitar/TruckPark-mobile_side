package com.example.truckpark.view.login;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.truckpark.R;
import com.example.truckpark.util.KeycloakHelper;
import com.example.truckpark.view.main.MainMenu;

import org.apache.commons.io.FileUtils;
import org.jboss.aerogear.android.core.Callback;


public class LoginActivity extends AppCompatActivity {

    private String className = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
        }
    }

    @Override
    protected void onStart() {

        super.onStart();
        if (!KeycloakHelper.isConnected()) {

            KeycloakHelper.connect(LoginActivity.this, new Callback() {
                @Override
                public void onSuccess(Object o) {
                    Log.d(className, "Successful connection to Keycloak. MainMenu Activity is to be opened.");
                    Intent mainMenu = new Intent(getApplicationContext(), MainMenu.class);
                    startActivity(mainMenu);
                }

                @Override
                public void onFailure(Exception exception) {
                    Log.e(className, String.format("Connection to an authorization server failure. Reason: %s", exception.getMessage()));
                    Toast.makeText(getApplicationContext(), "Błąd połączenia z serwerem logującym , skontaktuj się z dostawcą oprogramowania", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            KeycloakHelper.refreshAccess();
            Log.i(className, "Keycloak access has been refreshed. MainMenu Activity is to be opened.");
            Intent mainMenu = new Intent(getApplicationContext(), MainMenu.class);
            startActivity(mainMenu);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(className, "LoginActivity is to be destroyed.");
        FileUtils.deleteQuietly(getApplicationContext().getCacheDir());
    }
}
