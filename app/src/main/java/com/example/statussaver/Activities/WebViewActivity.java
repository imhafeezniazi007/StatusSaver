package com.example.statussaver.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.statussaver.R;
import com.example.statussaver.databinding.ActivityWebViewBinding;

import org.w3c.dom.Document;

import java.util.Timer;
import java.util.TimerTask;

public class WebViewActivity extends AppCompatActivity {

    ActivityWebViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWebViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.toolbar.setTitle("WhatsWeb");
        binding.toolbar.setNavigationIcon(R.drawable.back);
        binding.toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        setSupportActionBar(binding.toolbar);

        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WebViewActivity.this, MainFeaturesActivity.class));
                finish();
            }
        });

        binding.webView.getSettings().setJavaScriptEnabled(true);

        binding.webView.setWebViewClient(new WebViewClient());
        binding.webView.getSettings().setUseWideViewPort(true);
        binding.webView.getSettings().setUserAgentString("Mozilla/5.0 (Linux; Win64; x64; rv:46.0) Gecko/20100101 Firefox/68.0");
        binding.webView.getSettings().setGeolocationEnabled(true);
        binding.webView.getSettings().setDomStorageEnabled(true);
        binding.webView.getSettings().setDatabaseEnabled(true);
        binding.webView.getSettings().setSupportMultipleWindows(true);
        binding.webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        binding.webView.getSettings().setNeedInitialFocus(true);
        binding.webView.getSettings().setLoadWithOverviewMode(true);
        binding.webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        binding.webView.getSettings().setBlockNetworkImage(true);
        binding.webView.getSettings().setBuiltInZoomControls(true);
        binding.webView.setInitialScale(100);

        hideElementsByClassName(binding.webView);
    }

    public static void hideElementsByClassName(WebView webView) {
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                String cssToInject = "";
                String[] classNamesToHide = {"_3AjBo", "_2XHqw", "landing-wrapper-before", "landing-header", "_10aH-"};
                for (String className : classNamesToHide) {
                    cssToInject += "." + className + " { display: none !important; } ";
                }

                String js = "javascript:(function() { " +
                        "var parent = document.getElementsByTagName('head').item(0);" +
                        "var style = document.createElement('style');" +
                        "style.type = 'text/css';" +
                        "style.innerHTML = '" + cssToInject + "';" +
                        "parent.appendChild(style);" +
                        "})();";

                view.loadUrl(js);
            }
        });

        webView.loadUrl("https://web.whatsapp.com/");
    }

}
