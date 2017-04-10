package shanyao.tabpagerindicatordemo.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import shanyao.tabpagerindicatordemo.FragmentFactory;
import shanyao.tabpagerindicatordemo.R;
import shanyao.tabpagerindicatordemo.ShanYaoApplication;
import shanyao.tabpagerindicatordemo.utils.CommonUtils;
import shanyao.tabpagerindictor.TabPageIndicator;

public class MainActivity extends FragmentActivity {

    private TabPageIndicator indicator;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        indicator = (TabPageIndicator) findViewById(R.id.indicator);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        BasePagerAdapter adapter = new BasePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        indicator.setViewPager(viewPager);
        setTabPagerIndicator();
    }

    private void setTabPagerIndicator() {
        indicator.setIndicatorMode(TabPageIndicator.IndicatorMode.MODE_NOWEIGHT_EXPAND_SAME);// 设置模式，一定要先设置模式
        indicator.setDividerColor(Color.WHITE);// 设置分割线的颜色
        indicator.setDividerPadding(CommonUtils.dip2px(ShanYaoApplication.getContext(), 10));
        indicator.setIndicatorColor(Color.RED);// 设置底部导航线的颜色
        indicator.setTextColorSelected(Color.RED);// 设置tab标题选中的颜色
        indicator.setTextColor(Color.BLACK);// 设置tab标题未被选中的颜色
        indicator.setTextSize(CommonUtils.sp2px(ShanYaoApplication.getContext(), 16));// 设置字体大小
    }

    class BasePagerAdapter extends FragmentStatePagerAdapter {
        String[] titles;

        public BasePagerAdapter(FragmentManager fm) {
            super(fm);
            this.titles = CommonUtils.getStringArray(R.array.expand_titles);
        }

        @Override
        public Fragment getItem(int position) {
            return FragmentFactory.createForExpand(position);
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }

}


