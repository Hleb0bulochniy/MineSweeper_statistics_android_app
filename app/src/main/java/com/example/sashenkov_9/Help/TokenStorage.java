package com.example.sashenkov_9.Help;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

public final class TokenStorage {

    private static final String PREFS_NAME = "auth_prefs";
    private static final String KEY_ACCESS = "access_token";
    private static final String KEY_REFRESH = "refresh_token";
    private static final String KEY_USERNAME = "username";

    private TokenStorage() {}

    private static SharedPreferences prefs(Context context) {
        try {
            Context appCtx = context.getApplicationContext();

            MasterKey masterKey = new MasterKey.Builder(appCtx)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build();

            return EncryptedSharedPreferences.create(
                    appCtx,
                    PREFS_NAME,
                    masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (Exception e) {
            return context.getApplicationContext()
                    .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        }
    }

    public static void saveTokens(Context context, String access, String refresh, String username) {
        prefs(context).edit()
                .putString(KEY_ACCESS, access)
                .putString(KEY_REFRESH, refresh)
                .putString(KEY_USERNAME, username)
                .apply();
    }

    public static String getAccessToken(Context context) {
        return prefs(context).getString(KEY_ACCESS, null);
    }

    public static String getRefreshToken(Context context) {
        return prefs(context).getString(KEY_REFRESH, null);
    }

    public static String getUsername(Context context) {
        return prefs(context).getString(KEY_USERNAME, null);
    }

    public static void clear(Context context) {
        prefs(context).edit().clear().apply();
    }
}