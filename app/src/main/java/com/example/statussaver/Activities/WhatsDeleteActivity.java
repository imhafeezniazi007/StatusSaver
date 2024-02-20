package com.example.statussaver.Activities;

import static android.os.FileObserver.DELETE;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.statussaver.R;
import com.example.statussaver.Utils.Consts;
import com.example.statussaver.Utils.NotificationDAO;
import com.example.statussaver.Utils.NotificationDatabase;
import com.example.statussaver.Utils.NotificationListenService;
import com.example.statussaver.Utils.RecursiveFileObserver;
import com.example.statussaver.databinding.ActivityWhatsDeleteBinding;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class WhatsDeleteActivity extends AppCompatActivity {

    ActivityWhatsDeleteBinding activityWhatsDeleteBinding;
    NotificationDAO notificationDAO;
    private static final int REQUEST_NOTIFICATION_ACCESS = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityWhatsDeleteBinding = ActivityWhatsDeleteBinding.inflate(getLayoutInflater());
        setContentView(activityWhatsDeleteBinding.getRoot());
        activityWhatsDeleteBinding.toolbar.setTitle("Whats Delete");
        activityWhatsDeleteBinding.toolbar.setNavigationIcon(R.drawable.back);
        activityWhatsDeleteBinding.toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        setSupportActionBar(activityWhatsDeleteBinding.toolbar);


        if (!isNotificationServiceEnabled()) {
            requestNotificationAccess();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                NotificationDatabase notificationDatabase = NotificationDatabase.getInstance(WhatsDeleteActivity.this);
                notificationDAO = notificationDatabase.notificationDao();

                int totalSenders = notificationDAO.getTotalSenders();
                activityWhatsDeleteBinding.messagesTextview.setText(totalSenders+ " Messages");
            }
        }).start();

        activityWhatsDeleteBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WhatsDeleteActivity.this, MainFeaturesActivity.class));
                finish();
            }
        });

        setClickListener(activityWhatsDeleteBinding.relativeLayout1, ChatsActivity.class);
        setClickListener(activityWhatsDeleteBinding.relativeLayout2, ImageActivity.class);
        setClickListener(activityWhatsDeleteBinding.relativeLayout3, VideoActivity.class);
        setClickListener(activityWhatsDeleteBinding.relativeLayout4, AudioActivity.class);
        setClickListener(activityWhatsDeleteBinding.relativeLayout5, DocumentActivity.class);
        setClickListener(activityWhatsDeleteBinding.relativeLayout6, VoiceActivity.class);

    }

    private void setClickListener(View view, final Class<?> activityClass) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WhatsDeleteActivity.this, activityClass));
            }
        });
    }



    private boolean isNotificationServiceEnabled() {
        ComponentName cn = new ComponentName(this, NotificationListenService.class);
        String flat = Settings.Secure.getString(getContentResolver(), "enabled_notification_listeners");
        return flat != null && flat.contains(cn.flattenToString());
    }

    private void requestNotificationAccess() {
        if (!isFinishing()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Permission Needed");
            builder.setMessage("This feature needs notification access to function");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);
                    startActivityForResult(intent, REQUEST_NOTIFICATION_ACCESS);
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    Toast.makeText(WhatsDeleteActivity.this, "Permission denied", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });

            AlertDialog dialog = builder.create();
            if (!isFinishing()) {
                dialog.show();
            }
        }
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
        startActivity(new Intent(WhatsDeleteActivity.this, MainFeaturesActivity.class));
        finish();
    }
}