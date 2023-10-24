package com.example.lab5_iot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.lab5_iot.databinding.ActivityDisplayTutorBinding;
import com.google.android.material.snackbar.Snackbar;

public class DisplayTutorActivity extends AppCompatActivity {
    ActivityDisplayTutorBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDisplayTutorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.asignarTutoria.setOnClickListener(view -> {
            // Crear un Intent para abrir la actividad DisplayTutor
            Intent intent = new Intent(DisplayTutorActivity.this, AsignarTutoriaActivity.class);
            startActivity(intent);
        });

        binding.BuscarTrabajador.setOnClickListener(view -> {
            Intent intent = new Intent(DisplayTutorActivity.this, SearchEmployeeActivity.class);
            startActivity(intent);

        });
        binding.DescargarLista.setOnClickListener(view -> {
            Intent intent = new Intent(DisplayTutorActivity.this, DownloadEmployeeActivity.class);
            startActivity(intent);
        });
    }
}