package com.example.appfood.Adapterr;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.appfood.Fragment.Fragment_Confirm;
import com.example.appfood.Fragment.Fragment_Process;
import com.example.appfood.Fragment.Fragment_Wait;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return new Fragment_Wait();
            case 1:
                return new Fragment_Process();
            case 2:
                return new Fragment_Confirm();
            default:
                return new Fragment_Wait();
        }

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title="";
        switch (position)
        {
            case 0:
                 title="Chờ xác nhận";
                break;
            case 1:
                  title="Đang chế biến";
                break;
            case 2:
                 title="Đang giao";
                break;
        }
        return title;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
