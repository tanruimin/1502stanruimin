package com.erzu.dearbaby.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;


public abstract class BaseFragment extends Fragment implements View.OnTouchListener{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initSelfView(inflater, container);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return true;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initData();
        view.setOnTouchListener(this);
    }
    protected abstract View initSelfView(LayoutInflater inflater, ViewGroup container);
    public  abstract void initData();
    public   abstract void initView(View view);

}
