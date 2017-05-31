package com.erzu.dearbaby.home.view.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;




public class HomeVpAdapter extends FragmentPagerAdapter {

    private List<String> mTitle;
    private Context context;
    private List<Fragment> fragmentList;

    public HomeVpAdapter(FragmentManager fm, List<String> mTitle, Context context, List<Fragment> fragmentList) {
        super(fm);
        this.mTitle = mTitle;
        this.context = context;
        this.fragmentList = fragmentList;
    }


    @Override
    public CharSequence getPageTitle(int position) {

        return mTitle.get(position);
    }

    @Override
    public int getCount() {
        return mTitle != null ? mTitle.size() : 0;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = fragmentList.get(position);

        return fragment;
    }

}
