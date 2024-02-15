package com.example.statussaver.Fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.statussaver.Adapters.ImageAdapter;
import com.example.statussaver.Adapters.SavedImageAdapter;
import com.example.statussaver.Models.MediaFile;
import com.example.statussaver.Models.Status;
import com.example.statussaver.R;
import com.example.statussaver.Utils.Consts;
import com.example.statussaver.databinding.FragmentImageBinding;
import com.example.statussaver.databinding.FragmentSavedImageBinding;
import com.google.android.gms.ads.interstitial.InterstitialAd;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;


public class SavedImageFragment extends Fragment {

    ArrayList<MediaFile> arrayList;
    SavedImageAdapter savedImageAdapter;
    Handler handler = new Handler();
    FragmentSavedImageBinding binding;

    public SavedImageFragment() {

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
        View view = inflater.inflate(R.layout.fragment_saved_image, container, false);
        binding = FragmentSavedImageBinding.bind(view);

        arrayList = new ArrayList<>();
        binding.imageRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        loadPictureStatuses();

        return view;
    }



    private void loadPictureStatuses() {
        if (Consts.APP_DIR_SAVED.exists())
        {
            new Thread(new Runnable() {
                @Override
                public void run() {

                    File[] mediaFiles = Consts.APP_DIR_SAVED.listFiles();

                    if (mediaFiles != null && mediaFiles.length > 0) {
                        Arrays.sort(mediaFiles);

                        for (final File mediaFile : mediaFiles) {
                            MediaFile media = new MediaFile(mediaFile, mediaFile.getName(),
                                    mediaFile.getAbsolutePath());

                            media.setThumbnail(getThumbnail(media));

                            if (!media.isVideo())
                            {
                                arrayList.add(media);
                            }
                        }
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                binding.imageProgressBar.setVisibility(View.GONE);
                                savedImageAdapter = new SavedImageAdapter(arrayList, getContext(),SavedImageFragment.this);
                                binding.imageRecyclerView.setAdapter(savedImageAdapter);
                                savedImageAdapter.notifyDataSetChanged();
                            }
                        });

                    }
                    else {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                binding.imageProgressBar.setVisibility(View.GONE);
                                Toast.makeText(getContext(), "Does not exist", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }
            }).start();
        }
    }

    public Bitmap getThumbnail(MediaFile mediaFile)
    {
        if (mediaFile.isVideo())
        {
            return ThumbnailUtils.createVideoThumbnail(mediaFile.getFile().getAbsolutePath(),
                    MediaStore.Video.Thumbnails.MICRO_KIND);
        }
        else {
            return ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(mediaFile.getFile().getAbsolutePath())
                    ,Consts.THUMBSIZE
                    ,Consts.THUMBSIZE);
        }
    }

}
