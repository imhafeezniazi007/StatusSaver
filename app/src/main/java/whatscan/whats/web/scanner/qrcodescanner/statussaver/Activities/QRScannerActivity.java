package whatscan.whats.web.scanner.qrcodescanner.statussaver.Activities;


import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.ScanOptions;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import whatscan.whats.web.scanner.qrcodescanner.statussaver.Adapters.QRScanAdapter;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.Models.QRScan;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.OnAdsCallback;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.R;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.Utils.AdManager;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.Utils.DBHelper;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.databinding.ActivityQrscannerBinding;

public class QRScannerActivity extends AppCompatActivity {

    ActivityQrscannerBinding activityQrscannerBinding;
    private static final int REQUEST_CAMERA_PERMISSION = 100;
    private static final int REQUEST_IMAGE_PICK = 101;
    private ListView listView;
    private List<QRScan> qrCodes;
    private QRScanAdapter adapter;
    private AdManager adManager;
    private boolean isAdShown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityQrscannerBinding = ActivityQrscannerBinding.inflate(getLayoutInflater());
        setContentView(activityQrscannerBinding.getRoot());
        activityQrscannerBinding.toolbar.setTitle("QR Scanner");
        activityQrscannerBinding.toolbar.setNavigationIcon(R.drawable.back);
        activityQrscannerBinding.toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        setSupportActionBar(activityQrscannerBinding.toolbar);

        activityQrscannerBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isAdShown) {
                    showInterstitialAd();
                }else {
                    finish();
                }
            }
        });

        DBHelper dbHelper = new DBHelper(this);
        List<QRScan> scanList = dbHelper.getAllQRCodes();
        QRScanAdapter adapter = new QRScanAdapter(this, scanList);
        activityQrscannerBinding.rvData.setAdapter(adapter);
        activityQrscannerBinding.rvData.setLayoutManager(new LinearLayoutManager(this));

        activityQrscannerBinding.rvData.addItemDecoration(new DividerItemDecoration(QRScannerActivity.this,
                DividerItemDecoration.VERTICAL));

        DividerItemDecoration itemDecorator = new DividerItemDecoration(QRScannerActivity.this, DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(QRScannerActivity.this, R.drawable.divider));

        activityQrscannerBinding.flash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withContext(QRScannerActivity.this)
                        .withPermission(Manifest.permission.CAMERA)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                startScanner();
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                                PermissionListener dialogPermissionListener =
                                        DialogOnDeniedPermissionListener.Builder
                                                .withContext(QRScannerActivity.this)
                                                .withTitle("Camera permission")
                                                .withMessage("Camera permission is needed to take picture QR Code")
                                                .withButtonText(android.R.string.ok)
                                                .build();
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                            }
                        }).check();
            }
        });

        activityQrscannerBinding.placeholder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImageFromGallery(v);
            }
        });


        showNativeAd();
    }

    private void showNativeAd() {
        TemplateView nativeAdView = activityQrscannerBinding.nativeSplashAd;

        adManager = new AdManager(QRScannerActivity.this, nativeAdView);
        adManager.showAd(AdManager.AdType.NATIVE);
    }

    public void pickImageFromGallery(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_IMAGE_PICK);
    }
    private void startScanner() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setOrientationLocked(true);
        integrator.setDesiredBarcodeFormats(ScanOptions.ALL_CODE_TYPES);
        integrator.setCaptureActivity(CapturePortraitQRActivity.class);
        integrator.initiateScan();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                handleQRCodeData(result.getContents());
                DBHelper databaseHelper = new DBHelper(this);
                SQLiteDatabase db = databaseHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("content", result.getContents());
                db.insert("qr_scans", null, values);
                db.close();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = decodeUriToBitmap(imageUri);
                if (bitmap != null) {
                    String qrResult = decodeQRCode(bitmap);
                    if (qrResult != null) {
                        handleQRCodeData(qrResult);
                    } else {
                        Toast.makeText(this, "No QR code found in the image", Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private Bitmap decodeUriToBitmap(Uri uri) throws FileNotFoundException {
        InputStream inputStream = getContentResolver().openInputStream(uri);
        return BitmapFactory.decodeStream(inputStream);
    }

    private String decodeQRCode(Bitmap bitmap) {
        int[] intArray = new int[bitmap.getWidth() * bitmap.getHeight()];
        bitmap.getPixels(intArray, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());

        RGBLuminanceSource source = new RGBLuminanceSource(bitmap.getWidth(), bitmap.getHeight(), intArray);
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source));

        Reader reader = new MultiFormatReader();
        try {
            Result result = reader.decode(binaryBitmap);
            return result.getText();
        } catch (NotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (ChecksumException | FormatException e) {
            throw new RuntimeException(e);
        }
    }

    private void showBottomSheet(String qrData) {
        View bottomSheetView = getLayoutInflater().inflate(R.layout.layer_qr_scanner, null);
        TextView textViewQRData = bottomSheetView.findViewById(R.id.text_qr_data);
        Button buttonCopy = bottomSheetView.findViewById(R.id.button_copy);

        textViewQRData.setText(qrData);

        buttonCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement copying text functionality here
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("QR Data", qrData);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(QRScannerActivity.this, "Text copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }

    private void handleQRCodeData(String qrData) {
        if (qrData != null && !qrData.isEmpty()) {
            showBottomSheet(qrData);
        } else {
            activityQrscannerBinding.textNoItem.setVisibility(View.VISIBLE);
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

    private void showInterstitialAd() {
        AdManager adManager = new AdManager(QRScannerActivity.this);
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