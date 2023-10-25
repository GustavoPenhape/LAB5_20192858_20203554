package com.example.lab5_iot;

import static android.Manifest.permission.POST_NOTIFICATIONS;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import com.example.lab5_iot.databinding.ActivityEmployeeFeedbackBinding;
import com.example.lab5_iot.entity.EmployeeDto;
import com.example.lab5_iot.service.TutorService;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;

public class EmployeeFeedbackActivity extends AppCompatActivity {

    ActivityEmployeeFeedbackBinding binding;
    TutorService tutorService;
    String localhost = getIpAddress.getIPAddress(true); // true para IPv4, false para IPv6
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmployeeFeedbackBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        intent = getIntent();

        EmployeeDto employeeDto = (EmployeeDto) intent.getSerializableExtra("empleado");
        tutorService = new Retrofit.Builder()
                .baseUrl("http://"+localhost+":3000")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TutorService.class);

        binding.button11.setOnClickListener(view -> finish());

        binding.sendFeedback.setOnClickListener(view -> {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(binding.sendFeedback.getWindowToken(), 0);
            String feedback = binding.editFeedback.getText().toString();
            if (feedback.trim().equals("")){
                Snackbar.make(binding.getRoot(), "Debe llenar el campo antes de enviar", Snackbar.LENGTH_SHORT).show();
            }else {
                postFeedback(String.valueOf(employeeDto.getResult().get(0).getEmployee_id()), feedback);
            }
        });
    }
    public void postFeedback(String employeeId, String feedback){
        tutorService.postFeedback(Integer.parseInt(employeeId), feedback).enqueue(new Callback<HashMap<String, String>>() {
            @Override
            public void onResponse(Call<HashMap<String, String>> call, Response<HashMap<String, String>> response) {
                if (response.isSuccessful()){
                    Log.d("msg-test", "feedback enviado");
                    lanzarNotificacion("employeeChannel", "Feedback enviado de manera exitosa", "Canal Trabajadores");
                }else {
                    lanzarNotificacion("employeeChannel", "No se pudo enviar el feedback.", "Canal Trabajadores");
                }
            }

            @Override
            public void onFailure(Call<HashMap<String, String>> call, Throwable t) {
                lanzarNotificacion("employeeChannel", "No se pudo enviar el feedback.", "Canal Trabajadores");
            }
        });
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
}