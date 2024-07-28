package org.o7planning.notificationchannel1app;

import android.app.Notification;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MainActivity extends AppCompatActivity {

    private NotificationManagerCompat notificationManagerCompat;
    private EditText editTextTitle;
    private EditText editTextMessage;

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                // Permission not granted, handle appropriately
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextTitle = findViewById(R.id.editText_title);
        editTextMessage = findViewById(R.id.editText_message);
        Button button1 = findViewById(R.id.button1);

        button1.setOnClickListener(view -> sendOnChannel1());

        notificationManagerCompat = NotificationManagerCompat.from(this);

        // Request notification permission at runtime
        requestNotificationPermission();
    }

    private void requestNotificationPermission() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS);
        }
    }

    private void sendOnChannel1() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        String title = editTextTitle.getText().toString();
        String message = editTextMessage.getText().toString();

        Notification notification = new NotificationCompat.Builder(this, NotificationApp.CHANNEL_1_ID)
                .setSmallIcon(R.drawable.baseline_access_alarms_24)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        int notificationId = 1;
        notificationManagerCompat.notify(notificationId, notification);
    }
}
