package com.erzu.dearbaby.main.view.activity;

import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.erzu.dearbaby.R;
import com.erzu.dearbaby.main.view.fragment.GuideSelectFragment;


public class GuideActivity extends AppCompatActivity {
 private   boolean canExist= false;
    private Handler handler=new Handler();
    private FragmentManager fm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide_activity);
        fm=getSupportFragmentManager();
        addGuideFrag();
    }

    private void addGuideFrag() {
        fm.beginTransaction().add(R.id.guideGather,new GuideSelectFragment()).commit();
    }

    /**
     * 退出程序
     */
    @Override
    public void onBackPressed() {
if (fm.popBackStackImmediate()){}
          else   if (canExist) {
                finish();
            } else {

                if (!canExist) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            canExist = false;
                        }
                    }, 2000);

                }
                canExist = true;
                Toast.makeText(this,R.string.click_twice_to_exit_app, Toast.LENGTH_SHORT).show();
            }
        }
    }

