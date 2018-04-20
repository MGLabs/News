package com.mglabs.news;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

public class NewsDetailActivity extends AppCompatActivity {

    private WebView webView;
    private ProgressBar progressBar;
    private static final String KEY_INDEX = "news_index";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        webView = findViewById(R.id.activity_news_detail_webview);
        progressBar = findViewById(R.id.activity_news_detail_progressbar);

        //retrieve the index
        int index = getIntent().getIntExtra(KEY_INDEX, -1);
        if(index != -1) {
            updateNewsDetails(index);
        }   else {
            Toast.makeText(NewsDetailActivity.this, "Sorry, incorrect index passed", Toast.LENGTH_LONG).show();
        }
    }

    public void updateNewsDetails (int index) {

        webView.getSettings().setJavaScriptEnabled(true);       //to load the webpage properly

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                progressBar.setVisibility(View.GONE);
                //Toast.makeText(NewsDetailActivity.this, "Error in loading webpage", Toast.LENGTH_SHORT).show();  //every activity is an istance of context
            }
        });

        webView.loadUrl(NewsStore.getNewsArticles().get(index).getUrl());
        getSupportActionBar().setTitle(NewsStore.getNewsArticles().get(index).getTitle());
    }


    /*   Method to retrieve the newsArticle index
     *   We put this method in the NewsDetailsActivity itself so it has control of the implementation
     *   Static so that this activity can be launched from anywhere
     *   int index della newsArticle
     */
    public static void launch (Context context, int index) {

        Intent intent = new Intent(context, NewsDetailActivity.class);
        intent.putExtra(KEY_INDEX, index);
        context.startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
