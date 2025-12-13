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

import com.example.sashenkov_9.Help.TokenStorage;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button registrationActivity;
    Button loginActivity;

    Button accountExitButton;
    TextView usernameTextMain;
    private List<MyItem> backendList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        registrationActivity = findViewById(R.id.switch_activity_registration);
        loginActivity = findViewById(R.id.switch_activity_login);
        accountExitButton = findViewById(R.id.accountExitButton);
        usernameTextMain = findViewById(R.id.UsernameTextMain);

        registrationActivity.setOnClickListener(this::SetRegistrationAcitivty);
        loginActivity.setOnClickListener(this::SetLoginAcitivty);

        accountExitButton.setOnClickListener(v -> {
            TokenStorage.clear(getApplicationContext());
            updateAuthUI();
        });

        ConstraintLayout root = findViewById(R.id.main);
        Flow flow = findViewById(R.id.flow);

        backendList.add(new MyItem("Кнопка 1"));
        backendList.add(new MyItem("Кнопка 2"));
        backendList.add(new MyItem("Кнопка 3"));

        ArrayList<Integer> ids = new ArrayList<>();

        for (MyItem item : backendList) {
            Button btn = new Button(this);
            btn.setId(View.generateViewId());
            btn.setText(item.getName());

            // по желанию: лисенер клика
            // btn.setOnClickListener(v -> { ... });

            root.addView(btn);
            ids.add(btn.getId());
        }

        // привязываем все созданные кнопки к Flow
        int[] idArray = new int[ids.size()];
        for (int i = 0; i < ids.size(); i++) {
            idArray[i] = ids.get(i);
        }
        flow.setReferencedIds(idArray);

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

    private void UpdateMapsInfo(View v){

    }

    private static class MyItem {
        private final String name;

        MyItem(String name) {
            this.name = name;
        }

        String getName() {
            return name;
        }
    }
}