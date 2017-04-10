package shanyao.tabpagerindicatordemo;

import android.support.v4.app.Fragment;

import shanyao.tabpagerindicatordemo.fragment.DFragment;


/**
 * Created by shan_yao on 2016/6/17.
 */
public class FragmentFactory {
    public static Fragment createForNoExpand(int position) {
        Fragment fragment = new DFragment();

    return fragment;
    }

    public static Fragment createForExpand(int position) {
        Fragment fragment = new DFragment();


        return fragment;
    }
}
