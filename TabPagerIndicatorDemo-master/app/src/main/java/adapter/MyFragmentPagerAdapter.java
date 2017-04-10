package adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;



import java.util.List;

import shanyao.tabpagerindicatordemo.fragment.DFragment;



public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<String>mList;
    private List<String>mUrl;

    public MyFragmentPagerAdapter(FragmentManager fm, List<String> list, List<String> url) {
        super(fm);
        mList = list;
        mUrl = url;
    }

    @Override
    public Fragment getItem(int position) {
        return DFragment.getFragment(mUrl.get(position));
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mList.get(position%mList.size()).toUpperCase();
    }
}
