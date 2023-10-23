package com.example.lab5_iot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DisplayTutor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_tutor);

        Button descargarListaButton = findViewById(R.id.DescargarLista);
        Button buscarTrabajadorButton = findViewById(R.id.BuscarTrabajador);
        Button asignarTutoriaButton = findViewById(R.id.asignarTutoria);

        asignarTutoriaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear un Intent para abrir la actividad DisplayTutor
                Intent intent = new Intent(DisplayTutor.this, AsignarTutoria.class);
                startActivity(intent);
            }
        });

    }
}