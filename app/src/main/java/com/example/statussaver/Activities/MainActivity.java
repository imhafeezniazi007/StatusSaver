package com.example.statussaver.Activities;

import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
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
    private static final int READ_STORAGE_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
        setSupportActionBar(activityMainBinding.toolbar);

        activityMainBinding.tabLayout.addTab(activityMainBinding.tabLayout.newTab().setText("Pics"));
        activityMainBinding.tabLayout.addTab(activityMainBinding.tabLayout.newTab().setText("Videos"));

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

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    READ_STORAGE_PERMISSION_REQUEST_CODE);
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case READ_STORAGE_PERMISSION_REQUEST_CODE: {
                // Check if the permission was granted
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted, you can now access the storage
                } else {
                    Toast.makeText(this, "permission not granted", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }


    public void navigateToThirdFragment(String bitmap, int position) {
        Bundle bundle = new Bundle();
        bundle.putString("image_path", bitmap);
        bundle.putInt("position", position);


        ImageStatusViewFragment imageStatusViewFragment = new ImageStatusViewFragment();
        imageStatusViewFragment.setArguments(bundle);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(activityMainBinding.fragmentContainer.getId(), imageStatusViewFragment);
        transaction.addToBackStack(null);
        transaction.commit();
        }

    public void navigateToForthFragment(String path) {
        Bundle bundle = new Bundle();
        bundle.putString("path", path);


        VideoStatusViewFragment videoStatusViewFragment = new VideoStatusViewFragment();
        videoStatusViewFragment.setArguments(bundle);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(activityMainBinding.fragmentContainer.getId(), videoStatusViewFragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }
}
