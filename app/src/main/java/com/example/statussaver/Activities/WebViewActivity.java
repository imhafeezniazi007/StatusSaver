package com.example.statussaver.Activities;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.statussaver.R;
import com.example.statussaver.Utils.AdManager;
import com.example.statussaver.Utils.ProgressImageDialog;
import com.example.statussaver.databinding.ActivityWebViewBinding;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnPaidEventListener;
import com.google.android.gms.ads.ResponseInfo;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class WebViewActivity extends AppCompatActivity {

    ActivityWebViewBinding binding;
    ProgressImageDialog progressDialog;
    private AdManager adManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWebViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.toolbar.setTitle("WhatsWeb");
        binding.toolbar.setNavigationIcon(R.drawable.back);
        binding.toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        setSupportActionBar(binding.toolbar);

        binding.webView.getSettings().setJavaScriptEnabled(true);


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

        //showOpenAppAd();

        progressDialog = new ProgressImageDialog(WebViewActivity.this);
        progressDialog.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
            }
        }, 11000);


        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adManager = new AdManager(WebViewActivity.this);
                adManager.showAd(AdManager.AdType.INTERSTITIAL);
                finish();
            }
        });

    }

    private void showAd() {
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        adManager = new AdManager(WebViewActivity.this);
        adManager.showAd(AdManager.AdType.INTERSTITIAL);
        finish();
    }
}
