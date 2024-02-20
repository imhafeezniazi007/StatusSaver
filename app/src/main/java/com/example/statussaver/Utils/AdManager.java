package com.example.statussaver.Utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

public class AdManager {
    private Activity mContext;
    private RewardedAd mRewardedAd;

    public AdManager(Activity context) {
        mContext = context;
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
            default:
                break;
        }
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
        REWARDED
    }
}
