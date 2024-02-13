package com.example.statussaver.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.statussaver.R;
import com.example.statussaver.databinding.ActivityMainBinding;
import com.example.statussaver.databinding.ActivityMainFeaturesBinding;

public class MainFeaturesActivity extends AppCompatActivity {

    ActivityMainFeaturesBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainFeaturesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.toolbar.setTitle("Whatscan");
        setSupportActionBar(binding.toolbar);

        binding.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainFeaturesActivity.this, WebViewActivity.class));
            }
        });

        binding.cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainFeaturesActivity.this, WhatsDeleteActivity.class));
            }
        });

        binding.cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainFeaturesActivity.this, DirectChatActivity.class));
            }
        });

        binding.cardView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainFeaturesActivity.this, QRScannerActivity.class));
            }
        });

        binding.cardView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainFeaturesActivity.this, MainActivity.class));
            }
        });

        binding.cardView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainFeaturesActivity.this, SavedStatusActivity.class));
            }
        });

        binding.cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainFeaturesActivity.this, BusinessStatusActivity.class));
            }
        });

    }
}