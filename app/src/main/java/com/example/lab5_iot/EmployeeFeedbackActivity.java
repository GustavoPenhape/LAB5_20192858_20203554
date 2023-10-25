package com.example.lab5_iot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.lab5_iot.databinding.ActivityEmployeeFeedbackBinding;

public class EmployeeFeedbackActivity extends AppCompatActivity {

    ActivityEmployeeFeedbackBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmployeeFeedbackBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }
}