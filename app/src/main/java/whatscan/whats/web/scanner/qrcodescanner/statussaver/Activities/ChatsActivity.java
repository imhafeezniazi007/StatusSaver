package whatscan.whats.web.scanner.qrcodescanner.statussaver.Activities;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import whatscan.whats.web.scanner.qrcodescanner.statussaver.Adapters.ChatsAdapter;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.Models.NotificationText;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.R;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.Utils.NotificationDAO;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.Utils.NotificationDatabase;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.databinding.ActivityChatsBinding;

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

        new LoadNotificationsTask(notificationDAO, chatsAdapter, ChatsActivity.this).execute();
    }

    private static class LoadNotificationsTask extends AsyncTask<Void, Void, List<Pair<String, String>>> {
        private NotificationDAO notificationDAO;
        private ChatsAdapter chatsAdapter;
        private ChatsActivity activity;

        public LoadNotificationsTask(NotificationDAO notificationDAO, ChatsAdapter chatsAdapter, ChatsActivity activity) {
            this.notificationDAO = notificationDAO;
            this.chatsAdapter = chatsAdapter;
            this.activity = activity;
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
            if (senderAndLatestTextList.isEmpty()) {
                activity.activityChatsBinding.textNoItem.setVisibility(View.VISIBLE);
            } else {
                chatsAdapter.setData(senderAndLatestTextList);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

