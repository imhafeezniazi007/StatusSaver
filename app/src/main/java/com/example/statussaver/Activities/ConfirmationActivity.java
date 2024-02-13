package com.example.statussaver.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.statussaver.R;
import com.example.statussaver.databinding.ActivityConfirmationBinding;

public class ConfirmationActivity extends AppCompatActivity {

    ActivityConfirmationBinding activityConfirmationBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityConfirmationBinding = ActivityConfirmationBinding.inflate(getLayoutInflater());
        setContentView(activityConfirmationBinding.getRoot());

    }
}