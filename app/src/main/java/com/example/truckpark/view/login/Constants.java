package com.example.truckpark.view.login;

public interface Constants {

    String AUTHZ_URL = "http://192.168.0.20:8091/auth";
    String AUTHZ_ENDPOINT = "/realms/truckpark/protocol/openid-connect/auth";
    String AUTHZ_TOKEN_ENDPOINT = "/realms/truckpark/protocol/openid-connect/token";
    String AUTHZ_ACCOUNT_ID = "keycloak-token";
    String AUTHZ_CLIENT_ID = "truckparkclientfrontend";
    String AUTHZ_CLIENT_SECRET = "";
//    String AUTHZ_REDIRECT_URL = "org.jboss.aerogear.android.cookbook.gdrive";
    String AUTHZ_REDIRECT_URL = "example://gizmos";

}
