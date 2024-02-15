package com.example.statussaver.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

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
        activityAudioBinding.toolbar.setNavigationIcon(R.drawable.back);
        activityAudioBinding.toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        setSupportActionBar(activityAudioBinding.toolbar);

        activityAudioBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AudioActivity.this, WhatsDeleteActivity.class));
                finish();
            }
        });
    }
}