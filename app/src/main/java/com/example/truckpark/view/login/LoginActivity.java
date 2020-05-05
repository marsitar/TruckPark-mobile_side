package com.example.truckpark.view.login;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.truckpark.R;
import com.example.truckpark.properties.PropertyManager;
import com.example.truckpark.view.main.MainMenu;

import org.jboss.aerogear.android.authorization.AuthorizationManager;
import org.jboss.aerogear.android.authorization.AuthzModule;
import org.jboss.aerogear.android.authorization.oauth2.OAuth2AuthorizationConfiguration;
import org.jboss.aerogear.android.core.Callback;

import java.net.MalformedURLException;
import java.net.URL;


public class LoginActivity extends AppCompatActivity {

    private static final String PROPERTY_FILE_NAME = "keycloak.properties";
    public static final String AUTHZ_MODULE_NAME = "KeycloakAuthz";
    private static AuthzModule authzModule;
    public static String TOKEN;
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
        this.authorizeAndAuthenticate();
    }

    private void authorizeAndAuthenticate() {
        try {
            LoginActivity.buildAuthzModule(this);
            authzModule.requestAccess(this, new Callback<String>() {
                @Override
                public void onSuccess(String o) {
                    TOKEN = o;
                    Log.d("TOKEN ++ ", o);
                    Intent mainMenu = new Intent(getApplicationContext(), MainMenu.class);
                    startActivity(mainMenu);
                }

                @Override
                public void onFailure(Exception exception) {
                    Log.e(className, String.format("Connection to an authorization server failure. Reason: %s", exception.getMessage()));
                    Toast.makeText(getApplicationContext(), "Błąd połączenia z serwerem logującym , skontaktuj się z dostawcą oprogramowania", Toast.LENGTH_LONG).show();
                }
            });


        } catch (Exception exception) {
            Log.wtf(className, String.format("Creating a connection to an authorization server failure. Reason: %s", exception.getMessage()));
            Toast.makeText(getApplicationContext(), "Błąd tworzenia połączenia z serwerem logującym , skontaktuj się z dostawcą oprogramowania", Toast.LENGTH_LONG).show();
        }
    }

    private static void buildAuthzModule(Context context) throws MalformedURLException{

        PropertyManager propertyManager = new PropertyManager(PROPERTY_FILE_NAME);
        final String AUTHZ_URL = propertyManager.getProperty("AUTHZ_URL", context);
        final String AUTHZ_ENDPOINT = propertyManager.getProperty("AUTHZ_ENDPOINT", context);
        final String AUTHZ_TOKEN_ENDPOINT = propertyManager.getProperty("AUTHZ_TOKEN_ENDPOINT", context);
        final String AUTHZ_ACCOUNT_ID = propertyManager.getProperty("AUTHZ_ACCOUNT_ID", context);
        final String AUTHZ_CLIENT_ID = propertyManager.getProperty("AUTHZ_CLIENT_ID", context);
        final String AUTHZ_CLIENT_SECRET = propertyManager.getProperty("AUTHZ_CLIENT_SECRET", context);
        final String AUTHZ_REDIRECT_URL = propertyManager.getProperty("AUTHZ_REDIRECT_URL", context);

        authzModule = AuthorizationManager.config(AUTHZ_MODULE_NAME, OAuth2AuthorizationConfiguration.class)
                .setBaseURL(new URL(AUTHZ_URL))
                .setAuthzEndpoint(AUTHZ_ENDPOINT)
                .setAccessTokenEndpoint(AUTHZ_TOKEN_ENDPOINT)
                .setAccountId(AUTHZ_ACCOUNT_ID)
                .setClientId(AUTHZ_CLIENT_ID)
                .setClientSecret(AUTHZ_CLIENT_SECRET)
                .setRedirectURL(AUTHZ_REDIRECT_URL)
                .addAdditionalAuthorizationParam((Pair.create("access_type", "offline")))
                .asModule();
    }
}
