package com.example.lab5_iot;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lab5_iot.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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
                int tutorCodeAsNumber = Integer.parseInt(tutorEmployeeCode);
                int employeeIdAsNumber = Integer.parseInt(employeeIdToAssign);
                enviarSolicitudPost(tutorCodeAsNumber, employeeIdAsNumber);
            }
        });
    }

    private void enviarSolicitudPost(Integer tutorCode, Integer employeeId) {
        String url = "http://127.0.0.1:3000/asignartutoria";

        // Crear un objeto JSON con los parámetros
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("tutorEmployeeCode", tutorCode);
            jsonParams.put("employeeIdToAssign", employeeId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Crear una solicitud POST
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonParams,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Procesa la respuesta del servidor aquí
                        Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
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
