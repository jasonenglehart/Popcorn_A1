package com.example.android.popcornmovies_a1;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.android.popcornmovies_a1.Data.PopcornMovie;
import com.example.android.popcornmovies_a1.Utilities.EndlessRecyclerViewScrollListener;
import com.example.android.popcornmovies_a1.Utilities.MovieJsonUtils;
import com.example.android.popcornmovies_a1.Utilities.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieListAdapter.MovieViewOnClickHandler{
    private RecyclerView mRecyclerview;
    private Context context;
    private MovieListAdapter adapter;
    private LinearLayout mErrorMsg;
    private LinearLayout mLostConnection;
    private Button mReconnect;
    private Button mRestart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerview = (RecyclerView) findViewById(R.id.rv_main);
        mErrorMsg = (LinearLayout)findViewById(R.id.start_load_error);
        mReconnect = (Button)findViewById(R.id.reconnect);
        mRestart = (Button)findViewById(R.id.start_again);
        mLostConnection = (LinearLayout)findViewById(R.id.lost_conection_layout);
        if (isOnline())
            start();
        else
            displayStartUpError();
    }

    private void start(){
        mRecyclerview.setVisibility(View.VISIBLE);
        mErrorMsg.setVisibility(View.GONE);
        mLostConnection.setVisibility(View.GONE);
        adapter = new MovieListAdapter(this);
        new RetrieveGenres().execute();
        mRecyclerview = (RecyclerView) findViewById(R.id.rv_main);
        mRecyclerview.setAdapter(adapter);
        context = getApplicationContext();
        GridLayoutManager mGridManager = new GridLayoutManager(context,2);
        mRecyclerview.setLayoutManager(mGridManager);
        mRecyclerview.setHasFixedSize(true);
        new RetrieveMovies().execute(1);
        mRecyclerview.addOnScrollListener(new EndlessRecyclerViewScrollListener(mGridManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (isOnline())
                    new RetrieveMovies().execute(page);
                else
                    showLostConnectionView();
            }
        });
    }

    private void displayStartUpError(){
        mRecyclerview.setVisibility(View.GONE);
        mErrorMsg.setVisibility(View.VISIBLE);
        mReconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOnline())
                    start();
            }
        });
    }

    private void showLostConnectionView(){
        mLostConnection.setVisibility(View.VISIBLE);
        mRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOnline()){
                    mLostConnection.setVisibility(View.GONE);
                    adapter.resetMovieList();
                    new RetrieveMovies().execute(1);
                }
            }
        });
    }





    private Intent packageIntent(Intent intent, PopcornMovie movie, String genres){
        intent.putExtra(PopcornMovie.MOVIE_ID,movie.getId());
        intent.putExtra(PopcornMovie.MOVIE_TITLE,movie.getTitle());
        intent.putExtra(PopcornMovie.MOVIE_POSTER,movie.getPosterPath());
        intent.putExtra(PopcornMovie.MOVIE_GENRE,movie.getGenre());
        intent.putExtra(PopcornMovie.MOVIE_VOTE_AVG,movie.getVoteAvg());
        intent.putExtra(PopcornMovie.MOVIE_VOTE_COUNT,movie.getVoteCount());
        intent.putExtra(PopcornMovie.MOVIE_POPULARITY,movie.getPopularity());
        intent.putExtra(PopcornMovie.MOVIE_MATURE,movie.getMature());
        intent.putExtra(PopcornMovie.MOVIE_LANGUAGE,movie.getLanguage());
        intent.putExtra(PopcornMovie.MOVIE_RELEASE_DATE,movie.getRelease_Date());
        intent.putExtra(PopcornMovie.MOVIE_OVERVIEW,movie.getOverView());
        intent.putExtra(PopcornMovie.GENRE_LIST,genres);
        return  intent;
    }

    @Override
    public void onClick(PopcornMovie movie) {
        Class destination = MovieDetails.class;
        Intent intent = new Intent(context,destination);
        intent = packageIntent(intent,movie,adapter.JSON_Genres);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemID = item.getItemId();
        if (itemID == R.id.sortByMethod){
            if(adapter.controller.selectedSort == NetworkUtils.SORT_BY_POPULAR){
                changeSort(NetworkUtils.SORT_BY_TOP_RATED);
                item.setTitle(getText(R.string.sortByTopRated));
            }
            else{
                changeSort(NetworkUtils.SORT_BY_POPULAR);
                item.setTitle(getText(R.string.sortByPopular));
            }
        }

        return true;

    }

    private void changeSort(String sort){
        adapter.controller.selectedSort = sort;
        adapter.resetMovieList();
        new RetrieveMovies().execute(1);
    }

    public class RetrieveMovies extends AsyncTask<Integer, Void, String> {

        @Override
        protected String doInBackground(Integer... integers) {
            if(integers.length == 0)
                return null;
            else{
                URL Url  =  NetworkUtils.getMovieURL(integers[0], adapter.controller.selectedSort);
                return NetworkUtils.getDataFromHTTP(Url);
            }
        }

        @Override
        protected void onPostExecute(String s) {
            if (s.isEmpty())
                Log.e("something","This.movies is null");
            else{
                JSONObject page = null;
                JSONArray movieJSONList = null;
                try {
                    page = new JSONObject(s);
                    movieJSONList = page.getJSONArray(MovieJsonUtils.PARAM_RESULT);
                } catch (JSONException e) {

                Log.e(NetworkUtils.JSON_GET, "getJSONArray Failed to work");
                    e.printStackTrace();
                }
                ArrayList<PopcornMovie> movies = new ArrayList<>();
                if (movieJSONList != null){
                    for(int i = 0; i < movieJSONList.length(); i++){
                        try {
                            movies.add(new PopcornMovie(movieJSONList.getJSONObject(i)));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            //Log.e(JSON_2_POPCORN_MOVIE, "Popcorn Object not able to be made");
                        }
                    }
                    adapter.updateMovieList(movies);
                }
                   // Log.e(LOG_INTERNET_ERROR,"JSONObject page is null");
            }
        }
    }

    public class RetrieveGenres extends AsyncTask<Void,Void,String>{

        @Override
        protected String doInBackground(Void... voids) {
            URL url = NetworkUtils.getGenreListURL();
            return NetworkUtils.getDataFromHTTP(url);
        }
        @Override
        protected void onPostExecute(String s) {
            if (s == null || s.isEmpty())
                Log.e(NetworkUtils.JSON_GET,"Unable to retrieve Genres");
            else
                adapter.JSON_Genres = s;
        }
    }
    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = null;
        if (cm != null) {
            netInfo = cm.getActiveNetworkInfo();
        }
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
