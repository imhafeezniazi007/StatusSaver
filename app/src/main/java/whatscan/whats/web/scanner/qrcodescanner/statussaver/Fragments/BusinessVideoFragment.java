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

import whatscan.whats.web.scanner.qrcodescanner.statussaver.Adapters.BusinessVideoAdapter;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.Models.Status;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.R;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.Utils.Consts;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.databinding.FragmentBusinessVideoBinding;

public class BusinessVideoFragment extends Fragment {

    FragmentBusinessVideoBinding binding;
    Handler handler = new Handler();
    ArrayList<Status> videoArrayList;
    BusinessVideoAdapter videoAdapter;

    public BusinessVideoFragment() {
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
        View view = inflater.inflate(R.layout.fragment_business_video, container, false);
        binding = FragmentBusinessVideoBinding.bind(view);

        videoArrayList = new ArrayList<>();
        binding.videoRecyclerView.setHasFixedSize(true);
        binding.videoRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        loadVideoStatuses();

        return view;
    }


    private void loadVideoStatuses() {
        if (Consts.STATUS_DIRECTORY_BUSINESS.exists())
        {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    File[] statusFiles = Consts.STATUS_DIRECTORY_BUSINESS.listFiles();

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
                                videoAdapter = new BusinessVideoAdapter(videoArrayList, getContext(),BusinessVideoFragment.this);
                                new BusinessVideoViewFragment(videoArrayList);
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

}