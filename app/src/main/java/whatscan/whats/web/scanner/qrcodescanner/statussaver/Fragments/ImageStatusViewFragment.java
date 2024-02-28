package whatscan.whats.web.scanner.qrcodescanner.statussaver.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.List;

import whatscan.whats.web.scanner.qrcodescanner.statussaver.Models.Status;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.R;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.Utils.Consts;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.databinding.FragmentImageStatusViewBinding;

public class ImageStatusViewFragment extends Fragment {

    FragmentImageStatusViewBinding fragmentImageStatusViewBinding;
    List<Status> statusList;
    Context context;
    Status mStatus;

    public ImageStatusViewFragment(Status status)
    {
        this.mStatus = status;

    }
    public ImageStatusViewFragment(List<Status> statusList) {
        this.statusList = statusList;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_status_view, container, false);
        fragmentImageStatusViewBinding = FragmentImageStatusViewBinding.bind(view);
        if (getArguments() != null) {
            String imagePath = getArguments().getString("image_path");
            if (imagePath != null) {

                Bitmap bitmap = BitmapFactory.decodeFile(imagePath);

                if (bitmap != null) {
                    fragmentImageStatusViewBinding.imageView2.setImageBitmap(bitmap);
                } else {
                    Log.e("ThirdFragment", imagePath);
                }
            }
        } else {
            Log.e("ThirdFragment", "Image path is null.");
        }

        fragmentImageStatusViewBinding.logo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getArguments() != null) {
                    try {
                        downloadImage(mStatus);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        fragmentImageStatusViewBinding.logo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                Uri screenshotUri = Uri.parse(mStatus.getPath());

                sharingIntent.setType("image/jpeg");
                sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
                startActivity(Intent.createChooser(sharingIntent, "Share image using"));

            }
        });

        return view;
    }


    public void downloadImage(Status status) throws IOException {
        File file = new File(Consts.APP_DIR);
        if (!file.exists())
        {
            file.mkdirs();
        }
        File destFile = new File(file+File.separator + status.getTitle());
        if (destFile.exists())
        {
            destFile.delete();
        }

        copyFile(status.getFile(),destFile);

        Toast.makeText(getContext(), "Download complete...", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(Uri.fromFile(destFile));
        getActivity().sendBroadcast(intent);
    }

    private void copyFile(File file, File destFile) throws IOException {
        if (!destFile.getParentFile().exists())
        {
            destFile.getParentFile().mkdirs();
        }
        if (!destFile.exists())
        {
            destFile.createNewFile();
        }

        FileChannel source = null;
        FileChannel destination = null;

        source = new FileInputStream(file).getChannel();
        destination = new FileOutputStream(destFile).getChannel();
        destination.transferFrom(source,0,source.size());

        source.close();
        destination.close();
    }

}