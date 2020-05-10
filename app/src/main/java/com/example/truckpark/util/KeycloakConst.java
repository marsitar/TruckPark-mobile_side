package com.example.truckpark.util;

public interface KeycloakConst {
    String AUTHZ_URL = "http://192.168.0.20:8091/auth";
    String AUTHZ_ENDPOINT = "/realms/truckpark/protocol/openid-connect/auth";
    String AUTHZ_ACCESS_TOKEN_ENDPOINT = "/realms/truckpark/protocol/openid-connect/token";
    String AUTHZ_REFRESH_TOKEN_ENDPOINT = "/realms/truckpark/protocol/openid-connect/token";
    String AUTHZ_ACCOUNT_ID = "keycloak-token";
    String AUTHZ_CLIENT_ID = "truckparkclientfrontend";
    String AUTHZ_CLIENT_SECRET = "";
    String AUTHZ_REDIRECT_URL = "truckpark://authorizationpage";
    String AUTHZ_MODULE_NAME = "KeycloakAuthz";
}