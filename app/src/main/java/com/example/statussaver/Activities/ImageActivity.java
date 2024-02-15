package com.example.statussaver.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

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
        activityImageBinding.toolbar.setNavigationIcon(R.drawable.back);
        activityImageBinding.toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        setSupportActionBar(activityImageBinding.toolbar);

        activityImageBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ImageActivity.this, WhatsDeleteActivity.class));
                finish();
            }
        });
    }
}