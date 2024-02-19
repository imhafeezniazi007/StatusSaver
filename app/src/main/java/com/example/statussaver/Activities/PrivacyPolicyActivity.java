package com.example.statussaver.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.TextView;

import com.example.statussaver.R;
import com.example.statussaver.databinding.ActivityPrivacyPolicyBinding;

public class PrivacyPolicyActivity extends AppCompatActivity {

    ActivityPrivacyPolicyBinding activityPrivacyPolicyBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPrivacyPolicyBinding = ActivityPrivacyPolicyBinding.inflate(getLayoutInflater());
        setContentView(activityPrivacyPolicyBinding.getRoot());

        TextView htmlTextView = activityPrivacyPolicyBinding.spannableText;
        String htmlContent = getString(R.string.html_content);
        Spanned spannedString = Html.fromHtml(htmlContent, Html.FROM_HTML_MODE_LEGACY);

        SpannableString spannableString = new SpannableString(spannedString);

        int startIndex = htmlContent.indexOf("Privacy Policy");
        int endIndex = startIndex + "Privacy Policy".length();

        int secondSIndex = 16;
        int secondEIndex = 22;

        StyleSpan[] spansToRemove = spannableString.getSpans(startIndex, endIndex, StyleSpan.class);
        for (StyleSpan span : spansToRemove) {
            spannableString.removeSpan(span);
        }

        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#FF109B2C"));
        spannableString.setSpan(colorSpan, secondSIndex, secondEIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/")));
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.parseColor("#FF109B2C"));
                ds.setUnderlineText(false);
            }
        };

        spannableString.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        htmlTextView.setText(spannableString);
        htmlTextView.setMovementMethod(LinkMovementMethod.getInstance());

        activityPrivacyPolicyBinding.appCompatButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PrivacyPolicyActivity.this, SubscriptionActivity.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}