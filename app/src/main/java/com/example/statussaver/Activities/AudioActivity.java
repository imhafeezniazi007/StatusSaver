package com.example.statussaver.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.example.statussaver.R;
import com.example.statussaver.databinding.ActivityAudioBinding;

public class AudioActivity extends AppCompatActivity {

    ActivityAudioBinding activityAudioBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAudioBinding = ActivityAudioBinding.inflate(getLayoutInflater());
        setContentView(activityAudioBinding.getRoot());
        activityAudioBinding.toolbar.setTitle("Audios");
        setSupportActionBar(activityAudioBinding.toolbar);
    }
}