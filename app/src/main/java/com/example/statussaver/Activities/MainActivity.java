package com.example.statussaver.Activities;

import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.example.statussaver.Adapters.ImageAdapter;
import com.example.statussaver.Adapters.PagerAdapter;
import com.example.statussaver.Fragments.ImageFragment;
import com.example.statussaver.Fragments.ImageStatusViewFragment;
import com.example.statussaver.Fragments.VideoFragment;
import com.example.statussaver.Fragments.VideoStatusViewFragment;
import com.example.statussaver.Models.Status;
import com.example.statussaver.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.tabs.TabLayout;

import com.example.statussaver.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    ActivityMainBinding activityMainBinding;


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
                startActivity(new Intent(MainActivity.this, MainFeaturesActivity.class));
                finish();
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
}
