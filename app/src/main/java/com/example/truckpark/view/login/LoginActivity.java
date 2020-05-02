package com.example.truckpark.view.login;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.truckpark.R;
import com.example.truckpark.view.main.MainMenu;

import org.jboss.aerogear.android.authorization.AuthorizationManager;
import org.jboss.aerogear.android.authorization.AuthzModule;
import org.jboss.aerogear.android.authorization.oauth2.OAuth2AuthorizationConfiguration;
import org.jboss.aerogear.android.core.Callback;

import java.net.URL;
import java.util.Collections;

import static com.example.truckpark.view.login.Constants.AUTHZ_ACCOUNT_ID;
import static com.example.truckpark.view.login.Constants.AUTHZ_CLIENT_ID;
import static com.example.truckpark.view.login.Constants.AUTHZ_CLIENT_SECRET;
import static com.example.truckpark.view.login.Constants.AUTHZ_ENDPOINT;
import static com.example.truckpark.view.login.Constants.AUTHZ_REDIRECT_URL;
import static com.example.truckpark.view.login.Constants.AUTHZ_TOKEN_ENDPOINT;
import static com.example.truckpark.view.login.Constants.AUTHZ_URL;


public class LoginActivity extends AppCompatActivity {

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
                    .setBaseURL(new URL(AUTHZ_URL))
                    .setAuthzEndpoint(AUTHZ_ENDPOINT)
                    .setAccessTokenEndpoint(AUTHZ_TOKEN_ENDPOINT)
                    .setAccountId(AUTHZ_ACCOUNT_ID)
                    .setClientId(AUTHZ_CLIENT_ID)
                    .setClientSecret(AUTHZ_CLIENT_SECRET)
                    .setRedirectURL(AUTHZ_REDIRECT_URL)
                    .setScopes(Collections.singletonList("https://www.googleapis.com/auth/drive"))
                    .addAdditionalAuthorizationParam((Pair.create("access_type", "offline")))
                    .asModule();

            authzModule.requestAccess(this, new Callback<String>() {
                @Override
                public void onSuccess(String o) {
                    Log.d("TOKEN ++ ", o);
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
