package whatscan.whats.web.scanner.qrcodescanner.statussaver.Fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.interstitial.InterstitialAd;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import whatscan.whats.web.scanner.qrcodescanner.statussaver.Adapters.ImageAdapter;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.Models.Status;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.R;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.Utils.Consts;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.databinding.FragmentImageBinding;

public class ImageFragment extends Fragment {

    private RecyclerView recyclerView;
    ArrayList<Status> arrayList;
    ImageAdapter imageAdapter;
    Handler handler = new Handler();
    private InterstitialAd mInterstitialAd;
    FragmentImageBinding binding;

    public ImageFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        binding = FragmentImageBinding.bind(view);

        arrayList = new ArrayList<>();
        binding.imageRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        loadPictureStatuses();

        return view;
    }



    private void loadPictureStatuses() {
        if (Consts.STATUS_DIRECTORY.exists())
        {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    File[] statusFiles = Consts.STATUS_DIRECTORY.listFiles();

                    if (statusFiles!=null && statusFiles.length>0)
                    {
                        Arrays.sort(statusFiles);

                        for (final File statusFile:statusFiles)
                        {
                            Status status = new Status(statusFile, statusFile.getName(),
                                    statusFile.getAbsolutePath());

                            status.setThumbnail(getThumbnail(status));

                            if (!status.isVideo())
                            {
                                arrayList.add(status);
                            }
                        }
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                binding.imageProgressBar.setVisibility(View.GONE);
                                imageAdapter = new ImageAdapter(arrayList, getContext(),ImageFragment.this);
                                new ImageStatusViewFragment(arrayList);
                                binding.imageRecyclerView.setAdapter(imageAdapter);
                                imageAdapter.notifyDataSetChanged();
                            }
                        });

                    }
                    else {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                binding.imageProgressBar.setVisibility(View.GONE);
                                binding.textNoItem.setVisibility(View.VISIBLE);
                            }
                        });

                    }
                }
            }).start();
        }else {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    binding.imageProgressBar.setVisibility(View.GONE);
                    binding.textNoItem.setVisibility(View.VISIBLE);
                }
            });

        }
    }

    public Bitmap getThumbnail(Status status)
    {
        if (status.isVideo())
        {
            return ThumbnailUtils.createVideoThumbnail(status.getFile().getAbsolutePath(),
                    MediaStore.Video.Thumbnails.MICRO_KIND);
        }
        else {
            return ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(status.getFile().getAbsolutePath())
            ,Consts.THUMBSIZE
                    ,Consts.THUMBSIZE);
        }
    }

}

