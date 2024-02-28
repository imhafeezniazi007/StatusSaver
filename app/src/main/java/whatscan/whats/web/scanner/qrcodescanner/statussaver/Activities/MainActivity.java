package whatscan.whats.web.scanner.qrcodescanner.statussaver.Activities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;

import whatscan.whats.web.scanner.qrcodescanner.statussaver.Fragments.ImageFragment;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.Fragments.ImageStatusViewFragment;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.Fragments.VideoFragment;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.Fragments.VideoStatusViewFragment;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.Models.Status;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.OnAdsCallback;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.R;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.Utils.AdManager;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    ActivityMainBinding activityMainBinding;
    private AdManager adManager;
    private boolean isAdShown = false;

    public MainActivity(AdManager adManager) {
        this.adManager = adManager;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
        setSupportActionBar(activityMainBinding.toolbar);
        activityMainBinding.toolbar.setTitle("Status Saver");
        activityMainBinding.toolbar.setNavigationIcon(R.drawable.back);
        activityMainBinding.toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));


        activityMainBinding.tabLayout.addTab(activityMainBinding.tabLayout.newTab().setText("Pics"));
        activityMainBinding.tabLayout.addTab(activityMainBinding.tabLayout.newTab().setText("Videos"));
        activityMainBinding.tabLayout.setBackgroundColor(Color.parseColor("#00CC77"));

        activityMainBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isAdShown) {
                    showInterstitialAd();
                }else {
                    finish();
                }
            }
        });


        replaceFragment(new ImageFragment());

        activityMainBinding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new ImageFragment();
                        break;
                    case 1:
                        fragment = new VideoFragment();
                        break;
                    default:
                        fragment = new ImageFragment();
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
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }


    public void navigateToThirdFragment(Status status) {
        Bundle bundle = new Bundle();
        bundle.putString("image_path", status.getPath());
        bundle.putString("title", status.getTitle());


        ImageStatusViewFragment imageStatusViewFragment = new ImageStatusViewFragment(status);
        imageStatusViewFragment.setArguments(bundle);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(activityMainBinding.fragmentContainer.getId(), imageStatusViewFragment);
        transaction.addToBackStack(null);
        transaction.commit();
        }

    public void navigateToForthFragment(Status status) {
        Bundle bundle = new Bundle();
        bundle.putString("path", status.getPath());


        VideoStatusViewFragment videoStatusViewFragment = new VideoStatusViewFragment(status);
        videoStatusViewFragment.setArguments(bundle);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(activityMainBinding.fragmentContainer.getId(), videoStatusViewFragment);
        transaction.addToBackStack(null);
        transaction.commit();
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
        AdManager adManager = new AdManager(MainActivity.this);
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
