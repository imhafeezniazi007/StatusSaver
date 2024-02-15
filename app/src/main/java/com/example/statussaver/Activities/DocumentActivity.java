package com.example.statussaver.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.statussaver.R;
import com.example.statussaver.databinding.ActivityDocumentBinding;

public class DocumentActivity extends AppCompatActivity {

    ActivityDocumentBinding activityDocumentBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDocumentBinding = ActivityDocumentBinding.inflate(getLayoutInflater());
        setContentView(activityDocumentBinding.getRoot());
        activityDocumentBinding.toolbar.setTitle("Document");
        activityDocumentBinding.toolbar.setNavigationIcon(R.drawable.back);
        activityDocumentBinding.toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        setSupportActionBar(activityDocumentBinding.toolbar);

        activityDocumentBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DocumentActivity.this, WhatsDeleteActivity.class));
                finish();
            }
        });
    }
}