package com.erzu.dearbaby.category.view.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.github.clans.fab.FloatingActionMenu;
import com.erzu.dearbaby.R;

import java.util.ArrayList;
import java.util.List;


public class CoordinatorLayoutActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private FloatingActionMenu mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout);
        mMenu= (FloatingActionMenu) findViewById(R.id.menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        initViewPager();
    }

    private void initViewPager() {

        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        List<String> titles = new ArrayList<>();
        titles.add("阅读");
        titles.add("购物");
        titles.add("理财");
        titles.add("生活");
        titles.add("直播");
        titles.add("真人秀");
        titles.add("体育");
        titles.add("婚恋");
        titles.add("社区");
        titles.add("头像");
        titles.add("通讯");
        titles.add("搞笑");
        titles.add("段子");
        for (int i = 0; i < titles.size(); i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(i)));
        }
        List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < titles.size(); i++) {
            fragments.add(new ListFragment());
        }
        FragmentAdapter mFragmentAdapteradapter =
                new FragmentAdapter(getSupportFragmentManager(), fragments, titles);
        //给ViewPager设置适配器
        mViewPager.setAdapter(mFragmentAdapteradapter);
        //将TabLayout和ViewPager关联起来。
        mTabLayout.setupWithViewPager(mViewPager);
        //给TabLayout设置适配器
        mTabLayout.setTabsFromPagerAdapter(mFragmentAdapteradapter);

    }

//    public void checkin(View view) {
//        Snackbar.make(view, "点击成功", Snackbar.LENGTH_SHORT).show();
//    }


}


