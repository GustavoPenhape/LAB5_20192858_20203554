package com.example.lab5_iot;

import static android.Manifest.permission.POST_NOTIFICATIONS;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.lab5_iot.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    private Button tutorButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        crearCanalNotificacion("tutorChannel");
        crearCanalNotificacion("employeeChannel");

        binding.Tutorbutton.setOnClickListener(view -> {
            // Mostrar una notificación con IMPORTANCE_HIGH
            lanzarNotificacion("tutorChannel","Está entrando en modo Tutor", "Canal tutor");
            // Iniciar la nueva actividad
            Intent intent = new Intent(MainActivity.this, DisplayTutorActivity.class);
            startActivity(intent);
        });

        binding.Trabajadorbutton.setOnClickListener(view ->{
            lanzarNotificacion("employeeChannel", "Está entrando en modo Empleado", "Canal trabajador");
            Intent intent = new Intent(MainActivity.this, EmployeeInfoActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });



    }

    public void crearCanalNotificacion(String channelId) {
        //android.os.Build.VERSION_CODES.O == 26
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Canal notificaciones tutor",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Canal para notificaciones con prioridad high");

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

            askPermission();
        }
    }
    public void askPermission(){
        //android.os.Build.VERSION_CODES.TIRAMISU == 33
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                ActivityCompat.checkSelfPermission(this, POST_NOTIFICATIONS) ==
                        PackageManager.PERMISSION_DENIED) {

            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{POST_NOTIFICATIONS},
                    101);
        }
    }
    public void lanzarNotificacion(String channelId, String mensaje, String title) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_bell_ringing_fillled)
                .setContentTitle(title)
                .setContentText(mensaje)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        if (ActivityCompat.checkSelfPermission(this, POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            notificationManager.notify(1, builder.build());
        }
    }



/*
    private void crearNotificacion(String message) {
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

 */
}
