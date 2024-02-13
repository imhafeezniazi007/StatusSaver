package com.example.statussaver.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

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
        setSupportActionBar(activityDocumentBinding.toolbar);
    }
}