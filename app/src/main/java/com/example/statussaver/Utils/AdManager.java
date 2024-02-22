package com.example.statussaver.Utils;

import android.app.Activity;
import android.app.Application;
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
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
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
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;

    public AdManager(Activity context) {
        mContext = context;
    }
    public AdManager(Activity context, TemplateView adView) {
        mContext = context;
        mNativeAdView = adView;
    }
    public AdManager(Activity context, AdView adView) {
        mContext = context;
        mAdView = adView;
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
            default:
                break;
        }
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

        MobileAds.initialize(mContext, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
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
        NATIVE
    }
}
