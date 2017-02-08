package com.mint.coffeeshops.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mint.coffeeshops.R;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        String url;
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            url = null;
        } else {
            url = extras.getString("coffee_shop_url");
        }

        WebView mWebView = (WebView) findViewById(R.id.webview);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        mWebView.setBackgroundColor(Color.TRANSPARENT);
        mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_INSET);

        mWebView.loadUrl(url);
        mWebView.setWebViewClient(new MyWebViewClient());


    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        //show the web page in webview but not in web browser
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
