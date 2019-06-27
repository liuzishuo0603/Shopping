package com.example.practicehomework;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebActivity extends AppCompatActivity {

    private WebView mWb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        initView();
    }

    private void initView() {
        mWb = (WebView) findViewById(R.id.wb_webview);
        if (getIntent() != null) {
            mWb.setWebViewClient(new WebViewClient());
            WebSettings settings = mWb.getSettings();
            settings.setJavaScriptCanOpenWindowsAutomatically(true);
            settings.setSupportZoom(true);
            settings.setDisplayZoomControls(true);
            settings.setBuiltInZoomControls(true);
            settings.setGeolocationEnabled(true);
            settings.setBuiltInZoomControls(true);
            settings.setDisplayZoomControls(true);
            String url = getIntent().getStringExtra("url");
            mWb.loadUrl(url);
        }
    }
}
