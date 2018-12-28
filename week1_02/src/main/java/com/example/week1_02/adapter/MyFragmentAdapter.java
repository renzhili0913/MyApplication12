package com.example.week1_02.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.week1_02.fragment.CommentFragment;
import com.example.week1_02.fragment.DetailsFragment;
import com.example.week1_02.fragment.ShopFragment;

public class MyFragmentAdapter extends FragmentPagerAdapter {
    private String[] mueus = new String[]{"商品","详情","评论"};
    public MyFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                return new ShopFragment();
            case 1:
                return new DetailsFragment();
            case 2:
                return new CommentFragment();

        }
        return null;
    }

    @Override
    public int getCount() {
        return mueus.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mueus[position];
    }
}
