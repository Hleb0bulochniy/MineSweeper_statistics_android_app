package com.example.sashenkov_9.Help;

import android.content.Context;
import android.util.Log;

import com.example.sashenkov_9.DTO.LoginDTO;
import com.example.sashenkov_9.DTO.MapSaveListModelDTO;
import com.example.sashenkov_9.DTO.TokenResponseDTO;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MapsClass {
    public MapsClass(Context context) {
        this.appContext = context.getApplicationContext();
    }
    private final Context appContext;
    private static final String BASE_URL = "http://10.0.2.2:5261";
    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();

    public void GetMapsInfo(MapsCallback callback) {

        String access = TokenStorage.getAccessToken(appContext);

        Request request = new Request.Builder()
                .url(BASE_URL + "/api/v1/maps-in-users/SaveList")
                .get()
                .addHeader("Authorization", "Bearer " + access)
                .addHeader("Accept", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onError(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseBody = response.body() != null ? response.body().string() : "";
                if (!response.isSuccessful()) {
                    callback.onError(responseBody);
                }
                try {
                    MapSaveListModelDTO dto = gson.fromJson(responseBody, MapSaveListModelDTO.class);
                    if (dto != null) {
                        MapsStorage.save(appContext, dto);
                    }
                    callback.onMapsRecieved(responseBody);
                } catch (Exception ex) {
                    callback.onError("JSON parse error: " + ex.getMessage() + " body=" + responseBody);
                }
            }
        });
    }
}
