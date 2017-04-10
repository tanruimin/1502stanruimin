package com.bawei.myalllib.http;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import com.bawei.myalllib.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 2017/3/29.
 */

public class VpMaxAdapter extends PagerAdapter {
    private Context context;
    private List<String>vpurllist;
    private List<View>vpviewlist=new ArrayList<View>();

    public VpMaxAdapter(Context context, List<String> vpurllist) {
        this.context = context;
        this.vpurllist = vpurllist;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView iv = new ImageView(context);
        iv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        iv.setScaleType(ImageView.ScaleType.FIT_XY);
        ImageLoader.getInstance().displayImage(vpurllist.get(position%vpurllist.size()), iv,new DisplayImageOptions.Builder().showImageOnLoading(R.mipmap.ic_launcher).build());
        ViewParent parent = iv.getParent();
        if (parent!=null){
            container.removeView(iv);
        }
        container.addView(iv);
        vpviewlist.add(iv);
        return iv;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
        container.removeView(vpviewlist.get(position%vpurllist.size()));

    }

}
