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
import whatscan.whats.web.scanner.qrcodescanner.statussaver.Fragments.SavedImageFragment;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.Models.MediaFile;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.R;

public class SavedImageAdapter extends RecyclerView.Adapter<SavedImageAdapter.ImageViewHolder> {

    private final List<MediaFile> statusList;
    private final Context context;
    private final SavedImageFragment imageFragment;

    public SavedImageAdapter(List<MediaFile> statusList, Context context, SavedImageFragment imageFragment) {
        this.statusList = statusList;
        this.context = context;
        this.imageFragment = imageFragment;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_status, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        MediaFile status = statusList.get(position);
        holder.binding.imageThumbnail.setImageBitmap(status.getThumbnail());

        holder.binding.imageThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getBindingAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    MediaFile status = statusList.get(position);
                    if (status != null && status.getThumbnail() != null) {
                        ((SavedStatusActivity) context).navigateToThirdFragment(status);
                    } else {
                        Log.e("ImageAdapter", "Invalid status or thumbnail is null");
                    }
                } else {
                    Log.e("ImageAdapter", "Invalid position");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return statusList.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        whatscan.whats.web.scanner.qrcodescanner.statussaver.databinding.ItemStatusBinding binding;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = whatscan.whats.web.scanner.qrcodescanner.statussaver.databinding.ItemStatusBinding.bind(itemView);
        }
    }
}
