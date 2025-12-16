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

import com.example.sashenkov_9.Help.LoginCallback;
import com.example.sashenkov_9.Help.RegisterCallback;
import com.example.sashenkov_9.Help.AuthClass;

public class RegistrationActivity extends AppCompatActivity {

    AuthClass authClass;
    Button registerButton;
    EditText userNameText;
    EditText emailText;
    EditText passwordText;
    EditText passwordRepeatText;
    Button backButton;
    TextView serverResponseText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration);

        authClass = new AuthClass(getApplicationContext());

        registerButton = findViewById(R.id.registrationButton);
        userNameText = findViewById(R.id.registrationName);
        emailText = findViewById(R.id.registrationEmail);
        passwordText = findViewById(R.id.registrationPassword);
        passwordRepeatText = findViewById(R.id.registrationPasswordRepeat);
        backButton = findViewById(R.id.registrationBackButton);
        serverResponseText = findViewById(R.id.serverResponseText);

        registerButton.setOnClickListener(this::Register);
        backButton.setOnClickListener(this::SetMainActivity);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void Register(View v){
        authClass.register(userNameText.getText().toString(), emailText.getText().toString(), passwordText.getText().toString(), passwordRepeatText.getText().toString(),
                new RegisterCallback() {

                    @Override
                    public void onResult(String result) {
                        authClass.login(
                                userNameText.getText().toString(),
                                passwordText.getText().toString(),
                                new LoginCallback() {
                                    @Override
                                    public void onTokenRecieved(String token) {
                                        runOnUiThread(() -> {
                                            serverResponseText.setText("OK");
                                            SetMainActivity(v);
                                        });
                                    }

                                    @Override
                                    public void onError(String error) {
                                        runOnUiThread(() -> serverResponseText.setText(error));
                                    }
                                }
                        );
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