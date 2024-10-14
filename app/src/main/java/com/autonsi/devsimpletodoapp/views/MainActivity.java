package com.autonsi.devsimpletodoapp.views;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.autonsi.devsimpletodoapp.R;
import com.autonsi.devsimpletodoapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_main);

        // Khởi tạo View Binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
