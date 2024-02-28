package whatscan.whats.web.scanner.qrcodescanner.statussaver.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.material.navigation.NavigationView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

import whatscan.whats.web.scanner.qrcodescanner.statussaver.R;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.Utils.AdManager;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.databinding.ActivityMainFeaturesBinding;

public class MainFeaturesActivity extends AppCompatActivity {

    ActivityMainFeaturesBinding binding;
    private InterstitialAd interstitialAd;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private int CHECK_PERMISSIONS;
    private AdManager adManager;
    private AppCompatButton btnExit, btnCancel, btnFeedback;
    private EditText subjectEt, messageEt;
    private ImageView imageView;
    private boolean isFirstBack = true;
    private boolean isFeedbackShown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainFeaturesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.toolbar.setTitle("Whatscan");
        binding.toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        setSupportActionBar(binding.toolbar);

        binding.premiumLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainFeaturesActivity.this, SubscriptionActivity.class));
            }
        });
        drawerLayout = binding.drawerLayout;
        navigationView = binding.navView;

        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            requestPermissionsIfNecessary();
        } else {
            requestLegacyPermissions();
        }

        showNativeAd();

        showNavigation();

        setOnClickListenersForCards();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            showNavigation();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showNavigation() {
        Menu menu = navigationView.getMenu();

        menu.findItem(R.id.nav_item_one).setIcon(R.drawable.share);
        menu.findItem(R.id.nav_item_two).setIcon(R.drawable.feedback);
        menu.findItem(R.id.nav_item_three).setIcon(R.drawable.privacy_policy);
        menu.findItem(R.id.nav_item_four).setIcon(R.drawable.rate);
        menu.findItem(R.id.nav_item_five).setIcon(R.drawable.send);
        menu.findItem(R.id.nav_item_six).setIcon(R.drawable.whatsweb_icon_drawer);
        menu.findItem(R.id.nav_item_seven).setIcon(R.drawable.qrscanner_drawer);
        menu.findItem(R.id.nav_item_eight).setIcon(R.drawable.premium);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.nav_item_one) {

                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_TEXT, "market://details?id=com.whatsapp");
                    intent.setType("text/plain");
                    startActivity(Intent.createChooser(intent, "Share link via"));

                } else if (menuItem.getItemId() == R.id.nav_item_two) {
                    setContentView(R.layout.layout_feedback);
                    isFeedbackShown = true;

                    btnFeedback = findViewById(R.id.btn_feedback);
                    messageEt = findViewById(R.id.message_feedback);
                    subjectEt = findViewById(R.id.subject_feedback);

                    btnFeedback.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String recipientEmail = getString(R.string.email_feedback);

                            Intent intent = new Intent(Intent.ACTION_SENDTO);
                            intent.setData(Uri.parse("mailto:"));
                            intent.putExtra(Intent.EXTRA_EMAIL, recipientEmail);
                            intent.putExtra(Intent.EXTRA_SUBJECT, subjectEt.getText());
                            intent.putExtra(Intent.EXTRA_TEXT, messageEt.getText());
                            if (subjectEt.getText().equals("")) {
                                subjectEt.setError("Please enter text");
                            } else if (messageEt.getText().equals("")) {
                                messageEt.setError("Please enter text");
                            } else {
                                if (intent.resolveActivity(getPackageManager()) != null) {
                                    startActivity(intent);
                                }
                            }
                        }
                    });

                } else if (menuItem.getItemId() == R.id.nav_item_three) {
                    String privacy_policy_website = getString(R.string.privacy_policy_website);

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(privacy_policy_website));
                    startActivity(intent);

                } else if (menuItem.getItemId() == R.id.nav_item_four) {

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("market://details?id=com.whatsapp"));
                    startActivity(intent);
                } else if (menuItem.getItemId() == R.id.nav_item_five) {
                    binding.cardView3.performClick();
                } else if (menuItem.getItemId() == R.id.nav_item_six) {
                    binding.cardView.performClick();
                } else if (menuItem.getItemId() == R.id.nav_item_seven) {
                    binding.cardView5.performClick();
                } else if (menuItem.getItemId() == R.id.nav_item_eight) {
                    startActivity(new Intent(MainFeaturesActivity.this, SubscriptionActivity.class));
                }

                menuItem.setChecked(true);
                setTitle(menuItem.getTitle());
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    private void showNativeAd() {
        TemplateView nativeAdView = binding.nativeMainad;

        AdManager adManager = new AdManager(this, nativeAdView);
        adManager.showAd(AdManager.AdType.NATIVE);
    }


    private void requestLegacyPermissions() {
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{permission}, CHECK_PERMISSIONS);
                return;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CHECK_PERMISSIONS) {
            boolean allPermissionsGranted = true;
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    allPermissionsGranted = false;
                    break;
                }
            }
            if (!allPermissionsGranted) {
                Toast.makeText(this, "Permissions denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    private void requestPermissionsIfNecessary() {
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.MANAGE_EXTERNAL_STORAGE};
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
            } else {
                Dexter.withContext(this)
                        .withPermissions(
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.MANAGE_EXTERNAL_STORAGE
                        )
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();
            }
        }
    }
    private void setClickListener(View view, final Class<?> activityClass) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainFeaturesActivity.this, activityClass));
            }
        });
    }

    private void setOnClickListenersForCards() {
        setClickListener(binding.cardView, WebViewActivity.class);
        setClickListener(binding.cardView2, BusinessStatusActivity.class);
        setClickListener(binding.cardView3, DirectChatActivity.class);
        setClickListener(binding.cardView4, WhatsDeleteActivity.class);
        setClickListener(binding.cardView5, QRScannerActivity.class);
        setClickListener(binding.cardView6, MainActivity.class);
        setClickListener(binding.cardView7, SavedStatusActivity.class);
    }

    private void exitActivity() {
        btnCancel = findViewById(R.id.btnBtnCancel);
        btnExit = findViewById(R.id.btnBtnExit);
        imageView = findViewById(R.id.rating_img);

        TemplateView nativeAdView = findViewById(R.id.nativeExitAd);

        AdManager adManager = new AdManager(this, nativeAdView);
        adManager.showAd(AdManager.AdType.NATIVE);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=com.whatsapp"));
                startActivity(intent);
                finish();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFirstBack = true;
                setContentView(binding.getRoot());
            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }

        if (isFeedbackShown) {
            setContentView(binding.getRoot());
            showNativeAd();
            showNavigation();
            setOnClickListenersForCards();
            isFeedbackShown = false;
        }
        else {
            if (isFirstBack) {
                isFirstBack = false;
                setContentView(R.layout.layout_exit_screen);
                exitActivity();
            } else {
                super.onBackPressed();
            }
        }
    }
}