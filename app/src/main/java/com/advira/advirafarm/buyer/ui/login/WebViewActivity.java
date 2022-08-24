package com.advira.advirafarm.buyer.ui.login;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.advira.advirafarm.buyer.R;

public class WebViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        WebView.setWebContentsDebuggingEnabled(false);

        WebView mywebview = findViewById(R.id.webView);
        TextView tv_signup_header2 = findViewById(R.id.tv_signup_header2);
        RelativeLayout rl_back = findViewById(R.id.rl_back);
        ImageView iv_rx = findViewById(R.id.iv_rx);


        Bundle extras = getIntent().getExtras();
        String header = "";
        String url = "";
        String isrx = "";


        if (extras != null) {
            header = extras.getString("header");
            url = extras.getString("url");



        }

        try {
            isrx = extras.getString("isrx");
            if (isrx.equalsIgnoreCase("yes")) {

                iv_rx.setVisibility(View.VISIBLE);
            } else {
                iv_rx.setVisibility(View.INVISIBLE);
            }

        } catch (Exception ex) {

        }




        tv_signup_header2.setText(header);
        //mywebview.loadUrl(url);
        //mywebview.setWebViewClient(new WebViewClient());

//

        mywebview.getSettings().setBuiltInZoomControls(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mywebview.getSettings().setSafeBrowsingEnabled(false);
        }

        WebSettings settings = mywebview.getSettings();
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setJavaScriptEnabled(true);

        settings.setAppCacheEnabled(false);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setDatabaseEnabled(false);
        settings.setDomStorageEnabled(false);
        settings.setGeolocationEnabled(false);
        settings.setSaveFormData(false);
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // chromium, enable hardware acceleration
            mywebview.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            // older android version, disable hardware acceleration
            mywebview.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
//        settings.setUserAgentString("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36");

        mywebview.setWebViewClient(new CustowebViewClient());
        mywebview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                Log.e("Progress: ", "Progress: " + newProgress);
                super.onProgressChanged(view, newProgress);
            }
        });
//        mywebview.loadDataWithBaseURL(url, null, "text/html", "UTF-8", null);
        mywebview.loadUrl(url);




        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                WebViewActivity.this.finish();
            }
        });


    }

    private class CustowebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            if (url.contains("adviraheal.com")) {
                view.loadUrl(url);
            } else {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(i);
            }
            return true;
        }
    }
}
