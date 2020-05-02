package com.example.truckpark.view.login;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.truckpark.R;
import com.example.truckpark.view.main.MainMenu;

import org.jboss.aerogear.android.authorization.AuthorizationManager;
import org.jboss.aerogear.android.authorization.AuthzModule;
import org.jboss.aerogear.android.authorization.oauth2.OAuth2AuthorizationConfiguration;
import org.jboss.aerogear.android.core.Callback;

import java.net.URL;


public class LoginActivity extends Activity {

    private String className = this.getClass().getSimpleName();
    private AuthzModule authzModule;

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
        this.authz();
    }

    private void authz() {
        try {

            authzModule = AuthorizationManager.config("GoogleDriveAuthz", OAuth2AuthorizationConfiguration.class)
                    .setBaseURL(new URL("http://192.168.0.20:8091/auth"))
                    .setAuthzEndpoint("/realms/truckpark/protocol/openid-connect/auth")
                    .setAccessTokenEndpoint("/realms/truckpark/protocol/openid-connect/token")
                    .setAccountId("keycloak-token")
                    .setClientId("truckparkclientfrontend")
                    .setClientSecret("")
                    .setRedirectURL("com.example.truckpark.view.main.MainMenu")
                    .addAdditionalAuthorizationParam((Pair.create("access_type", "offline")))
                    .asModule();

            authzModule.requestAccess(this, new Callback<String>() {
                @Override
                public void onSuccess(String o) {
                    Log.d("TOKEN ++ ", o);
                    Toast.makeText(getApplicationContext(), o, Toast.LENGTH_LONG).show();

                }

                @Override
                public void onFailure(Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
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
