package com.example.statussaver.Utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.statussaver.R;
import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnPaidEventListener;
import com.google.android.gms.ads.ResponseInfo;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

public class AdManager {
    private Activity mContext;
    private RewardedAd mRewardedAd;
    private TemplateView mNativeAdView;
    private AppOpenAdManager appOpenAdManager;
    private InterstitialAd mInterstitialAd;

    public AdManager(Activity context) {
        mContext = context;
    }
    public AdManager(Activity context, TemplateView adView) {
        mContext = context;
        mNativeAdView = adView;
    }

    public void showAd(AdType adType) {
        switch (adType) {
            case BANNER:
                showBannerAd();
                break;
            case INTERSTITIAL:
                showInterstitialAd();
                break;
            case REWARDED:
                showRewardedAd();
                break;
            case NATIVE:
                showNativeAd();
                break;
            case OPENAPP:
                showOpenAppAd();
                break;
            default:
                break;
        }
    }


    private class AppOpenAdManager {
        private static final String LOG_TAG = "AppOpenAdManager";
        private static final String AD_UNIT_ID = "ca-app-pub-3940256099942544/9257395921";

        private AppOpenAd appOpenAd = null;
        private boolean isLoadingAd = false;
        private boolean isShowingAd = false;

        public AppOpenAdManager() {}

        public void loadAd() {

            if (isLoadingAd || isAdAvailable()) {
                return;
            }

            isLoadingAd = true;
            AdRequest request = new AdRequest.Builder().build();
            AppOpenAd.load(
                    mContext, AD_UNIT_ID, request,
                    AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT,
                    new AppOpenAd.AppOpenAdLoadCallback() {
                        @Override
                        public void onAdLoaded(AppOpenAd ad) {
                            // Called when an app open ad has loaded.
                            Log.d(LOG_TAG, "Ad was loaded.");
                            appOpenAd = ad;
                            isLoadingAd = false;
                        }

                        @Override
                        public void onAdFailedToLoad(LoadAdError loadAdError) {
                            // Called when an app open ad has failed to load.
                            Log.d(LOG_TAG, loadAdError.getMessage());
                            isLoadingAd = false;
                        }
                    });
        }
        private boolean isAdAvailable() {
            return appOpenAd != null;
        }
    }
    private void showOpenAppAd() {
        appOpenAdManager = new AppOpenAdManager();
        appOpenAdManager.loadAd();
    }


    private void showNativeAd() {

        AdLoader adLoader = new AdLoader.Builder(mContext, "ca-app-pub-3940256099942544/2247696110")
                .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                    @Override
                    public void onNativeAdLoaded(NativeAd nativeAd) {
                        NativeTemplateStyle styles = new NativeTemplateStyle.Builder().build();
                        mNativeAdView.setStyles(styles);
                        mNativeAdView.setNativeAd(nativeAd);
                    }
                })
                .withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(LoadAdError loadAdError) {
                    }
                })
                .withNativeAdOptions(new NativeAdOptions.Builder().build())
                .build();

        adLoader.loadAd(new AdRequest.Builder().build());
    }

    private void showBannerAd() {
        AdView adView = new AdView(mContext);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");

        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    private void showInterstitialAd() {
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(mContext, "ca-app-pub-3940256099942544/1033173712", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd loadedInterstitialAd) {
                        if (loadedInterstitialAd != null) {
                            loadedInterstitialAd.show(mContext);
                        } else {
                            Log.d("TAG", "The interstitial ad object is null");
                        }
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        Log.d("TAG", loadAdError.toString());
                    }

                });
    }

    private void showRewardedAd() {
        if (mRewardedAd == null) {
            RewardedAd.load(mContext, "ca-app-pub-3940256099942544/5224354917",
                    new AdRequest.Builder().build(), new RewardedAdLoadCallback() {
                        @Override
                        public void onAdLoaded(RewardedAd rewardedAd) {
                            mRewardedAd = rewardedAd;
                            if (mRewardedAd != null) {
                                mRewardedAd.show(mContext, rewardItem -> {
                                    // Handle the reward item
                                });
                            } else {
                                Log.d("TAG", "The rewarded ad object is null");
                            }
                        }
                    });
        } else {
            mRewardedAd.show(mContext, rewardItem -> {
                // Handle the reward item
            });
        }
    }

    public enum AdType {
        BANNER,
        INTERSTITIAL,
        REWARDED,
        NATIVE,
        OPENAPP
    }
}
