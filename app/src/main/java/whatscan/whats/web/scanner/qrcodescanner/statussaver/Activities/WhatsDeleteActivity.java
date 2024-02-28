package whatscan.whats.web.scanner.qrcodescanner.statussaver.Activities;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.ads.nativetemplates.TemplateView;

import whatscan.whats.web.scanner.qrcodescanner.statussaver.OnAdsCallback;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.R;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.Utils.AdManager;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.Utils.Consts;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.Utils.ItemCounter;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.Utils.NotificationDAO;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.Utils.NotificationDatabase;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.Utils.NotificationListenService;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.databinding.ActivityWhatsDeleteBinding;

public class WhatsDeleteActivity extends AppCompatActivity {

    ActivityWhatsDeleteBinding activityWhatsDeleteBinding;
    NotificationDAO notificationDAO;
    private static final int REQUEST_NOTIFICATION_ACCESS = 1;
    private AdManager adManager;
    private ItemCounter itemCounter;

    private boolean isAdShown = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityWhatsDeleteBinding = ActivityWhatsDeleteBinding.inflate(getLayoutInflater());
        setContentView(activityWhatsDeleteBinding.getRoot());
        activityWhatsDeleteBinding.toolbar.setTitle("Whats Delete");
        activityWhatsDeleteBinding.toolbar.setNavigationIcon(R.drawable.back);
        activityWhatsDeleteBinding.toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        setSupportActionBar(activityWhatsDeleteBinding.toolbar);

        if (!isNotificationServiceEnabled()) {
            requestNotificationAccess();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                NotificationDatabase notificationDatabase = NotificationDatabase.getInstance(WhatsDeleteActivity.this);
                notificationDAO = notificationDatabase.notificationDao();

                int totalSenders = notificationDAO.getTotalSenders();
                activityWhatsDeleteBinding.messagesTextview.setText(totalSenders+ " Messages");
            }
        }).start();

        activityWhatsDeleteBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isAdShown) {
                    showInterstitialAd();
                }else {
                    finish();
                }
            }
        });

        itemCounter = new ItemCounter();

        activityWhatsDeleteBinding.imagesTextview.setText(itemCounter.countItems(Consts.IMG_FILE_DIR)+ " Images");
        activityWhatsDeleteBinding.videosTextview.setText(itemCounter.countItems(Consts.VID_FILE_DIR)+ " Videos");
        activityWhatsDeleteBinding.audiosTextview.setText(itemCounter.countItems(Consts.AUD_FILE_DIR)+ " Audios");
        activityWhatsDeleteBinding.documentsTextview.setText(itemCounter.countItems(Consts.DOC_FILE_DIR)+ " Documents");
        activityWhatsDeleteBinding.voicesTextview.setText(itemCounter.countItems(Consts.VOICE_FILE_DIR)+ " Voices");

        setClickListener(activityWhatsDeleteBinding.relativeLayout1, ChatsActivity.class);
        setClickListener(activityWhatsDeleteBinding.relativeLayout2, ImageActivity.class);
        setClickListener(activityWhatsDeleteBinding.relativeLayout3, VideoActivity.class);
        setClickListener(activityWhatsDeleteBinding.relativeLayout4, AudioActivity.class);
        setClickListener(activityWhatsDeleteBinding.relativeLayout5, DocumentActivity.class);
        setClickListener(activityWhatsDeleteBinding.relativeLayout6, VoiceActivity.class);

        showNativeAd();
    }


    private void showInterstitialAd() {
            AdManager adManager = new AdManager(WhatsDeleteActivity.this);
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

    private void showNativeAd() {
        TemplateView nativeAdView = activityWhatsDeleteBinding.nativeWhatsDeleteAd;

        adManager = new AdManager(this, nativeAdView);
        adManager.showAd(AdManager.AdType.NATIVE);
    }

    private void setClickListener(View view, final Class<?> activityClass) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WhatsDeleteActivity.this, activityClass));
            }
        });
    }



    private boolean isNotificationServiceEnabled() {
        ComponentName cn = new ComponentName(this, NotificationListenService.class);
        String flat = Settings.Secure.getString(getContentResolver(), "enabled_notification_listeners");
        return flat != null && flat.contains(cn.flattenToString());
    }

    private void requestNotificationAccess() {
        if (!isFinishing()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Permission Needed");
            builder.setMessage("This feature needs notification access to function");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);
                    startActivityForResult(intent, REQUEST_NOTIFICATION_ACCESS);
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    Toast.makeText(WhatsDeleteActivity.this, "Permission denied", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });

            AlertDialog dialog = builder.create();
            if (!isFinishing()) {
                dialog.setCancelable(false);
                dialog.show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_NOTIFICATION_ACCESS) {
            if (isNotificationServiceEnabled()) {
                Log.e("_serv", "onActivityResult: service started successfully" );
            } else {
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (!isAdShown) {
            showInterstitialAd();
        } else {
            super.onBackPressed();
        }
    }
}