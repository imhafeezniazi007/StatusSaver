package com.example.statussaver.Activities;

import static android.os.FileObserver.DELETE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.statussaver.R;
import com.example.statussaver.Utils.Consts;
import com.example.statussaver.Utils.NotificationDAO;
import com.example.statussaver.Utils.NotificationDatabase;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityWhatsDeleteBinding = ActivityWhatsDeleteBinding.inflate(getLayoutInflater());
        setContentView(activityWhatsDeleteBinding.getRoot());
        activityWhatsDeleteBinding.toolbar.setTitle("Whats Delete");
        activityWhatsDeleteBinding.toolbar.setNavigationIcon(R.drawable.back);
        activityWhatsDeleteBinding.toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        setSupportActionBar(activityWhatsDeleteBinding.toolbar);

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

        activityWhatsDeleteBinding.relativeLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WhatsDeleteActivity.this, ChatsActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(WhatsDeleteActivity.this, MainFeaturesActivity.class));
        finish();
    }
}