package networking;

import com.mglabs.news.model.GetArticlesResponse;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by MG on 29/01/18.
 *
 * Retrofit implementation steps:
 * - Add Retrofit dependencies
 * - Create an interface
 * - Define the endpoints in the interface
 * - Create an implementation of the interface
 * - Call the endpoint method
 */

public class NewsAPI {

    public static final String APIKEY = "4d709cdaa4cd476c877d47c395bc338a";
    public static final String APIPATH = "http://newsapi.org/v2/";

    private static NewsService newsService = null;

    //public method to access the NewsService interface (and then any of our endpoints)
    public static NewsService getApi() {
        //as a singleton, we first check whether an instance already exists
        if(newsService == null) {
            //initialize NewsService
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(APIPATH)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            newsService = retrofit.create(NewsService.class);
        }
        return newsService;
    }

    //interface containing calls to all our API endpoints
    public interface NewsService {

        //method representing the article endpoint whose return type is a call of type <getArticlesResponse>.
        //We then need to annotate this method with the @annotation of the relative path of the endpoint
        @GET("top-headlines?apiKey=" + APIKEY)
        Call<GetArticlesResponse> getArticles (@Query("sources") String source);

        @GET("everything?apiKey=" + APIKEY)
        Call<GetArticlesResponse> getEverything (@Query("q") String q, @Query("sources") String sources, @Query("sortBy") String sortBy);

    }

    //We now need an instance of NewsService interface and we want it as a singleton.
}
