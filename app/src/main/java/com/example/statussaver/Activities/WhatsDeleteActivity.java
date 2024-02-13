package com.example.statussaver.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.statussaver.R;
import com.example.statussaver.databinding.ActivityWhatsDeleteBinding;

public class WhatsDeleteActivity extends AppCompatActivity {

    ActivityWhatsDeleteBinding activityWhatsDeleteBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityWhatsDeleteBinding = ActivityWhatsDeleteBinding.inflate(getLayoutInflater());
        setContentView(activityWhatsDeleteBinding.getRoot());
        activityWhatsDeleteBinding.toolbar.setTitle("Whats Delete");
        setSupportActionBar(activityWhatsDeleteBinding.toolbar);

    }
}