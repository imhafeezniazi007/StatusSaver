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

import whatscan.whats.web.scanner.qrcodescanner.statussaver.Adapters.VideoAdapter;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.Models.Status;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.R;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.Utils.Consts;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.databinding.FragmentVideoBinding;

public class VideoFragment extends Fragment {

    FragmentVideoBinding binding;
    Handler handler = new Handler();
    ArrayList<Status> videoArrayList;
    VideoAdapter videoAdapter;

    public VideoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding = FragmentVideoBinding.bind(view);

        videoArrayList = new ArrayList<>();
        binding.videoRecyclerView.setHasFixedSize(true);
        binding.videoRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        loadVideoStatuses();
    }


    private void loadVideoStatuses() {
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

                            if (status.isVideo())
                            {
                                videoArrayList.add(status);
                            }
                        }
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                binding.videoProgressBar.setVisibility(View.GONE);
                                videoAdapter = new VideoAdapter(videoArrayList, getContext(),VideoFragment.this);
                                new VideoStatusViewFragment(videoArrayList);
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

    private Bitmap getThumbnail(Status status)
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
        return inflater.inflate(R.layout.fragment_video, container, false);

    }

}