package com.example.statussaver.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import com.example.statussaver.R;
import com.example.statussaver.Utils.ProgressImageDialog;
import com.example.statussaver.databinding.ActivitySplashBinding;

import java.util.Calendar;

public class SplashActivity extends AppCompatActivity {

    ActivitySplashBinding binding;
    private static final String PREF_NAME = "MyPrefs";
    private static final String ONE_MONTH = "monthly_subscribed";
    private static final String ONE_YEAR = "yearly_subscribed";
    private static final String ONE_TIME = "one_time_purchased";
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

        binding.appCompatButton3.setVisibility(View.INVISIBLE);
        binding.progressCircular.setVisibility(View.VISIBLE);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.progressCircular.setVisibility(View.GONE);
                binding.appCompatButton3.setVisibility(View.VISIBLE);
            }
        }, 4000);

        binding.appCompatButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFirstTime) {
                    startActivity(new Intent(SplashActivity.this, PrivacyPolicyActivity.class));
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
        return sharedPrefs.getBoolean(ONE_MONTH, false) || sharedPrefs.getBoolean(ONE_YEAR, false) || sharedPrefs.getBoolean(ONE_TIME, false);
    }

    private void showActivity() {
        startActivity(new Intent(SplashActivity.this, SubscriptionActivity.class));
    }

    private boolean shouldShowActivity() {
        long lastShown = sharedPrefs.getLong(LAST_SHOWN_KEY, 0);
        long currentTime = Calendar.getInstance().getTimeInMillis();

        long timeDiff = currentTime - lastShown;
        if (timeDiff >= getSubscriptionPeriod()) {
            return true;
        }
        return false;
    }

    private long getSubscriptionPeriod() {
        if (isSubscribed()) {
            if (sharedPrefs.getBoolean(ONE_MONTH, false)) {
                return 30 * 24 * 60 * 60 * 1000; // 30 days in milliseconds
            } else if (sharedPrefs.getBoolean(ONE_YEAR, false)) {
                return 365 * 24 * 60 * 60 * 1000; // 365 days in milliseconds
            } else if (sharedPrefs.getBoolean(ONE_TIME, false)) {
                return Long.MAX_VALUE;
            }
        }

        return 3 * 24 * 60 * 60 * 1000;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}
