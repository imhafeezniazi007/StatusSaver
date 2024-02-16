package com.example.statussaver.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.example.statussaver.Adapters.ChatsAdapter;
import com.example.statussaver.Adapters.MessagesAdapter;
import com.example.statussaver.Models.NotificationText;
import com.example.statussaver.R;
import com.example.statussaver.Utils.NotificationDAO;
import com.example.statussaver.Utils.NotificationDatabase;
import com.example.statussaver.databinding.ActivityChatsViewBinding;

import java.util.ArrayList;
import java.util.List;

public class ChatsViewActivity extends AppCompatActivity {

    ActivityChatsViewBinding activityChatsViewBinding;
    NotificationDAO notificationDAO;
    MessagesAdapter chatsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityChatsViewBinding = ActivityChatsViewBinding.inflate(getLayoutInflater());
        setContentView(activityChatsViewBinding.getRoot());

        String senderName = getIntent().getStringExtra("sender_name");

        activityChatsViewBinding.toolbar.setTitle(senderName);
        activityChatsViewBinding.toolbar.setNavigationIcon(R.drawable.back);
        activityChatsViewBinding.toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        setSupportActionBar(activityChatsViewBinding.toolbar);

        activityChatsViewBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChatsViewActivity.this, ChatsActivity.class));
                finish();
            }
        });

        activityChatsViewBinding.rvChatsView.setLayoutManager(new LinearLayoutManager(this));

        NotificationDatabase notificationDatabase = NotificationDatabase.getInstance(this);
        notificationDAO = notificationDatabase.notificationDao();

        chatsAdapter = new MessagesAdapter(this, new ArrayList<>());
        activityChatsViewBinding.rvChatsView.setAdapter(chatsAdapter);

        // Fetch messages for the sender
        new LoadMessagesForSenderTask(notificationDAO, chatsAdapter, senderName).execute();
    }

    private static class LoadMessagesForSenderTask extends AsyncTask<Void, Void, List<NotificationText>> {
        private NotificationDAO notificationDAO;
        private MessagesAdapter adapter;
        private String senderName;

        public LoadMessagesForSenderTask(NotificationDAO notificationDAO, MessagesAdapter adapter, String senderName) {
            this.notificationDAO = notificationDAO;
            this.adapter = adapter;
            this.senderName = senderName;
        }

        @Override
        protected List<NotificationText> doInBackground(Void... voids) {
            return notificationDAO.getAllMessagesForSender(senderName);
        }

        @Override
        protected void onPostExecute(List<NotificationText> messages) {
            super.onPostExecute(messages);
            adapter.setData(messages);
        }
    }
}