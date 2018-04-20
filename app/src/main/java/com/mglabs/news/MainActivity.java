package com.mglabs.news;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.mglabs.news.model.GetArticlesResponse;

import networking.NewsAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.google.android.gms.ads.MobileAds;


public class MainActivity extends AppCompatActivity {

    private RecyclerView newsRecyclerView;
    private HomeNewsAdapter homeNewsAdapter;
    private ProgressBar progressBar;
    private SearchView searchView;
    private SwipeRefreshLayout swipeContainer;
    Call<GetArticlesResponse> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Top 10 Reuters News");

        //initialize Mobile AdMob SDK with AdMob App ID
        MobileAds.initialize(this, "ca-app-pub-9665300231931991~2300935689");


        //AdView request
        AdView adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        newsRecyclerView = findViewById(R.id.activity_main_recyclerview); //to be done after the setNewsArticles on the store
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressBar = findViewById(R.id.activity_main_progressbar);

        searchView = findViewById(R.id.activity_main_searchview);
        searchView.setFocusable(false);



        swipeContainer = findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                call = NewsAPI.getApi().getArticles("reuters");

                call.enqueue(new Callback<GetArticlesResponse>() {

                    @Override
                    public void onResponse(Call<GetArticlesResponse> call, Response<GetArticlesResponse> response) {

                        //homeNewsAdapter.clear();
                        GetArticlesResponse getArticlesResponse = response.body();
                        NewsStore.setNewsArticles(getArticlesResponse.getArticles());
                        homeNewsAdapter = new HomeNewsAdapter(getArticlesResponse.getArticles());
                        newsRecyclerView.setAdapter(homeNewsAdapter);

                        swipeContainer.setRefreshing(false);
                    }

                    @Override
                    public void onFailure(Call<GetArticlesResponse> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this, "Error received", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        //call to networking API service (asynchronously, because we're calling this right from the main thread)
        call = NewsAPI.getApi().getArticles("reuters");
        call.enqueue(new Callback<GetArticlesResponse>() {

            @Override
            public void onResponse(Call<GetArticlesResponse> call, Response<GetArticlesResponse> response) {

                progressBar.setVisibility(View.GONE);
                GetArticlesResponse getArticlesResponse = response.body();
                NewsStore.setNewsArticles(getArticlesResponse.getArticles());
                //Toast.makeText(MainActivity.this, "Response received", Toast.LENGTH_SHORT).show();
                HomeNewsAdapter homeNewsAdapter = new HomeNewsAdapter(getArticlesResponse.getArticles());
                newsRecyclerView.setAdapter(homeNewsAdapter);
            }

            @Override
            public void onFailure(Call<GetArticlesResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Error received", Toast.LENGTH_SHORT).show();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                call = NewsAPI.getApi().getEverything(query, "cnn, bbc-news, the-wall-street-journal, associated-press, fox-news ", "publishedAt");
                call.enqueue(new Callback<GetArticlesResponse>() {

                    @Override
                    public void onResponse(Call<GetArticlesResponse> call, Response<GetArticlesResponse> response) {

                        progressBar.setVisibility(View.GONE);
                        GetArticlesResponse getArticlesResponse = response.body();
                        NewsStore.setNewsArticles(getArticlesResponse.getArticles());
                        HomeNewsAdapter homeNewsAdapter = new HomeNewsAdapter(getArticlesResponse.getArticles());
                        newsRecyclerView.setAdapter(homeNewsAdapter);
                    }

                    @Override
                    public void onFailure(Call<GetArticlesResponse> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this, "Error received", Toast.LENGTH_SHORT).show();
                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }



}
