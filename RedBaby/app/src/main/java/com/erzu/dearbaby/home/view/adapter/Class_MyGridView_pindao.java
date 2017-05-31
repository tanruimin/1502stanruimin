package com.erzu.dearbaby.home.view.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;



public class Class_MyGridView_pindao extends GridView {
    public Class_MyGridView_pindao(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
