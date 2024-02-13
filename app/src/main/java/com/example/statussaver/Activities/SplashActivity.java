package com.example.statussaver.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import com.example.statussaver.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {

    ActivitySplashBinding binding;
    private static final int SPLASH_DURATION = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        anim();

    }

    private void anim(){

        binding.logoImageView.postDelayed(new Runnable() {
            @Override
            public void run() {
                startMainActivity();
            }
        }, SPLASH_DURATION);
    }
    private void startMainActivity() {
        startActivity(new Intent(this, MainFeaturesActivity.class));
        finish();
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        anim();

    }
}