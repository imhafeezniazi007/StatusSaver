package com.example.statussaver.Activities;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.statussaver.R;
import com.example.statussaver.databinding.ActivityDirectChatBinding;
import com.example.statussaver.databinding.ActivityWebViewBinding;

public class DirectChatActivity extends AppCompatActivity {

    ActivityDirectChatBinding activityDirectChatBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDirectChatBinding = ActivityDirectChatBinding.inflate(getLayoutInflater());
        setContentView(activityDirectChatBinding.getRoot());
        activityDirectChatBinding.toolbar.setTitle("Direct Chat");
        activityDirectChatBinding.toolbar.setNavigationIcon(R.drawable.back);
        activityDirectChatBinding.toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        setSupportActionBar(activityDirectChatBinding.toolbar);

        activityDirectChatBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DirectChatActivity.this, MainFeaturesActivity.class));
                finish();
            }
        });

        activityDirectChatBinding.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = activityDirectChatBinding.countryCode.getSelectedCountryCodeWithPlus() + activityDirectChatBinding.editTextPhoneNumber.getText().toString();
                String message = activityDirectChatBinding.textMessageDirectMessage.getText().toString();
                if (message.equals("") && phoneNumber.equals("")) {
                    startActivity(
                            new Intent(Intent.ACTION_VIEW,
                                    Uri.parse(
                                            String.format("https://api.whatsapp.com/send?phone=%s&text=%s", phoneNumber, message)
                                    )
                            )
                    );
                }
                else {
                    activityDirectChatBinding.textMessageDirectMessage.setError("Please enter message");
                }
            }
        });

    }
}