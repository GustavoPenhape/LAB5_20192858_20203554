package com.example.lab5_iot;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import com.example.lab5_iot.databinding.ActivitySearchEmployeeBinding;
import com.example.lab5_iot.entity.Employee;
import com.example.lab5_iot.entity.EmployeeDto;
import com.example.lab5_iot.service.TutorService;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import java.io.File;
import java.net.MalformedURLException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchEmployeeActivity extends AppCompatActivity {
    ActivitySearchEmployeeBinding binding;
    String localhost = "10.100.56.229";
    TutorService tutorService;
    EmployeeDto eDto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchEmployeeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        tutorService = new Retrofit.Builder()
                .baseUrl("http://"+localhost+":3000")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TutorService.class);

        binding.btnDload2.setOnClickListener(view -> {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(binding.btnDload2.getWindowToken(), 0);
            String permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;

            // >=29
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q ||
                    ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
                try {
                    descargarConDownloadManager();
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            }else{
                launcher.launch(permission);
            }
        });
        binding.button2.setOnClickListener(view -> {
            finish();
        });
        binding.button4.setOnClickListener(view -> {
            new MaterialAlertDialogBuilder(this)
                    .setTitle("Detalles")
                    .setMessage("En esta sección puede descargar un archivo de texto con información del trabajador que se encuentre. Asegúrese de ingresar un código válido")
                    .setPositiveButton("Aceptar", (dialogInterface, i) -> {

                    })
                    .show();
        });
    }
    ActivityResultLauncher<String> launcher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            isGranted -> {

                if (isGranted) { // permiso concedido
                    try {
                        descargarConDownloadManager();
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    Snackbar snackbar = Snackbar.make(binding.getRoot(), "No se encontró ningún trabajador con el código ingresado.", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                    Log.e("msg-test", "Permiso denegado");
                }
            });
    public void descargarConDownloadManager() throws MalformedURLException {
        String fileName = "informacionDe"+binding.employeeCode.getText().toString()+".txt";
        Uri downloadUri = Uri.parse("http://"+localhost+":3000/buscar/"+binding.employeeCode.getText().toString());

        DownloadManager.Request request = new DownloadManager.Request(downloadUri);

        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setAllowedOverRoaming(false);
        request.setTitle(fileName);
        request.setMimeType("text/plain");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOCUMENTS, File.separator + fileName);


        DownloadManager dm = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        try {
            dm.enqueue(request);
        }catch (RuntimeException e){
            e.printStackTrace();
        }
    }
    private void fetchDataEmployees(String employeeId){
        tutorService.getEmployeeById(Integer.parseInt(employeeId)).enqueue(new Callback<EmployeeDto>() {
            @Override
            public void onResponse(Call<EmployeeDto> call, Response<EmployeeDto> response) {
                if (response.isSuccessful()){
                    eDto = response.body();
                }
            }

            @Override
            public void onFailure(Call<EmployeeDto> call, Throwable t) {
                Log.d("msg-test", "error: "+t.getMessage());
            }
        });
    }
}