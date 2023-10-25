package com.example.lab5_iot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.lab5_iot.databinding.ActivityEmployeeDownSchedBinding;

public class EmployeeDownSchedActivity extends AppCompatActivity {
    ActivityEmployeeDownSchedBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmployeeDownSchedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }


}