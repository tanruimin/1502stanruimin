package tanruimin.bwei.com.zhouwuzy;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * date:2017/4/21.
 * author:tanruimin
 * function:
 */

public class MyRelativeLayout extends RelativeLayout {
    private static final String TAG = "MyRelativeLayout";
    public MyRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    //负责分发事件的方法
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG,"dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }
    //阻断触摸事件的方法返回false没有阻断向下传递，返回true阻断消息
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG,"onInterceptTouchEvent");
        return super.onInterceptTouchEvent(ev);
    }
    //返回false向下传递返回true自己消费
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG,"onTouchEvent");
        return super.onTouchEvent(event);
    }
}
