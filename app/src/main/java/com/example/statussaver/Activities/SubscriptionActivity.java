package com.example.statussaver.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.statussaver.R;
import com.example.statussaver.databinding.ActivitySubscriptionBinding;

public class SubscriptionActivity extends AppCompatActivity {

    ActivitySubscriptionBinding activitySubscriptionBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySubscriptionBinding = ActivitySubscriptionBinding.inflate(getLayoutInflater());
        setContentView(activitySubscriptionBinding.getRoot());
    }
}