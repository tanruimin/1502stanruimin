package tanruimin.bwei.com.zhouwuzy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private MyButton mButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVew();
    }

    private void initVew() {
        mButton = (MyButton) findViewById(R.id.textview);
        mButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Toast.makeText(MainActivity.this,"点击",Toast.LENGTH_SHORT).show();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Toast.makeText(MainActivity.this,"手指移动事件",Toast.LENGTH_SHORT).show();

                        int x = (int) event.getRawX();
                        int y = (int) event.getRawY();
                        Log.e(TAG, "x = "+x+"   y = "+y);
                        break;
                    case MotionEvent.ACTION_UP:
                        Toast.makeText(MainActivity.this,"手指抬起事件",Toast.LENGTH_SHORT).show();

                        break;
                    case MotionEvent.ACTION_CANCEL:
                        Toast.makeText(MainActivity.this,"取消事件",Toast.LENGTH_SHORT).show();

                        break;
                    case MotionEvent.ACTION_SCROLL:
                        Toast.makeText(MainActivity.this,"滚动事件",Toast.LENGTH_SHORT).show();
                       
                        break;
                }
                return false;
            }
        });
    }
    //负责分发事件的方法
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG,"dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG,"onTouchEvent");
        return super.onTouchEvent(event);
    }
}

