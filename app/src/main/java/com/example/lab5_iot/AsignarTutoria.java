package com.example.lab5_iot;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lab5_iot.R;

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
        String url = "http://localhost:3000/asignar-tutoria"; // Reemplaza con la URL correcta de tu servidor Node.js

        // Crear una solicitud POST con los datos del tutor y el empleado
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Procesa la respuesta del servidor aquí
                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Manejar errores de la solicitud
                Toast.makeText(getApplicationContext(), "Error en la solicitud", Toast.LENGTH_LONG).show();
            }
        });

        // Agregar la solicitud a la cola de solicitudes
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}
