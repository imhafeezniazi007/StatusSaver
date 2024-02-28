package whatscan.whats.web.scanner.qrcodescanner.statussaver.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import whatscan.whats.web.scanner.qrcodescanner.statussaver.Activities.MainActivity;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.Fragments.VideoFragment;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.Models.Status;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.R;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.databinding.ItemStatusBinding;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {


    private final List<Status> statusList;
    Context context;
    VideoFragment videoFragment;

    public VideoAdapter(List<Status> statusList, Context context, VideoFragment videoFragment) {
        this.statusList = statusList;
        this.context = context;
        this.videoFragment = videoFragment;
    }
    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_status,parent,false);
        return new VideoAdapter.VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        Status status = statusList.get(position);
        holder.binding.imageThumbnail.setImageBitmap(status.getThumbnail());

        holder.binding.imageThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // Status status = statusList.get(holder.getAbsoluteAdapterPosition());
                int position = holder.getAbsoluteAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Status status = statusList.get(position);
                }
                if (context instanceof MainActivity) {
                    if (status.getThumbnail() != null) {

                        if (status.isVideo()) {


                            ((MainActivity) context).navigateToForthFragment(status);

                        }else {
                            Log.e("FirstFragment", "Not a video");
                        }
                    } else {
                        Log.e("FirstFragment", "Thumbnail not found");
                    }
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return statusList.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder
    {
            ItemStatusBinding binding;

            public VideoViewHolder(@NonNull View itemView) {
                super(itemView);
                binding = ItemStatusBinding.bind(itemView);

                //code to download video on item click
//                binding.imageThumbnail.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Status status = statusList.get(getAdapterPosition());
//                        if (status!=null)
//                        {
//                            try {
//                                videoFragment.downloadVideo(status);
//                            } catch (IOException e) {
//                                throw new RuntimeException(e);
//                            }
//                        }
//                    }
//                });
            }

    }
}
