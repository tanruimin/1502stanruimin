package com.erzu.dearbaby.base;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.erzu.dearbaby.registlogin.view.activity.LoginActivity;
import com.erzu.dearbaby.utils.SharedPreferenceUtils2;

import java.util.List;



public class BaseFragmentActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener {
    private List<Fragment> fragments;
    private FragmentManager manager;
    private int oldId;
    private int contentId;

    protected void initView(int contentId, RadioGroup tabs, List<Fragment> fragments) {
        this.contentId = contentId;
        manager = getSupportFragmentManager();
        this.fragments = fragments;
        addOrShow(0);
        ((RadioButton) tabs.getChildAt(0)).setChecked(true);
        tabs.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        hide(oldId);
        for (int i = 0; i < fragments.size(); i++) {
            if (group.getChildAt(i).getId() == checkedId) {
             if (i!=4||(boolean)SharedPreferenceUtils2.get(this,"hadLogin",true)){
                oldId = i;
                addOrShow(i);}
               else {
                     addOrShow(oldId);
                 ( (RadioButton)group.getChildAt(oldId)).setChecked(true);
                     Intent intent=new Intent(this, LoginActivity.class);
                     startActivity(intent);
                 }

             }
            }
        }


    private void addOrShow(int i) {
        Fragment fragmentByTag = manager.findFragmentByTag(contentId + "frag" + i);
        FragmentTransaction transaction = manager.beginTransaction();
        if (fragmentByTag != null) {
            transaction.show(fragmentByTag);

        } else {
            transaction.add(contentId, fragments.get(i), contentId + "frag" + i);
        }
        transaction.commit();
    }

    private void hide(int id) {
        Fragment fragmentByTag = manager.findFragmentByTag(contentId + "frag" + id);
        FragmentTransaction transaction = manager.beginTransaction();
        if (fragmentByTag != null) {
            transaction.hide(fragmentByTag);
        }
        transaction.commit();
    }


}
