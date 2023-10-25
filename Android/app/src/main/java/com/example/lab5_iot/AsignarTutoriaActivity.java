package com.example.lab5_iot;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.example.lab5_iot.databinding.ActivityAsignarTutoriaBinding;
import com.example.lab5_iot.service.TutorService;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AsignarTutoriaActivity extends AppCompatActivity {
    ActivityAsignarTutoriaBinding binding;
    TutorService tutorService;
    String localhost = "192.168.1.7";
    private EditText editTutorCode;
    private EditText editEmployeeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAsignarTutoriaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Encuentra las vistas por sus IDs
        editTutorCode = binding.editTutorCode;
        editEmployeeId = binding.editEmployeeId;

        tutorService = new Retrofit.Builder()
                .baseUrl("http://"+localhost+":3000")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TutorService.class);

        // Configura el clic del botón para realizar la asignación de tutoría
        binding.asignarTutoriaDisplay.setOnClickListener(view -> {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(binding.asignarTutoriaDisplay.getWindowToken(), 0);
            asignarTutoria(editTutorCode.getText().toString(), editEmployeeId.getText().toString());
        });

        binding.button6.setOnClickListener(view -> finish());
        binding.button7.setOnClickListener(view -> {
            new MaterialAlertDialogBuilder(this)
                    .setTitle("Detalles")
                    .setMessage("\"Asignar tutorías\" sirve para permitir a los tutores citar a sus trabajadores en reuniones programadas en un plazo cercano. Asegúrate de ingresar correctamente los códigos y que el tutor ingresado sea el manager del empleado.")
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .show();
        });
    }

    private void asignarTutoria(String managerId, String employeeId) {
        tutorService.postAssignment(Integer.parseInt(managerId), Integer.parseInt(employeeId)).enqueue(new Callback<HashMap<String, String>>() {
            @Override
            public void onResponse(Call<HashMap<String, String>> call, Response<HashMap<String, String>> response) {
                if (response.isSuccessful()){
                    HashMap<String, String> result = response.body();
                    String message = result.get("message");
                    Snackbar snackbar = Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
                else {
                    Snackbar snackbar = Snackbar.make(binding.getRoot(), "Error en el servidor", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
            }

            @Override
            public void onFailure(Call<HashMap<String, String>> call, Throwable t) {
                Log.d("msg-test", "error: "+t.getMessage());
                Snackbar snackbar = Snackbar.make(binding.getRoot(), "Error en la consulta, intentelo después.", Snackbar.LENGTH_SHORT);
                    snackbar.show();
            }
        });
    }
}
