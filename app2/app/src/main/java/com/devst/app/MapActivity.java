package com.devst.app;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MapActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        webView = findViewById(R.id.webViewMap);

        Intent intent = getIntent();
        double lat = intent.getDoubleExtra("latitude", 0);
        double lng = intent.getDoubleExtra("longitude", 0);

        // If no coordinates passed, use default location (Santiago, Chile)
        if (lat == 0 && lng == 0) {
            lat = -33.447487;
            lng = -70.673676;
        }

        // Build Google Maps URL with marker at the coordinates
        String mapsUrl = "https://www.google.com/maps?q=" + lat + "," + lng + "&z=15";

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(mapsUrl);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
