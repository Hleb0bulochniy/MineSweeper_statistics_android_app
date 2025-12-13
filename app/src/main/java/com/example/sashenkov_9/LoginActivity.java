package com.example.sashenkov_9;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.sashenkov_9.Help.AuthClass;
import com.example.sashenkov_9.Help.LoginCallback;
import com.example.sashenkov_9.Help.RegisterCallback;

public class LoginActivity extends AppCompatActivity {
    AuthClass auth;
    Button loginButton;
    EditText usernameText;
    EditText passwordText;
    TextView serverResponseText;
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        auth = new AuthClass(getApplicationContext());

        loginButton = findViewById(R.id.loginButton);
        usernameText = findViewById(R.id.loginName);
        passwordText = findViewById(R.id.loginPassword);
        serverResponseText = findViewById(R.id.serverResponseTextLogin);
        backButton = findViewById(R.id.loginBackButton);

        loginButton.setOnClickListener(this::Login);
        backButton.setOnClickListener(this::SetMainActivity);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void Login(View v){
        auth.login(usernameText.getText().toString(), passwordText.getText().toString(),
                new LoginCallback() {
                    @Override
                    public void onTokenRecieved(String result) {
                        runOnUiThread(() -> {
                            serverResponseText.setText(result);
                            SetMainActivity(v);
                        });
                    }


                    @Override
                    public void onError(String error) {
                        runOnUiThread(() ->
                                serverResponseText.setText(error)
                        );
                    }
                }
        );

    }

    private void SetMainActivity(View v){
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}