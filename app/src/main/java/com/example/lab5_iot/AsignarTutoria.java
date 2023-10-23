package com.example.lab5_iot;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.lab5_iot.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public class AsignarTutoria extends AppCompatActivity {

    private EditText editTutorCode;
    private EditText editEmployeeId;
    private Button asignarTutoriaButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asignar_tutoria);

        // Encuentra las vistas por sus IDs
        editTutorCode = findViewById(R.id.editTutorCode);
        editEmployeeId = findViewById(R.id.editEmployeeId);
        asignarTutoriaButton = findViewById(R.id.asignarTutoria_display);

        // Configura el clic del botón para realizar la asignación de tutoría
        asignarTutoriaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tutorEmployeeCode = editTutorCode.getText().toString();
                String employeeIdToAssign = editEmployeeId.getText().toString();
                enviarSolicitudPost(tutorEmployeeCode, employeeIdToAssign);
            }
        });
    }

    private void enviarSolicitudPost(String tutorCode, String employeeId) {
        // Crear una instancia de Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.100:3000/")  // Reemplaza con la URL correcta de tu servidor Node.js
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Crear una interfaz para definir el servicio
        ApiService apiService = retrofit.create(ApiService.class);

        // Realizar la solicitud POST
        Call<String> call = apiService.asignarTutoria(tutorCode, employeeId);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    // Procesa la respuesta del servidor aquí
                    String message = response.body();
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                } else {
                    // Manejar errores de la solicitud
                    Toast.makeText(getApplicationContext(), "Error en la solicitud", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                // Manejar errores de la red
                Toast.makeText(getApplicationContext(), "Error en la red", Toast.LENGTH_LONG).show();
            }
        });
    }
}
