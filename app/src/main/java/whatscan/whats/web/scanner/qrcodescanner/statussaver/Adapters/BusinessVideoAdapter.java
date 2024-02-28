package whatscan.whats.web.scanner.qrcodescanner.statussaver.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import whatscan.whats.web.scanner.qrcodescanner.statussaver.Activities.BusinessStatusActivity;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.Fragments.BusinessVideoFragment;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.Models.Status;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.R;

public class BusinessVideoAdapter extends RecyclerView.Adapter<BusinessVideoAdapter.VideoViewHolder> {


    private final List<Status> statusList;
    Context context;
    BusinessVideoFragment businessVideoFragment;

    public BusinessVideoAdapter(List<Status> statusList, Context context, BusinessVideoFragment businessVideoFragment) {
        this.statusList = statusList;
        this.context = context;
        this.businessVideoFragment = businessVideoFragment;
    }
    @NonNull
    @Override
    public BusinessVideoAdapter.VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_status,parent,false);
        return new BusinessVideoAdapter.VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BusinessVideoAdapter.VideoViewHolder holder, int position) {
        Status status = statusList.get(position);
        holder.binding.imageThumbnail.setImageBitmap(status.getThumbnail());

        holder.binding.imageThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int position = holder.getAbsoluteAdapterPosition();

                if (context instanceof BusinessStatusActivity) {
                    if (status.getThumbnail() != null) {

                        if (status.isVideo()) {


                            ((BusinessStatusActivity) context).navigateToForthFragment(status);

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
