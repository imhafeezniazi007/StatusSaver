package com.example.statussaver.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Toast;

import com.example.statussaver.Adapters.ChatsAdapter;
import com.example.statussaver.Adapters.QRScanAdapter;
import com.example.statussaver.Models.NotificationText;
import com.example.statussaver.Models.QRScan;
import com.example.statussaver.R;
import com.example.statussaver.Utils.NotificationDAO;
import com.example.statussaver.Utils.NotificationDatabase;
import com.example.statussaver.databinding.ActivityChatsBinding;
import com.example.statussaver.databinding.ActivityMainFeaturesBinding;

import java.util.ArrayList;
import java.util.List;

public class ChatsActivity extends AppCompatActivity {

    ActivityChatsBinding activityChatsBinding;
    private NotificationDAO notificationDAO;
    private ChatsAdapter chatsAdapter;

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
                finish();
            }
        });

        activityChatsBinding.rvChats.setLayoutManager(new LinearLayoutManager(this));

        NotificationDatabase notificationDatabase = NotificationDatabase.getInstance(this);
        notificationDAO = notificationDatabase.notificationDao();

        chatsAdapter = new ChatsAdapter(this, new ArrayList<>());
        activityChatsBinding.rvChats.setAdapter(chatsAdapter);

        new LoadNotificationsTask(notificationDAO, chatsAdapter).execute();
    }

    private static class LoadNotificationsTask extends AsyncTask<Void, Void, List<Pair<String, String>>> {
        private NotificationDAO notificationDAO;
        private ChatsAdapter chatsAdapter;

        public LoadNotificationsTask(NotificationDAO notificationDAO, ChatsAdapter chatsAdapter) {
            this.notificationDAO = notificationDAO;
            this.chatsAdapter = chatsAdapter;
        }

        @Override
        protected List<Pair<String, String>> doInBackground(Void... voids) {
            List<String> senderNames = notificationDAO.getAllSenderNames();
            List<Pair<String, String>> senderAndLatestTextList = new ArrayList<>();
            for (String senderName : senderNames) {
                NotificationText latestMessage = notificationDAO.getLatestMessageForSender(senderName);
                if (latestMessage != null) {
                    senderAndLatestTextList.add(new Pair<>(senderName, latestMessage.getText()));
                }
            }
            return senderAndLatestTextList;

        }

        @Override
        protected void onPostExecute(List<Pair<String, String>> senderAndLatestTextList) {
            super.onPostExecute(senderAndLatestTextList);
            chatsAdapter.setData(senderAndLatestTextList);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

