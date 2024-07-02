package com.baidu.location.demo;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

// News
public class NewsAndEventsActivity extends AppCompatActivity {

    private static final String TAG = "WebViewActivity";
    private WebView wv;
    private AlertDialog alertDialog;

    private ImageButton btn_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_and_events);

        // controllers
        this.btn_back = findViewById(R.id.btn_back);
        this.wv = findViewById(R.id.wv);

        wv.getSettings().setJavaScriptEnabled(true);
        wv.setWebViewClient(new MyClient());
        wv.setWebChromeClient(new MyWebChromeClient());
        //load url
        wv.loadUrl("https://www.civildefence.govt.nz/resources/news-and-events/news/");

        //Loading
        View dialogView = LayoutInflater.from(NewsAndEventsActivity.this).inflate(R.layout.activity_map_dialog, null, false);
        alertDialog = new AlertDialog.Builder(NewsAndEventsActivity.this).setTitle("").setView(dialogView).create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        // back to home
        this.btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewsAndEventsActivity.this.finish();
            }
        });
    }

    class MyClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                view.loadUrl(request.getUrl().toString());
            }
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            Log.e(TAG, "onPageStarted: ");
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Log.e(TAG, "onPageFinished: ");
        }
    }

    class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if(newProgress == 100){
                //close Dialog
                alertDialog.dismiss();
            }
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            setTitle(title);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==event.KEYCODE_BACK&&wv.canGoBack()){
            wv.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}