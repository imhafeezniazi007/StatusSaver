package com.example.statussaver.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.statussaver.Fragments.BusinessImageFragment;
import com.example.statussaver.Fragments.BusinessImageViewFragment;
import com.example.statussaver.Fragments.BusinessVideoFragment;
import com.example.statussaver.Fragments.BusinessVideoViewFragment;
import com.example.statussaver.Fragments.ImageFragment;
import com.example.statussaver.Fragments.ImageStatusViewFragment;
import com.example.statussaver.Fragments.VideoFragment;
import com.example.statussaver.Fragments.VideoStatusViewFragment;
import com.example.statussaver.Models.Status;
import com.example.statussaver.R;
import com.example.statussaver.Utils.AdManager;
import com.example.statussaver.databinding.ActivityBusinessStatusBinding;
import com.example.statussaver.databinding.ActivitySavedStatusBinding;
import com.google.android.material.tabs.TabLayout;

import java.io.Serializable;

public class BusinessStatusActivity extends AppCompatActivity {

    ActivityBusinessStatusBinding activityBusinessStatusBinding;
    private AdManager adManager;
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


        activityBusinessStatusBinding.tabLayout.addTab(activityBusinessStatusBinding.tabLayout.newTab().setText("Pics"));
        activityBusinessStatusBinding.tabLayout.addTab(activityBusinessStatusBinding.tabLayout.newTab().setText("Videos"));

        replaceFragment(new BusinessImageFragment());
        activityBusinessStatusBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adManager = new AdManager(BusinessStatusActivity.this);
                adManager.showAd(AdManager.AdType.INTERSTITIAL);
                finish();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        adManager = new AdManager(this);
        adManager.showAd(AdManager.AdType.INTERSTITIAL);
        finish();
    }
}