package com.mehedi.user.ubarparkingapps.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mehedi.user.ubarparkingapps.R;

public class DirectionActivity extends AppCompatActivity {
     private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction);


        webView = findViewById(R.id.webviewid);
       // webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://www.google.com/maps/");
    }
}
