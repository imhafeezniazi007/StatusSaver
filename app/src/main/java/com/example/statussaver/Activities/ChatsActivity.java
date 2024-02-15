package com.example.statussaver.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.statussaver.Adapters.ChatsAdapter;
import com.example.statussaver.Adapters.QRScanAdapter;
import com.example.statussaver.Models.NotificationText;
import com.example.statussaver.Models.QRScan;
import com.example.statussaver.R;
import com.example.statussaver.databinding.ActivityChatsBinding;
import com.example.statussaver.databinding.ActivityMainFeaturesBinding;

import java.util.ArrayList;
import java.util.List;

public class ChatsActivity extends AppCompatActivity {

    ActivityChatsBinding activityChatsBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityChatsBinding = ActivityChatsBinding.inflate(getLayoutInflater());
        setContentView(activityChatsBinding.getRoot());
        activityChatsBinding.toolbar.setTitle("Chats");
        activityChatsBinding.toolbar.setNavigationIcon(R.drawable.back);
        activityChatsBinding.toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        setSupportActionBar(activityChatsBinding.toolbar);

        activityChatsBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChatsActivity.this, WhatsDeleteActivity.class));
                finish();
            }
        });

        activityChatsBinding.rvChats.setLayoutManager(new LinearLayoutManager(this));

        List<NotificationText> notificationTextList = new ArrayList<>();

        ChatsAdapter chatsAdapter = new ChatsAdapter(this, notificationTextList);

        activityChatsBinding.rvChats.setAdapter(chatsAdapter);
    }
}