package com.example.sashenkov_9.Help;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import com.example.sashenkov_9.DTO.MapSaveListModelDTO;
import com.google.gson.Gson;

public class MapsStorage {
    private static final String PREFS_NAME = "maps_prefs";
    private static final String KEY_MAP_SAVE_LIST = "map_save_list_json";

    private MapsStorage() {}

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

    public static void save(Context context, MapSaveListModelDTO dto) {
        String json = new Gson().toJson(dto);
        prefs(context).edit().putString(KEY_MAP_SAVE_LIST, json).apply();
    }

    public static MapSaveListModelDTO get(Context context) {
        String json = prefs(context).getString(KEY_MAP_SAVE_LIST, null);
        if (json == null || json.trim().isEmpty()) return null;

        try {
            return new Gson().fromJson(json, MapSaveListModelDTO.class);
        } catch (Exception e) {
            return null;
        }
    }

    public static void clear(Context context) {
        prefs(context).edit().remove(KEY_MAP_SAVE_LIST).apply();
    }
}
