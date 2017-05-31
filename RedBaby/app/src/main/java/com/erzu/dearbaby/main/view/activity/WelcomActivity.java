package com.erzu.dearbaby.main.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import com.erzu.dearbaby.R;
import com.erzu.dearbaby.utils.SharedPreferenceUtils2;

public class WelcomActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcom);


        delayStartActivty();
    }

    private void delayStartActivty() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
              startWitchActivity((Boolean) SharedPreferenceUtils2.get(getApplicationContext(),"hadSetState",false));
            }


        },2000);
    }

    private void startWitchActivity(boolean hadSetState) {
        if (hadSetState){
            startActivity(new Intent(this,MainActivity.class));
        }else {
            startActivity(new Intent(this,GuideActivity.class));
        }
        finish();
    }

    /**
     * 控制点击返回键无法销毁Activity
     */
    @Override
    public void onBackPressed() {

    }
}
