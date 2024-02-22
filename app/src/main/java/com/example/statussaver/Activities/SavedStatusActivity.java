package com.example.statussaver.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import com.example.statussaver.Adapters.SavedStatusAdapter;
import com.example.statussaver.Fragments.ImageFragment;
import com.example.statussaver.Fragments.SavedImageFragment;
import com.example.statussaver.Fragments.SavedVideoFragment;
import com.example.statussaver.Models.MediaFile;
import com.example.statussaver.R;
import com.example.statussaver.Utils.AdManager;
import com.example.statussaver.Utils.Consts;
import com.example.statussaver.databinding.ActivitySavedStatusBinding;
import com.google.android.gms.ads.AdView;
import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class SavedStatusActivity extends AppCompatActivity {

    ActivitySavedStatusBinding activitySavedStatusBinding;
    ArrayList<MediaFile> arrayList;
    SavedStatusAdapter savedStatusAdapter;
    private AdManager adManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activitySavedStatusBinding = ActivitySavedStatusBinding.inflate(getLayoutInflater());
        setContentView(activitySavedStatusBinding.getRoot());
        activitySavedStatusBinding.toolbar.setTitle("Saved Statuses");
        activitySavedStatusBinding.toolbar.setNavigationIcon(R.drawable.back);
        activitySavedStatusBinding.toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        setSupportActionBar(activitySavedStatusBinding.toolbar);

        activitySavedStatusBinding.tabLayout.addTab(activitySavedStatusBinding.tabLayout.newTab().setText("Pics"));
        activitySavedStatusBinding.tabLayout.addTab(activitySavedStatusBinding.tabLayout.newTab().setText("Videos"));
        activitySavedStatusBinding.tabLayout.setBackgroundColor(Color.parseColor("#00CC77"));
        showBannerAd();

        activitySavedStatusBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adManager = new AdManager(SavedStatusActivity.this);
                adManager.showAd(AdManager.AdType.INTERSTITIAL);
                finish();
            }
        });


        replaceFragment(new SavedImageFragment());

        activitySavedStatusBinding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new SavedImageFragment();
                        break;
                    case 1:
                        fragment = new SavedVideoFragment();
                        break;
                    default:
                        fragment = new SavedImageFragment();
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

    private void showBannerAd() {
        AdView adView = activitySavedStatusBinding.adView;
        adManager = new AdManager(SavedStatusActivity.this, adView);
        adManager.showAd(AdManager.AdType.BANNER);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    public Bitmap getThumbnail(MediaFile file) {
        if (file.isVideo()) {
            return ThumbnailUtils.createVideoThumbnail(file.getFile().getAbsolutePath(),
                    MediaStore.Video.Thumbnails.MICRO_KIND);
        } else {
            return ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(file.getFile().getAbsolutePath())
                    , Consts.THUMBSIZE
                    , Consts.THUMBSIZE);
        }
    }

    public void navigateToThirdFragment(MediaFile status) {
        Intent intent = new Intent(getApplicationContext(), SavedImageViewActivity.class);
        intent.putExtra("image_path", status.getPath());
        intent.putExtra("title", status.getName());
        startActivity(intent);
    }

    public void navigateToForthFragment(MediaFile status) {
        Intent intent = new Intent(getApplicationContext(), SavedVideoViewActivity.class);
        intent.putExtra("image_path", status.getPath());
        intent.putExtra("title", status.getName());
        startActivity(intent);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        adManager = new AdManager(SavedStatusActivity.this);
        adManager.showAd(AdManager.AdType.INTERSTITIAL);
        finish();
    }
}
