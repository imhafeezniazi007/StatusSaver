package whatscan.whats.web.scanner.qrcodescanner.statussaver.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.ads.nativetemplates.TemplateView;

import whatscan.whats.web.scanner.qrcodescanner.statussaver.OnAdsCallback;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.R;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.Utils.AdManager;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.databinding.ActivityDirectChatBinding;

public class DirectChatActivity extends AppCompatActivity {

    ActivityDirectChatBinding activityDirectChatBinding;
    private AdManager adManager;
    private boolean isAdShown = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDirectChatBinding = ActivityDirectChatBinding.inflate(getLayoutInflater());
        setContentView(activityDirectChatBinding.getRoot());
        activityDirectChatBinding.toolbar.setTitle("Direct Chat");
        activityDirectChatBinding.toolbar.setNavigationIcon(R.drawable.back);
        activityDirectChatBinding.toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        setSupportActionBar(activityDirectChatBinding.toolbar);

        showNativeAd();
        activityDirectChatBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isAdShown) {
                    adManager = new AdManager(DirectChatActivity.this);
                    adManager.showAd(AdManager.AdType.INTERSTITIAL, new OnAdsCallback() {
                        @Override
                        public void onDismiss() {
                            finish();
                        }

                        @Override
                        public void onError(String err) {

                        }
                    });
                }else {
                    finish();
                }
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

    private void showNativeAd() {
        TemplateView nativeAdView = activityDirectChatBinding.nativeDirectChatAd;

        adManager = new AdManager(this, nativeAdView);
        adManager.showAd(AdManager.AdType.NATIVE);
    }

    @Override
    public void onBackPressed() {
        if (!isAdShown) {
            showInterstitialAd();
        } else {
            super.onBackPressed();
        }
    }

    private void showInterstitialAd() {
        AdManager adManager = new AdManager(DirectChatActivity.this);
        adManager.showAd(AdManager.AdType.INTERSTITIAL, new OnAdsCallback() {
                        @Override
                        public void onDismiss() {
                            finish();
                        }

                        @Override
                        public void onError(String err) {

                        }
                    });
        isAdShown = true;
    }
}