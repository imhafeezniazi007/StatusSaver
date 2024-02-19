package com.example.statussaver.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import com.example.statussaver.R;
import com.example.statussaver.databinding.ActivitySplashBinding;

import java.util.Calendar;

public class SplashActivity extends AppCompatActivity {

    ActivitySplashBinding binding;
    private static final int SPLASH_DURATION = 2000;
    private static final String PREF_NAME = "MyPrefs";
    private static final String SUBSCRIBED_KEY = "subscribed";
    private static final String LAST_SHOWN_KEY = "lastShown";

    private static final String KEY_FIRST_TIME = "isFirstTime";
    private SharedPreferences sharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPrefs = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        boolean isFirstTime = sharedPrefs.getBoolean(KEY_FIRST_TIME, true);

        binding.appCompatButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFirstTime) {
                    startActivity(new Intent(SplashActivity.this, PrivacyPolicyActivity.class));

                    SharedPreferences.Editor editor = sharedPrefs.edit();
                    editor.putBoolean(KEY_FIRST_TIME, false);
                    editor.apply();
                    finish();
                } else {
                    if (!isSubscribed() && shouldShowActivity()) {
                        showActivity();
                        finish();
                    } else {
                        startMainActivity();
                    }
                }
            }
        });

    }

    private void startMainActivity() {
        startActivity(new Intent(SplashActivity.this, MainFeaturesActivity.class));
        finish();
    }

    private boolean isSubscribed() {
        return sharedPrefs.getBoolean(SUBSCRIBED_KEY, false);
    }

    private void showActivity() {
        startActivity(new Intent(SplashActivity.this, SubscriptionActivity.class));
    }

    private boolean shouldShowActivity() {
        long lastShown = sharedPrefs.getLong(LAST_SHOWN_KEY, 0);
        long currentTime = Calendar.getInstance().getTimeInMillis();
        long threeDaysInMillis = 3 * 24 * 60 * 60 * 1000; // 3 days in milliseconds

        return (currentTime - lastShown) >= threeDaysInMillis;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}