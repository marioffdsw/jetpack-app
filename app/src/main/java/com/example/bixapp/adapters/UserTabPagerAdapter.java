package com.example.bixapp.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.bixapp.R;
import com.example.bixapp.views.FrgAlbum;
import com.example.bixapp.views.FrgGallery;
import com.example.bixapp.views.FrgPost;

public class UserTabPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.posts, R.string.albumes};
    private final Context context;
    private int userId;

    public UserTabPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                FrgPost frgPost = FrgPost.newInstance();
                frgPost.setUserId(userId);
                return frgPost;
            case 1:
                FrgGallery frgGallery = FrgGallery.newInstance();
                frgGallery.setUserId(userId);
                return frgGallery;
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return context.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 2;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
