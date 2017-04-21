package tanruimin.bwei.com.zhouwuzy;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;

/**
 * date:2017/4/21.
 * author:tanruimin.
 * function:
 */

public class MyButton extends Button {
    private static final String TAG = "MyButton";
    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.d(TAG,"dispatchTouchEvent");
        return super.dispatchTouchEvent(event);
    }
    //返回false向下传递返回true自己消费
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG,"onTouchEvent");
        return super.onTouchEvent(event);
    }
}
