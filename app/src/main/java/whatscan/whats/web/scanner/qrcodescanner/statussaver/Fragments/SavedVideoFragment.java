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

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import whatscan.whats.web.scanner.qrcodescanner.statussaver.Adapters.SavedVideoAdapter;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.Models.MediaFile;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.R;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.Utils.Consts;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.databinding.FragmentSavedVideoBinding;

public class SavedVideoFragment extends Fragment {

    FragmentSavedVideoBinding binding;
    Handler handler = new Handler();
    ArrayList<MediaFile> videoArrayList;
    SavedVideoAdapter videoAdapter;

    public SavedVideoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding = FragmentSavedVideoBinding.bind(view);

        videoArrayList = new ArrayList<>();
        binding.videoRecyclerView.setHasFixedSize(true);
        binding.videoRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        loadVideoStatuses();
    }


    private void loadVideoStatuses() {
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

                            if (media.isVideo())
                            {
                                videoArrayList.add(media);
                            }
                        }
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                binding.videoProgressBar.setVisibility(View.GONE);
                                videoAdapter = new SavedVideoAdapter(videoArrayList, getContext(),SavedVideoFragment.this);
                                binding.videoRecyclerView.setAdapter(videoAdapter);
                                videoAdapter.notifyDataSetChanged();
                            }
                        });

                    }
                    else {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                binding.videoProgressBar.setVisibility(View.GONE);
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
                    binding.videoProgressBar.setVisibility(View.GONE);
                    binding.textNoItem.setVisibility(View.VISIBLE);
                }
            });

        }
    }

    private Bitmap getThumbnail(MediaFile status)
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_saved_video, container, false);

    }

}