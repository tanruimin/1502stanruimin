package com.erzu.dearbaby.lepingou.view.fragment;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.erzu.dearbaby.R;
import com.erzu.dearbaby.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LepingouFragment extends BaseFragment {
    @BindView(R.id.epp_recharge_webview)
    WebView eppRechargeWebview;
    @BindView(R.id.epp_pay_title_back_img)
    Button eppPayTitleBackImg;
    @BindView(R.id.epp_pay_title_text)
    TextView eppPayTitleText;

    @Override
    protected View initSelfView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_lepin, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {
        eppRechargeWebview.loadUrl("http://c.m.suning.com/group_index.html");
        eppRechargeWebview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
       eppRechargeWebview.getSettings().setJavaScriptEnabled(true);
        eppRechargeWebview.getSettings().setBlockNetworkImage(true);
        eppRechargeWebview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                eppRechargeWebview.getSettings().setBlockNetworkImage(false);

            }
        });
    }

    @Override
    public void initView(View view) {

    }



    @OnClick(R.id.epp_pay_title_back_img)
    public void onClick() {
    }
}
