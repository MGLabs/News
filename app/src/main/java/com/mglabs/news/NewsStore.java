package com.mglabs.news;

import com.mglabs.news.model.Article;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mglabs on 22/01/2018.
 *
 * Static list of news for other parts of the app to access the news (i.e details page).
 */

public class NewsStore {
    private static List<Article> newsArticles = new ArrayList<>();

    public static List<Article> getNewsArticles() {
        return newsArticles;
    }

    public static void setNewsArticles(List<Article> newsArticles) {
        NewsStore.newsArticles = newsArticles;
    }

}
