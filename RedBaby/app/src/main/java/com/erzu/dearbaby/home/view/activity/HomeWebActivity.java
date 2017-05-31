package com.erzu.dearbaby.home.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.erzu.dearbaby.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeWebActivity extends AppCompatActivity {

    @BindView(R.id.home_busy_web_view)
    WebView homeBusyWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_web_view);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String webUrl = intent.getStringExtra("web");
        homeBusyWebView.loadUrl(webUrl);

    }

}
