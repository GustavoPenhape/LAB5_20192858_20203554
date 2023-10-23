package com.example.lab5_iot;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.lab5_iot.R;

public class MainActivity extends AppCompatActivity {

    private Button tutorButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tutorButton = findViewById(R.id.Tutorbutton);

        tutorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Mostrar una notificación con IMPORTANCE_HIGH
                showHighImportanceNotification("Está entrando en modo Tutor");

                // Iniciar la nueva actividad
                Intent intent = new Intent(MainActivity.this, DisplayTutor.class);
                startActivity(intent);
            }
        });

    }

    private void showHighImportanceNotification(String message) {
        // Configurar la notificación con IMPORTANCE_HIGH
        NotificationChannel channel = new NotificationChannel("tutor_channel", "Tutor Channel", NotificationManager.IMPORTANCE_HIGH);

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);

        Notification notification = new Notification.Builder(this, "tutor_channel")
                .setContentTitle("Tutor App")
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_bell_ringing_fillled)
                .build();

        // Mostrar la notificación
        notificationManager.notify(1, notification);
    }
}
