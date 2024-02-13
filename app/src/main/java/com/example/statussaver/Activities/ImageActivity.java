package com.example.statussaver.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.statussaver.R;
import com.example.statussaver.databinding.ActivityImageBinding;

public class ImageActivity extends AppCompatActivity {

    ActivityImageBinding activityImageBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityImageBinding = ActivityImageBinding.inflate(getLayoutInflater());
        setContentView(activityImageBinding.getRoot());
        activityImageBinding.toolbar.setTitle("Images");
        setSupportActionBar(activityImageBinding.toolbar);
    }
}