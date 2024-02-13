package com.example.statussaver.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.statussaver.R;
import com.example.statussaver.databinding.ActivityVideoBinding;

public class VideoActivity extends AppCompatActivity {

    ActivityVideoBinding activityVideoBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityVideoBinding = ActivityVideoBinding.inflate(getLayoutInflater());
        setContentView(activityVideoBinding.getRoot());
        activityVideoBinding.toolbar.setTitle("Videos");
        setSupportActionBar(activityVideoBinding.toolbar);
    }
}