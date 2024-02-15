package com.example.statussaver.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.statussaver.R;
import com.example.statussaver.databinding.ActivityChatsViewBinding;

public class ChatsViewActivity extends AppCompatActivity {

    ActivityChatsViewBinding activityChatsViewBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityChatsViewBinding = ActivityChatsViewBinding.inflate(getLayoutInflater());
        setContentView(activityChatsViewBinding.getRoot());


        //activityChatsViewBinding.toolbar.setTitle(N);
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
    }
}