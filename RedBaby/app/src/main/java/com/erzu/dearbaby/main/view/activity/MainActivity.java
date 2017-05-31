package com.erzu.dearbaby.main.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.erzu.dearbaby.R;
import com.erzu.dearbaby.base.BaseFragmentActivity;
import com.erzu.dearbaby.category.view.fragment.CategoryFragment;
import com.erzu.dearbaby.home.view.fragment.HomeFragment;
import com.erzu.dearbaby.lepingou.view.fragment.LepingouFragment;
import com.erzu.dearbaby.me.view.fragment.MineFragment;
import com.erzu.dearbaby.shoppingcart.view.fragment.CartFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 主页面
 */
public class MainActivity extends BaseFragmentActivity {


    @BindView(R.id.tabHome)
    RadioButton tabHome;
    @BindView(R.id.tabCategory)
    RadioButton tabCategory;
    @BindView(R.id.tabLepin)
    RadioButton tabLepin;
    @BindView(R.id.tabShopping)
    RadioButton tabShopping;
    @BindView(R.id.tabMyBuy)
    RadioButton tabMyBuy;
    @BindView(R.id.tabBottom)
    RadioGroup tabBottom;
    @BindView(R.id.realContent)
    FrameLayout realContent;
    @BindView(R.id.Iv_home_bg)
    ImageView IvHomeBg;
    @BindView(R.id.main_layout)
    RelativeLayout mainLayout;
    private List<Fragment> contentlist;
    private Handler handler = new Handler();
    private boolean canExist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        addShoppingNum();
        initData();
        initView(R.id.realContent, tabBottom, contentlist);
    }

    private void addShoppingNum() {
        TextView shoppingNum = new TextView(this);
        RelativeLayout.LayoutParams shoppingParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        shoppingParams.addRule(RelativeLayout.ALIGN_RIGHT, R.id.tab);
        shoppingParams.addRule(RelativeLayout.ALIGN_TOP, R.id.tab);

        shoppingParams.rightMargin = (int) (((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay().getWidth() * 1.5 / 5 - 20);
        shoppingParams.topMargin = 5;
        shoppingNum.setTextSize(10);
        shoppingNum.setLayoutParams(shoppingParams);
        shoppingNum.setBackgroundResource(R.drawable.shoppingcart_tab_num_bg);
        shoppingNum.setText("1");
        shoppingNum.setTextColor(Color.WHITE);
        shoppingNum.setGravity(Gravity.CENTER);
        mainLayout.addView(shoppingNum);

    }

    private void initData() {
        contentlist = new ArrayList<>();
        contentlist.add(new HomeFragment());
        contentlist.add(new CategoryFragment());
        contentlist.add(new LepingouFragment());
        contentlist.add(new CartFragment());
        contentlist.add(new MineFragment());
    }

    public static void startMainActiviy(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }

    /**
     * 退出程序
     */
    @Override
    public void onBackPressed() {
        if (tabHome.isChecked()) {
            if (canExist) {
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
                Toast.makeText(this, R.string.click_twice_to_exit_app, Toast.LENGTH_SHORT).show();
            }
        } else {
            tabHome.setChecked(true);
        }
    }
}
