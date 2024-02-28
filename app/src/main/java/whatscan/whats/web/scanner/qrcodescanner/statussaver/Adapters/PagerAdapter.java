package whatscan.whats.web.scanner.qrcodescanner.statussaver.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import whatscan.whats.web.scanner.qrcodescanner.statussaver.Fragments.ImageFragment;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.Fragments.VideoFragment;

public class PagerAdapter extends FragmentPagerAdapter {
    private ImageFragment imageFragment;
    private VideoFragment videoFragment;
    public PagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
        imageFragment = new ImageFragment();
        videoFragment = new VideoFragment();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position==0)
        {
            return new ImageFragment();
        }
        else{
            return new VideoFragment();
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position==0)
        {
            return "Pics";
        }
        else if (position==1){
            return "Videos";
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
