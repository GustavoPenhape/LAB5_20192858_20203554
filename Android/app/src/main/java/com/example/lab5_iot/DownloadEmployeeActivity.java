package com.example.lab5_iot;

import static android.Manifest.permission.POST_NOTIFICATIONS;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import com.example.lab5_iot.databinding.ActivityDownloadEmployeeBinding;
import com.example.lab5_iot.entity.Employee;
import com.example.lab5_iot.service.TutorService;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DownloadEmployeeActivity extends AppCompatActivity {
    ActivityDownloadEmployeeBinding binding;
    String localhost = "192.168.1.7";
    TutorService tutorService;
    List<Employee> trabajadores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDownloadEmployeeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        tutorService = new Retrofit.Builder()
                .baseUrl("http://"+localhost+":3000")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TutorService.class);

        binding.btnDload.setOnClickListener(view -> {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(binding.btnDload.getWindowToken(), 0);
            String managerId = binding.tutorCode.getText().toString();
                fetchDataEmployees(managerId);
        });

        binding.button.setOnClickListener(view -> {
            finish();
        });

        binding.button3.setOnClickListener(view -> {
            new MaterialAlertDialogBuilder(this)
                    .setTitle("Detalles")
                    .setMessage("En esta sección puede descargar una lista con información de los trabajadores que cuenten con el tutor correspondiente al código que ingrese.")
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .show();
        });

    }

    private void fetchDataEmployees(String managerid){
        tutorService.getEmployeesByManager(Integer.parseInt(managerid)).enqueue(new Callback<List<Employee>>() {
            @Override
            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                if (response.isSuccessful()){
                    trabajadores = response.body();

                    Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TITLE, "listaTrabajadores"+managerid+".txt");
                    activityForResultLauncher.launch(intent);
                }else{
                    Snackbar snackbar = Snackbar.make(binding.getRoot(), "No se encuentran trabajadores para ese manager o el manager no existe.", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
            }

            @Override
            public void onFailure(Call<List<Employee>> call, Throwable t) {
                Snackbar snackbar = Snackbar.make(binding.getRoot(), "Error de servidor", Snackbar.LENGTH_SHORT);
                snackbar.show();
                Log.d("msg-test", "error: "+t.getMessage());
            }
        });
    }
    ActivityResultLauncher<Intent> activityForResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();

                    if (data != null) {
                        try (ParcelFileDescriptor pfd =
                                     getContentResolver().openFileDescriptor(data.getData(), "w");
                             FileWriter fileWriter = new FileWriter(pfd.getFileDescriptor())) {
                            String managerId = binding.tutorCode.getText().toString();
                            String contenido = "Lista de trabajadores con ManagerId "+managerId+"\n---------------------------------------------------------";
                            for (Employee e: trabajadores){
                                contenido = contenido + "\n" + e.getEmployee_id() + " | " + e.getLast_name() +", "+ e.getFirst_name()+" | "+ e.getEmail();
                            }
                            fileWriter.write(contenido);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

    public void saveInternal(List<Employee> employees) {
        //convertimos el arreglo a un String (para guardarlo como json
        Gson gson = new Gson();
        String listaTrabajosJson = gson.toJson(employees);

        //nombre del archivo a guardar
        String fileNameJson = "listaTrabajadores";

        //Se utiliza la clase FileOutputStream para poder almacenar en Android
        try (FileOutputStream fileOutputStream = this.openFileOutput(fileNameJson, Context.MODE_PRIVATE);
             FileWriter fileWriter = new FileWriter(fileOutputStream.getFD())) {
            fileWriter.write(listaTrabajosJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}