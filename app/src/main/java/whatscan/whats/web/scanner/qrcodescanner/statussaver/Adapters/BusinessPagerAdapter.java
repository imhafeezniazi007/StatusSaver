package whatscan.whats.web.scanner.qrcodescanner.statussaver.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import whatscan.whats.web.scanner.qrcodescanner.statussaver.Fragments.BusinessImageFragment;
import whatscan.whats.web.scanner.qrcodescanner.statussaver.Fragments.BusinessVideoFragment;

public class BusinessPagerAdapter extends FragmentPagerAdapter {
    private BusinessImageFragment businessImageFragment;
    private BusinessVideoFragment businessVideoFragment;
    public BusinessPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
        businessImageFragment = new BusinessImageFragment();
        businessVideoFragment = new BusinessVideoFragment();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position==0)
        {
            return new BusinessImageFragment();
        }
        else{
            return new BusinessVideoFragment();
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
