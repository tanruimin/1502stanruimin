package shanyao.tabpagerindicatordemo.activity;



import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.bawei.mualllib.vp.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

import adapter.MyFragmentPagerAdapter;
import shanyao.tabpagerindicatordemo.R;


public class MainActivity extends FragmentActivity {

    private TabPageIndicator mindicator;
    private ViewPager vp;
    private List<String> title=new ArrayList<String>();
    private List<String> myurl=new ArrayList<String>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();
        vp.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(),title,myurl));
        mindicator.setViewPager(vp);

    }

    private void initview() {
        mindicator= (TabPageIndicator) findViewById(R.id.indicator);
        vp= (ViewPager) findViewById(R.id.vp);
        title.add("国际");
        title.add("时尚");
        title.add("财经");
        title.add("科技");
        title.add("军事");
        title.add("体育");
        title.add("娱乐");
        title.add("国内");
        title.add("社会");
        title.add("头条");
        myurl.add(Mypath.gj);
        myurl.add(Mypath.ss);
        myurl.add(Mypath.cj);
        myurl.add(Mypath.kj);
        myurl.add(Mypath.js);
        myurl.add(Mypath.ty);
        myurl.add(Mypath.yl);
        myurl.add(Mypath.gn);
        myurl.add(Mypath.shehui);
        myurl.add(Mypath.tt);
    }
}

