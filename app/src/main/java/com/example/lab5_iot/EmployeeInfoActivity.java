package com.example.lab5_iot;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.lab5_iot.databinding.ActivityEmployeeInfoBinding;
import com.example.lab5_iot.entity.EmployeeDto;
import com.example.lab5_iot.service.TutorService;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EmployeeInfoActivity extends AppCompatActivity {
    ActivityEmployeeInfoBinding binding;
    TutorService tutorService;
    String localhost = getIpAddress.getIPAddress(true); // true para IPv4, false para IPv6
    EmployeeDto eDto;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmployeeInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        tutorService = new Retrofit.Builder()
                .baseUrl("http://"+localhost+":3000")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TutorService.class);

        binding.btnIngresar.setOnClickListener(view -> {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(binding.btnIngresar.getWindowToken(), 0);
            if(binding.workerCode.getText().toString().trim().equals("")){
                Snackbar.make(binding.getRoot(), "Ingrese un código para ingresar", Snackbar.LENGTH_SHORT).show();
            }
            else {
                fetchDataEmployees(binding.workerCode.getText().toString());
            }
        });

        binding.button9.setOnClickListener(view -> finish());
    }
    public void fetchDataEmployees(String employeeId){
        tutorService.getEmployeeById(Integer.parseInt(employeeId)).enqueue(new Callback<EmployeeDto>() {
            @Override
            public void onResponse(Call<EmployeeDto> call, Response<EmployeeDto> response) {
                if (response.isSuccessful()){
                    eDto = response.body();
                    Log.d("msg-test",eDto.getStatus());
                    if (eDto.getStatus().equals("error")){
                        Toast.makeText(EmployeeInfoActivity.this, "No se ha encontrado ningun empleado con dicho codigo.", Toast.LENGTH_SHORT).show();
                        Snackbar.make(binding.getRoot(), "No se ha encontrado ningun empleado con dicho codigo.", Snackbar.LENGTH_SHORT).show();
                    }
                    else {
                        intent = new Intent(EmployeeInfoActivity.this, EmployeeMainActivity.class);
                        intent.putExtra("empleado", eDto);
                        startActivity(intent);
                    }
                }
            }
            @Override
            public void onFailure(Call<EmployeeDto> call, Throwable t) {
                Log.d("msg-test", "error: "+t.getMessage());
            }
        });
    }

}