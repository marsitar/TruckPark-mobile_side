package com.example.truckpark.util;

import android.app.Activity;
import android.util.Log;

import org.jboss.aerogear.android.authorization.AuthorizationManager;
import org.jboss.aerogear.android.authorization.AuthzModule;
import org.jboss.aerogear.android.authorization.oauth2.OAuth2AuthorizationConfiguration;
import org.jboss.aerogear.android.authorization.oauth2.OAuthWebViewDialog;
import org.jboss.aerogear.android.core.Callback;

import java.net.MalformedURLException;
import java.net.URL;

public class KeycloakHelper {

    static {
        try {
            AuthorizationManager.config(KeycloakConst.AUTHZ_MODULE_NAME, OAuth2AuthorizationConfiguration.class)
                    .setBaseURL(new URL(KeycloakConst.AUTHZ_URL))
                    .setAuthzEndpoint(KeycloakConst.AUTHZ_ENDPOINT)
                    .setAccessTokenEndpoint(KeycloakConst.AUTHZ_ACCESS_TOKEN_ENDPOINT)
                    .setRefreshEndpoint(KeycloakConst.AUTHZ_REFRESH_TOKEN_ENDPOINT)
                    .setAccountId(KeycloakConst.AUTHZ_ACCOUNT_ID)
                    .setClientId(KeycloakConst.AUTHZ_CLIENT_ID)
                    .setRedirectURL(KeycloakConst.AUTHZ_REDIRECT_URL)
                    .asModule();
            Log.i(KeycloakHelper.class.getName(), String.format("%s has been configured in AuthorizationManager.", KeycloakConst.AUTHZ_MODULE_NAME));
        } catch (MalformedURLException e) {
            Log.e(KeycloakHelper.class.getName(), String.format("There is a problem with MalformedURLException. Reason url: %s", KeycloakConst.AUTHZ_URL));
        }
    }

    public static void connect(final Activity activity, final Callback callback) {
        try {
            final AuthzModule authzModule = AuthorizationManager.getModule(KeycloakConst.AUTHZ_MODULE_NAME);

            authzModule.requestAccess(activity, new Callback<String>() {
                @SuppressWarnings("unchecked")
                @Override
                public void onSuccess(String s) {
                    Log.d(KeycloakHelper.class.getName(), "Successful access.");
                    callback.onSuccess(s);
                }

                @Override
                public void onFailure(Exception e) {
                    Log.e(KeycloakHelper.class.getName(), "Failure access.");
                    if (!e.getMessage().matches(OAuthWebViewDialog.OAuthReceiver.DISMISS_ERROR)) {
                        authzModule.deleteAccount();
                    }
                    callback.onFailure(e);
                }
            });
        } catch (Exception e) {
            Log.wtf(KeycloakHelper.class.getName(), String.format("There is a problem with connection to %s. Reason - %s", KeycloakConst.AUTHZ_MODULE_NAME, e.getMessage()));
        }
    }

    public static boolean isConnected() {
        boolean isKeycloakConnected = AuthorizationManager.getModule(KeycloakConst.AUTHZ_MODULE_NAME).isAuthorized();
        Log.d(KeycloakHelper.class.getName(), String.format("Check whether keycloak is connected. Result - %b", isKeycloakConnected));
        return isKeycloakConnected;
    }

    public static void refreshAccess() {
        AuthorizationManager.getModule(KeycloakConst.AUTHZ_MODULE_NAME).refreshAccess();
        Log.d(KeycloakHelper.class.getName(), "Access to Keycloak has been refreshed.");
    }
}
