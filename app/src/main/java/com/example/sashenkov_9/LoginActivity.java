package com.example.sashenkov_9;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.sashenkov_9.Help.AuthClass;
import com.example.sashenkov_9.Help.LoginCallback;
import com.example.sashenkov_9.Help.RegisterCallback;

import org.jspecify.annotations.NonNull;

public class LoginActivity extends AppCompatActivity {
    AuthClass auth;
    Button loginButton;
    EditText usernameText;
    EditText passwordText;
    TextView serverResponseText;
    Button backButton;

    final String CHANNEL_ID = "ch1";
    final int PERMISSION_CODE = 100;
    final int NOTIFICATION_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createNotificationChannel();

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
                            createNotification(v);
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

    public void createNotification(View view) {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED) {
            showNotification();
        } else {
            ActivityCompat.requestPermissions(LoginActivity.this,
                    new String[] {Manifest.permission.POST_NOTIFICATIONS},
                    PERMISSION_CODE);
        }
    }

    private void showNotification() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE
        );

        String textTitle = "MinesweeperStats";
        String textContent = "Successful login!";

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(textTitle)
                        .setContentText(textContent)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(textContent))
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(getApplicationContext());

        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull
    String[] permissions, @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions,
                grantResults);
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED)
                showNotification();
        }
    }



    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManagerCompat notificationManager =
                    NotificationManagerCompat.from(getApplicationContext());
            if (notificationManager.getNotificationChannel(CHANNEL_ID)
                    == null) {
                CharSequence name = "login";
                String description = "login notifications";
                int importance =
                        NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel channel = new
                        NotificationChannel(CHANNEL_ID, name, importance);
                channel.setDescription(description);
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    private void SetMainActivity(View v){
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}