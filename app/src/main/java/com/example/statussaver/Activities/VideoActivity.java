package com.example.statussaver.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

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
        activityVideoBinding.toolbar.setNavigationIcon(R.drawable.back);
        activityVideoBinding.toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        setSupportActionBar(activityVideoBinding.toolbar);

        activityVideoBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VideoActivity.this, WhatsDeleteActivity.class));
                finish();
            }
        });
    }
}