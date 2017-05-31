package com.erzu.dearbaby.me.view.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.erzu.dearbaby.R;
import com.erzu.dearbaby.me.model.url.Url;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;



public class FavorActivity extends Activity {


    @BindView(R.id.mybuy_webview)
    WebView mybuyWebview;
    @BindView(R.id.epp_pay_title_back_img)
    Button eppPayTitleBackImg;
    @BindView(R.id.epp_pay_title_text)
    TextView eppPayTitleText;
    @BindView(R.id.more_dialog)
    ImageView moreDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_epay_wap);
        ButterKnife.bind(this);

initWeb();
    }

    private void initWeb() {
        mybuyWebview.loadUrl(Url.WISHLIST);

        mybuyWebview.getSettings().setJavaScriptEnabled(true);
        mybuyWebview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mybuyWebview.getSettings().setBlockNetworkImage(true);
        mybuyWebview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mybuyWebview.getSettings().setBlockNetworkImage(false);
            }
        });

    }

    @OnClick({R.id.epp_pay_title_back_img, R.id.more_dialog})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.epp_pay_title_back_img:
                finish();
                break;
            case R.id.more_dialog:
                break;
        }
    }
}
