package com.erzu.dearbaby.home.view.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.erzu.dearbaby.R;
import com.erzu.dearbaby.home.view.adapter.Class_MyGridView_pindao;
import com.erzu.dearbaby.home.view.adapter.Class_OtherAdapter;

import java.util.ArrayList;
import java.util.List;



public class Class_Edits extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private Class_MyGridView_pindao mUserGv, mOtherGv;
    private List<String> mUserList = new ArrayList<>();
    private List<String> mOtherList = new ArrayList<>();
    private Class_OtherAdapter mUserAdapter, mOtherAdapter;
    private TextView class_edit_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_edit);
        initView();
        class_edit_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void initView() {
        mUserGv = (Class_MyGridView_pindao) findViewById(R.id.userGridView);
        mOtherGv = (Class_MyGridView_pindao) findViewById(R.id.otherGridView);
        class_edit_cancel = (TextView) findViewById(R.id.class_edit_cancel);
        mOtherList.add("育儿图书");
        mOtherList.add("防辐射服");
        mOtherList.add("儿童车");
        mOtherList.add("奶瓶");
        mOtherList.add("自行车");
        mOtherList.add("纸尿裤");
        mOtherList.add("奶粉");
        mOtherList.add("驱蚊液");
        mOtherList.add("推车");
        mOtherList.add("男童夏装");
        mOtherList.add("女童夏装");
        mOtherList.add("儿童玩具");
//        mUserList.add("情感");
//        mUserList.add("儿童车");
        mUserAdapter = new Class_OtherAdapter(this, mUserList, true);
        mOtherAdapter = new Class_OtherAdapter(this, mOtherList, false);
        mUserGv.setAdapter(mUserAdapter);
        mOtherGv.setAdapter(mOtherAdapter);
        mUserGv.setOnItemClickListener(this);
        mOtherGv.setOnItemClickListener(this);
    }

    /**
     * 获取点击的Item的对应View，
     * 因为点击的Item已经有了自己归属的父容器MyGridView，所有我们要是有一个ImageView来代替Item移动
     *
     * @param view
     * @return
     */
    private ImageView getView(View view) {
        view.destroyDrawingCache();
        view.setDrawingCacheEnabled(true);
        Bitmap cache = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        ImageView iv = new ImageView(this);
        iv.setImageBitmap(cache);
        return iv;
    }

    /**
     * 获取移动的VIEW，放入对应ViewGroup布局容器
     *
     * @param viewGroup
     * @param view
     * @param initLocation
     * @return
     */
    private View getMoveView(ViewGroup viewGroup, View view, int[] initLocation) {
        int x = initLocation[0];
        int y = initLocation[1];
        viewGroup.addView(view);
        LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mLayoutParams.leftMargin = x;
        mLayoutParams.topMargin = y;
        view.setLayoutParams(mLayoutParams);
        return view;
    }

    /**
     * 创建移动的ITEM对应的ViewGroup布局容器
     * 用于存放我们移动的View
     */
    private ViewGroup getMoveViewGroup() {
        //window中最顶层的view
        ViewGroup moveViewGroup = (ViewGroup) getWindow().getDecorView();
        LinearLayout moveLinearLayout = new LinearLayout(this);
        moveLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        moveViewGroup.addView(moveLinearLayout);
        return moveLinearLayout;
    }

    /**
     * 点击ITEM移动动画
     *
     * @param moveView
     * @param startLocation
     * @param endLocation
     * @param moveChannel
     * @param clickGridView
     */
    private void MoveAnim(View moveView, int[] startLocation, int[] endLocation, final String moveChannel,
                          final GridView clickGridView, final boolean isUser) {
        int[] initLocation = new int[2];
        //获取传递过来的VIEW的坐标
        moveView.getLocationInWindow(initLocation);
        //得到要移动的VIEW,并放入对应的容器中
        final ViewGroup moveViewGroup = getMoveViewGroup();
        final View mMoveView = getMoveView(moveViewGroup, moveView, initLocation);
        //创建移动动画
        TranslateAnimation moveAnimation = new TranslateAnimation(
                startLocation[0], endLocation[0], startLocation[1],
                endLocation[1]);
        moveAnimation.setDuration(300L);//动画时间
        //动画配置
        AnimationSet moveAnimationSet = new AnimationSet(true);
        moveAnimationSet.setFillAfter(false);//动画效果执行完毕后，View对象不保留在终止的位置
        moveAnimationSet.addAnimation(moveAnimation);
        mMoveView.startAnimation(moveAnimationSet);
        moveAnimationSet.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                moveViewGroup.removeView(mMoveView);
                // 判断点击的是DragGrid还是OtherGridView
                if (isUser) {
                    mOtherAdapter.setVisible(true);
                    mOtherAdapter.notifyDataSetChanged();
                    mUserAdapter.remove();
                } else {
                    mUserAdapter.setVisible(true);
                    mUserAdapter.notifyDataSetChanged();
                    mOtherAdapter.remove();
                }
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        switch (parent.getId()) {
            case R.id.userGridView:
                //position为 0，1 的不可以进行任何操作
//                if (position != 0 && position != 1) {
            {
                final ImageView moveImageView = getView(view);
                if (moveImageView != null) {
                    TextView newTextView = (TextView) view.findViewById(R.id.text_item);
                    final int[] startLocation = new int[2];
                    newTextView.getLocationInWindow(startLocation);
                    final String channel = ((Class_OtherAdapter) parent.getAdapter()).getItem(position);//获取点击的频道内容
                    mOtherAdapter.setVisible(false);
                    //添加到最后一个
                    mOtherAdapter.addItem(channel);
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            try {
                                int[] endLocation = new int[2];
                                //获取终点的坐标
                                mOtherGv.getChildAt(mOtherGv.getLastVisiblePosition()).getLocationInWindow(endLocation);
                                MoveAnim(moveImageView, startLocation, endLocation, channel, mUserGv, true);
                                mUserAdapter.setRemove(position);
                            } catch (Exception localException) {
                            }
                        }
                    }, 50L);
//                    }
                }
            }
            break;
            case R.id.otherGridView:
                final ImageView moveImageView = getView(view);
                if (moveImageView != null) {
                    TextView newTextView = (TextView) view.findViewById(R.id.text_item);
                    final int[] startLocation = new int[2];
                    newTextView.getLocationInWindow(startLocation);
                    final String channel = ((Class_OtherAdapter) parent.getAdapter()).getItem(position);
                    mUserAdapter.setVisible(false);
                    //添加到最后一个
                    mUserAdapter.addItem(channel);
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            try {
                                int[] endLocation = new int[2];
                                //获取终点的坐标
                                mUserGv.getChildAt(mUserGv.getLastVisiblePosition()).getLocationInWindow(endLocation);
                                MoveAnim(moveImageView, startLocation, endLocation, channel, mOtherGv, false);
                                mOtherAdapter.setRemove(position);
                            } catch (Exception localException) {
                            }
                        }
                    }, 50L);
                }
                break;
            default:
                break;
        }
    }

}