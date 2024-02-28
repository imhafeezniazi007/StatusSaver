package whatscan.whats.web.scanner.qrcodescanner.statussaver.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import whatscan.whats.web.scanner.qrcodescanner.statussaver.Activities.SavedStatusActivity;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.Fragments.SavedVideoFragment;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.Models.MediaFile;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.R;

public class SavedVideoAdapter extends RecyclerView.Adapter<SavedVideoAdapter.VideoViewHolder> {


    private final List<MediaFile> statusList;
    Context context;
    SavedVideoFragment videoFragment;

    public SavedVideoAdapter(List<MediaFile> statusList, Context context, SavedVideoFragment videoFragment) {
        this.statusList = statusList;
        this.context = context;
        this.videoFragment = videoFragment;
    }
    @NonNull
    @Override
    public SavedVideoAdapter.VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_status,parent,false);
        return new SavedVideoAdapter.VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        MediaFile status = statusList.get(position);
        holder.binding.imageThumbnail.setImageBitmap(status.getThumbnail());

        holder.binding.imageThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int position = holder.getAbsoluteAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    MediaFile status = statusList.get(position);
                }
                if (context instanceof SavedStatusActivity) {
                    if (status.getThumbnail() != null) {

                        if (status.isVideo()) {

                            ((SavedStatusActivity) context).navigateToForthFragment(status);

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
        whatscan.whats.web.scanner.qrcodescanner.statussaver.databinding.ItemStatusBinding binding;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = whatscan.whats.web.scanner.qrcodescanner.statussaver.databinding.ItemStatusBinding.bind(itemView);
        }

    }
}
