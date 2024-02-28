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

import whatscan.whats.web.scanner.qrcodescanner.statussaver.Adapters.BusinessImageAdapter;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.Models.Status;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.R;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.Utils.Consts;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.databinding.FragmentBusinessImageBinding;


public class BusinessImageFragment extends Fragment {

    ArrayList<Status> arrayList;
    BusinessImageAdapter businessImageAdapter;
    Handler handler = new Handler();
    FragmentBusinessImageBinding fragmentBusinessImageBinding;

    public BusinessImageFragment() {

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
        View view = inflater.inflate(R.layout.fragment_business_image, container, false);
        fragmentBusinessImageBinding = FragmentBusinessImageBinding.bind(view);

        arrayList = new ArrayList<>();
        fragmentBusinessImageBinding.imageRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        loadPictureStatuses();

        return view;
    }



    private void loadPictureStatuses() {
        if (Consts.STATUS_DIRECTORY_BUSINESS.exists()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    File[] statusFiles = Consts.STATUS_DIRECTORY_BUSINESS.listFiles();

                    if (statusFiles != null && statusFiles.length > 0) {
                        Arrays.sort(statusFiles);

                        for (final File statusFile : statusFiles) {
                            Status status = new Status(statusFile, statusFile.getName(),
                                    statusFile.getAbsolutePath());

                            status.setThumbnail(getThumbnail(status));

                            if (!status.isVideo()) {
                                arrayList.add(status);
                            }
                        }
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                fragmentBusinessImageBinding.imageProgressBar.setVisibility(View.GONE);
                                businessImageAdapter = new BusinessImageAdapter(arrayList, getContext(), BusinessImageFragment.this);
                                new BusinessImageViewFragment(arrayList);
                                fragmentBusinessImageBinding.imageRecyclerView.setAdapter(businessImageAdapter);
                                businessImageAdapter.notifyDataSetChanged();
                            }
                        });

                    } else {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                fragmentBusinessImageBinding.imageProgressBar.setVisibility(View.GONE);
                                fragmentBusinessImageBinding.textNoItem.setVisibility(View.VISIBLE);
                            }
                        });

                    }
                }
            }).start();
        } else {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    fragmentBusinessImageBinding.imageProgressBar.setVisibility(View.GONE);
                    fragmentBusinessImageBinding.textNoItem.setVisibility(View.VISIBLE);
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

