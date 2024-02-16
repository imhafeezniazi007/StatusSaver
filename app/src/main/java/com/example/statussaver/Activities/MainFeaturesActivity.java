package com.example.statussaver.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.ComponentName;
import android.net.Uri;
import android.os.Build;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.statussaver.R;
import com.example.statussaver.Utils.FileObserverService;
import com.example.statussaver.Utils.NotificationListenService;
import com.example.statussaver.databinding.ActivityMainBinding;
import com.example.statussaver.databinding.ActivityMainFeaturesBinding;

public class MainFeaturesActivity extends AppCompatActivity {

    ActivityMainFeaturesBinding binding;
    private static final int READ_STORAGE_PERMISSION_REQUEST_CODE = 1;
    private static final int REQUEST_CODE_PERMISSIONS = 100;
    private static final int REQUEST_NOTIFICATION_ACCESS = 1;
    private static final String[] REQUIRED_PERMISSIONS = new String[] {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.MANAGE_EXTERNAL_STORAGE
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainFeaturesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.toolbar.setTitle("Whatscan");
        binding.toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        setSupportActionBar(binding.toolbar);

        startService(new Intent(getApplicationContext(), FileObserverService.class));

        if (!isNotificationServiceEnabled()) {
            requestNotificationAccess();
        }

        requestPermissionsIfNecessary();
        binding.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainFeaturesActivity.this, WebViewActivity.class));
            }
        });

        binding.cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainFeaturesActivity.this, WhatsDeleteActivity.class));
            }
        });

        binding.cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainFeaturesActivity.this, DirectChatActivity.class));
            }
        });

        binding.cardView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainFeaturesActivity.this, QRScannerActivity.class));
            }
        });

        binding.cardView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainFeaturesActivity.this, MainActivity.class));
            }
        });

        binding.cardView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainFeaturesActivity.this, SavedStatusActivity.class));
            }
        });

        binding.cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainFeaturesActivity.this, BusinessStatusActivity.class));
            }
        });


        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    READ_STORAGE_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case READ_STORAGE_PERMISSION_REQUEST_CODE: {
                // Check if the permission was granted
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted, you can now access the storage
                } else {
                    Toast.makeText(this, "permission not granted", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    private void requestPermissionsIfNecessary() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, REQUEST_CODE_PERMISSIONS);
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
            }
        }
    }

    private boolean isNotificationServiceEnabled() {
        ComponentName cn = new ComponentName(this, NotificationListenService.class);
        String flat = Settings.Secure.getString(getContentResolver(), "enabled_notification_listeners");
        return flat != null && flat.contains(cn.flattenToString());
    }

    private void requestNotificationAccess() {
        // Request permission to access notifications
        Intent intent = new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);
        startActivityForResult(intent, REQUEST_NOTIFICATION_ACCESS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_NOTIFICATION_ACCESS) {
            if (isNotificationServiceEnabled()) {
                Log.e("_serv", "onActivityResult: service started successfully" );
            } else {
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        onStop();
        finish();
    }
}