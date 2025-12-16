package com.example.sashenkov_9;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.constraintlayout.helper.widget.Flow;
import com.example.sashenkov_9.DTO.MapSaveListModelDTO;
import com.example.sashenkov_9.DTO.MapSaveModelDTO;
import com.example.sashenkov_9.Help.MapsCallback;
import com.example.sashenkov_9.Help.MapsClass;
import com.example.sashenkov_9.Help.MapsStorage;
import com.example.sashenkov_9.Help.TokenStorage;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Flow flow;
    ConstraintLayout root;
    MapsClass mapsClass;
    Button registrationActivity;
    Button loginActivity;
    Button accountExitButton;
    TextView usernameTextMain;
    private final ArrayList<Integer> mapButtonIds = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        root = findViewById(R.id.main);
        flow = findViewById(R.id.flow);

        mapsClass = new MapsClass(getApplicationContext());
        registrationActivity = findViewById(R.id.switch_activity_registration);
        loginActivity = findViewById(R.id.switch_activity_login);
        accountExitButton = findViewById(R.id.accountExitButton);
        usernameTextMain = findViewById(R.id.UsernameTextMain);

        registrationActivity.setOnClickListener(this::SetRegistrationAcitivty);
        loginActivity.setOnClickListener(this::SetLoginAcitivty);

        accountExitButton.setOnClickListener(v -> {
            TokenStorage.clear(getApplicationContext());
            MapsStorage.clear(getApplicationContext());
            clearMapsUI();
            updateAuthUI();
        });


        root = findViewById(R.id.main);
        flow = findViewById(R.id.flow);

        updateAuthUI();
        refreshMapsIfLoggedIn();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        updateAuthUI();
        refreshMapsIfLoggedIn();
    }

    private void updateAuthUI() {
        String token = TokenStorage.getAccessToken(getApplicationContext());
        boolean loggedIn = token != null && !token.trim().isEmpty();

        if (loggedIn) {
            registrationActivity.setVisibility(View.GONE);
            loginActivity.setVisibility(View.GONE);

            accountExitButton.setVisibility(View.VISIBLE);
            usernameTextMain.setVisibility(View.VISIBLE);

            String username = TokenStorage.getUsername(getApplicationContext());
            usernameTextMain.setText(username != null ? username : "Username");
        } else {
            registrationActivity.setVisibility(View.VISIBLE);
            loginActivity.setVisibility(View.VISIBLE);

            accountExitButton.setVisibility(View.GONE);
            usernameTextMain.setVisibility(View.GONE);
        }
    }

    private void SetRegistrationAcitivty(View v){
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), RegistrationActivity.class);
        startActivity(intent);
    }
    private void SetLoginAcitivty(View v){
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    private boolean hasToken() {
        String token = TokenStorage.getAccessToken(getApplicationContext());
        return token != null && !token.trim().isEmpty();
    }

    private void clearMapsUI() {
        for (int id : mapButtonIds) {
            View v = root.findViewById(id);
            if (v != null) root.removeView(v);
        }
        mapButtonIds.clear();
        flow.setReferencedIds(new int[0]);
    }

    private void renderMapsFromStorage() {
        MapSaveListModelDTO dto = MapsStorage.get(getApplicationContext());
        if (dto == null || dto.getMapSaveList() == null) return;

        clearMapsUI();

        ArrayList<Integer> ids = new ArrayList<>();

        for (MapSaveModelDTO m : dto.getMapSaveList()) {
            Button btn = new Button(this);
            int id = View.generateViewId();
            btn.setId(id);
            btn.setText(m.getMapName() != null ? m.getMapName() : ("Map " + m.getMapId()));

            btn.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, StatsActivity.class);
                intent.putExtra(StatsActivity.EXTRA_MAP_ID, m.getMapId());
                startActivity(intent);
            });

            root.addView(btn);
            ids.add(id);
            mapButtonIds.add(id);
        }

        int[] idArray = new int[ids.size()];
        for (int i = 0; i < ids.size(); i++) idArray[i] = ids.get(i);
        flow.setReferencedIds(idArray);
    }

    private void refreshMapsIfLoggedIn() {
        if (!hasToken()) {
            MapsStorage.clear(getApplicationContext());
            clearMapsUI();
            return;
        }

        mapsClass.GetMapsInfo(new MapsCallback() {
            @Override
            public void onMapsRecieved(String result) {
                runOnUiThread(() -> renderMapsFromStorage());
            }

            @Override
            public void onError(String error) {

            }
        });
    }

}