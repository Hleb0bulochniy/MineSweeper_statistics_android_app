package com.example.sashenkov_9.Help;

import android.content.Context;
import android.util.Log;

import com.example.sashenkov_9.DTO.LoginDTO;
import com.example.sashenkov_9.DTO.RegistrationDTO;
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

public class AuthClass {
    public AuthClass(Context context) {
        this.appContext = context.getApplicationContext();
    }
    private final Context appContext;
    private static final String TAG = "AuthService";
    private static final String BASE_URL = "http://10.0.2.2:5161";
    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();

    public void register(String userName, String email, String password1, String password2, RegisterCallback callback) {

        RegistrationDTO model = new RegistrationDTO(userName, email, password1, password2);

        String jsonData = gson.toJson(model);
        Log.d(TAG, "=== START REGISTER ===");
        Log.d(TAG, "Request URL: " + BASE_URL + "/api/v1/auth/UserRegistration");
        Log.d(TAG, "Request JSON: " + jsonData);

        MediaType JSON = MediaType.get("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(jsonData, JSON);

        Request request = new Request.Builder()
                .url(BASE_URL + "/api/v1/auth/UserRegistration")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure: request failed");
                Log.e(TAG, "Exception message: " + e.getMessage(), e);
                Log.d(TAG, "=== END REGISTER (FAILURE) ===");
                callback.onError(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "onResponse called");
                Log.d(TAG, "HTTP code: " + response.code());
                Log.d(TAG, "isSuccessful: " + response.isSuccessful());

                String responseBody = response.body() != null ? response.body().string() : "";
                Log.d(TAG, "Response body: " + responseBody);

                if (!response.isSuccessful()) {
                    Log.e(TAG, "Request NOT successful. Code: " +
                            response.code() + " Message: " + response.message());
                    callback.onError(responseBody);
                } else {
                    Log.d(TAG, "Request successful");
                    callback.onResult(responseBody);
                }

                Log.d(TAG, "=== END REGISTER (RESPONSE) ===");
            }
        });
    }

    public void login(String userName, String password, LoginCallback callback) {

        LoginDTO model = new LoginDTO(userName, password);

        String jsonData = gson.toJson(model);

        MediaType JSON = MediaType.get("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(jsonData, JSON);

        Request request = new Request.Builder()
                .url(BASE_URL + "/api/v1/auth/UserLogin")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "Login failed", e);
                callback.onError(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "onResponse called");
                Log.d(TAG, "HTTP code: " + response.code());
                Log.d(TAG, "isSuccessful: " + response.isSuccessful());

                String responseBody = response.body() != null ? response.body().string() : "";
                Log.d(TAG, "Response body: " + responseBody);

                if (!response.isSuccessful()) {
                    Log.e(TAG, "Request NOT successful. Code: " +
                            response.code() + " Message: " + response.message());
                    callback.onError(responseBody);
                } else {
                    Log.d(TAG, "Request successful");
                    TokenResponseDTO tokenResponse =
                            gson.fromJson(responseBody, TokenResponseDTO.class);
                    TokenStorage.saveTokens(
                            appContext,
                            tokenResponse.getAccess_token(),
                            tokenResponse.getRefresh_token(),
                            tokenResponse.getUsername()
                    );

                    callback.onTokenRecieved(tokenResponse.getAccess_token());
                }

                Log.d(TAG, "=== END LOGIN (RESPONSE) ===");
            }
        });
    }
}
