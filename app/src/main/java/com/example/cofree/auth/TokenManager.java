package com.example.cofree.auth;

import android.content.Context;
import android.content.SharedPreferences;

public class TokenManager {

    private static final String PREFS_NAME = "cofree_auth";

    private static final String KEY_TOKEN = "token";
    private static final String KEY_REFRESH_TOKEN = "refresh_token";
    private static final String KEY_USER = "user";

    private final SharedPreferences preferences;

    public TokenManager(Context context) {
        preferences = context.getSharedPreferences(
                PREFS_NAME,
                Context.MODE_PRIVATE
        );
    }

    public void saveTokens(String token, String refreshToken) {
        preferences.edit()
                .putString(KEY_TOKEN, token)
                .putString(KEY_REFRESH_TOKEN, refreshToken)
                .apply();
    }

    public String getToken() {
        return preferences.getString(KEY_TOKEN, null);
    }

    public String getRefreshToken() {
        return preferences.getString(KEY_REFRESH_TOKEN, null);
    }

    public void saveUser(String user) {
        preferences.edit()
                .putString(KEY_USER, user)
                .apply();
    }

    public String getUser() {
        return preferences.getString(KEY_USER, null);
    }

    public boolean hasToken() {
        return getToken() != null;
    }

    public void clear() {
        preferences.edit().clear().apply();
    }
}
