package whatscan.whats.web.scanner.qrcodescanner.statussaver.Utils;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

import whatscan.whats.web.scanner.qrcodescanner.statussaver.OnAdsCallback;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.R;

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

    public void showAd(AdType adType, OnAdsCallback callback) {
        switch (adType) {
            case BANNER:
                showBannerAd();
                break;
            case INTERSTITIAL:
                showInterstitialAd(callback);
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
    public void showAd(AdType adType) {
        switch (adType) {
            case BANNER:
                showBannerAd();
                break;
            case INTERSTITIAL:
                showInterstitialAd(null);
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

        AdLoader adLoader = new AdLoader.Builder(mContext, mContext.getString(R.string.id_native_ad))
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

    boolean isAdDisableTemp = true;

    private void showInterstitialAd(OnAdsCallback callback) {
        if(callback == null)
            return;
        if (isAdDisableTemp) {
            callback.onDismiss();
            return;
        }
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(mContext, mContext.getString(R.string.id_interstitial_ad), adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd loadedInterstitialAd) {
                        if (loadedInterstitialAd != null) {
                            loadedInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                                @Override
                                public void onAdDismissedFullScreenContent() {
                                    callback.onDismiss();
                                }
                            });
                            loadedInterstitialAd.show(mContext);
                        } else {
                            Log.d("TAG", "The interstitial ad object is null");
                        }
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        Log.d("TAG", loadAdError.toString());
                        callback.onError(loadAdError.getMessage());
                    }

                });


    }

    private void showRewardedAd() {
        if (mRewardedAd == null) {
            RewardedAd.load(mContext, mContext.getString(R.string.id_rewarded_ad),
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
