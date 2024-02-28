package whatscan.whats.web.scanner.qrcodescanner.statussaver.Activities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.ads.AdView;
import com.google.android.material.tabs.TabLayout;

import whatscan.whats.web.scanner.qrcodescanner.statussaver.Fragments.BusinessImageFragment;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.Fragments.BusinessImageViewFragment;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.Fragments.BusinessVideoFragment;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.Fragments.BusinessVideoViewFragment;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.Models.Status;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.OnAdsCallback;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.R;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.Utils.AdManager;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.databinding.ActivityBusinessStatusBinding;

public class BusinessStatusActivity extends AppCompatActivity {

    ActivityBusinessStatusBinding activityBusinessStatusBinding;
    private AdManager adManager;
    private boolean isAdShown = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityBusinessStatusBinding = ActivityBusinessStatusBinding.inflate(getLayoutInflater());
        setContentView(activityBusinessStatusBinding.getRoot());
        activityBusinessStatusBinding.toolbar.setTitle("Business Status");
        activityBusinessStatusBinding.toolbar.setNavigationIcon(R.drawable.back);
        activityBusinessStatusBinding.tabLayout.setBackgroundColor(Color.parseColor("#00CC77"));
        activityBusinessStatusBinding.toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        setSupportActionBar(activityBusinessStatusBinding.toolbar);


        showBannerAd();

        activityBusinessStatusBinding.tabLayout.addTab(activityBusinessStatusBinding.tabLayout.newTab().setText("Pics"));
        activityBusinessStatusBinding.tabLayout.addTab(activityBusinessStatusBinding.tabLayout.newTab().setText("Videos"));

        replaceFragment(new BusinessImageFragment());
        activityBusinessStatusBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isAdShown) {
                    showInterstitialAd();
                }else {
                    finish();
                }
            }
        });


        activityBusinessStatusBinding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new BusinessImageFragment();
                        break;
                    case 1:
                        fragment = new BusinessVideoFragment();
                        break;
                    default:
                        fragment = new BusinessImageFragment();
                        break;
                }
                replaceFragment(fragment);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_business, fragment);
        transaction.commit();
    }


    public void navigateToThirdFragment(Status status) {
        Bundle bundle = new Bundle();
        bundle.putString("image_path", status.getPath());
        bundle.putString("title", status.getTitle());


        BusinessImageViewFragment businessImageViewFragment = new BusinessImageViewFragment(status);
        businessImageViewFragment.setArguments(bundle);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(activityBusinessStatusBinding.fragmentContainerBusiness.getId(), businessImageViewFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void navigateToForthFragment(Status status) {
        Bundle bundle = new Bundle();
        bundle.putString("path", status.getPath());

        BusinessVideoViewFragment businessVideoViewFragment = new BusinessVideoViewFragment(status);
        businessVideoViewFragment.setArguments(bundle);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(activityBusinessStatusBinding.fragmentContainerBusiness.getId(), businessVideoViewFragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }
    private void showBannerAd() {
//        AdView adView = activityBusinessStatusBinding.adView;
//        adManager = new AdManager(BusinessStatusActivity.this, adView);
//        adManager.showAd(AdManager.AdType.BANNER);
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
        AdManager adManager = new AdManager(BusinessStatusActivity.this);
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