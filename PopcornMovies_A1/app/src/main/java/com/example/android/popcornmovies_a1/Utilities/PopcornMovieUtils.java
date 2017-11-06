package com.example.android.popcornmovies_a1.Utilities;

import android.os.AsyncTask;
import android.util.Log;

import com.example.android.popcornmovies_a1.Data.PopcornMovie;
import com.example.android.popcornmovies_a1.MovieListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.SoftReference;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by jason on 10/26/2017.
 */

public class PopcornMovieUtils {

    public static final String INITIALIZE =  "INITIALIZE";
    public static final String CHANGE_SORT=  "CHANGE SORT";
    public static final String LOG_CONSTRUCTOR = "LOG_CONSTRUCTOR";
    public static final String JSON_2_POPCORN_MOVIE = "JSON_2_POPCORN_MOVIE";


    public static final String DEFAULT_POSTER_SIZE = NetworkUtils.SIZE_w342;
    public static final String DEFAULT_SELECTED_SORT = NetworkUtils.SORT_BY_POPULAR;
    public static final String LOG_INTERNET_ERROR = "INTERNET_ERROR";
    public static final int pageSize = 20;

    private ArrayList<PopcornMovie> movies;
    public ArrayList<PopcornMovie> getMovies(){return movies;}

    public String posterSize;
    public String selectedSort;

    private int pageNo;

    public PopcornMovieUtils(String sort){SwitchSort(sort);}

    public PopcornMovieUtils(){InitializeApp();}



    /*public void GetMoviesFromJSONPage (JSONObject obj){
        try {
            AddMoviesToList(obj.getJSONArray(MovieJsonUtils.PARAM_RESULT));
        } catch (JSONException e) {
            e.printStackTrace();

        }
    }

    private void AddMoviesToList(JSONArray array)  {

        if (array != null){
            for (int i = 0; i < array.length();i++){
                try {
                    this.getMovies().add(new PopcornMovie(array.getJSONObject(i)));
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }
    }*/


    public void InitializeApp(){
        SetPosterSize(DEFAULT_POSTER_SIZE);
        ResetMovieList();
        SetSort(NetworkUtils.SORT_BY_POPULAR);
        SetPage(1);
        AddMovies();
        SetPage(2);
        AddMovies();
    }



    public void SwitchSort(String sort)
    {
        SetSort(sort);
        ResetMovieList();
        SetPage(1);
        AddMovies();
    }

    public void TurnPage(){
        IncrementPage();
        AddMovies();
    }

    public boolean NeedBuffer(int position){return movies.size() - pageSize > position;}

    public void addMovieToList(PopcornMovie movie){
        movies.add((PopcornMovie) movie);
    }

    private void AddMovies(){
        new RetrieveMovies().execute(pageNo);
    }

    public void SetSort(String sort){selectedSort = sort;}
    private void SetPosterSize(String size){posterSize = size;}

    private void IncrementPage(){
        pageNo++;}

    private void SetPage(int pageNo){
        this.pageNo = pageNo;}

    private void ResetMovieList(){
        movies = new ArrayList<PopcornMovie>();}



    public class RetrieveMovies extends AsyncTask<Integer, Void, String> {

        @Override
        protected String doInBackground(Integer... integers) {
            if(integers.length == 0)
                return null;
            else{
                URL Url  =  NetworkUtils.getMovieURL(integers[0], selectedSort);
                return NetworkUtils.getDataFromHTTP(Url);
            }
        }

        @Override
        protected void onPostExecute(String s) {
            if (movies == null)
                Log.e(LOG_INTERNET_ERROR,"This.movies is null");
            else{
                JSONObject page = null;
                JSONArray movieJSONList = null;
                try {
                    page = new JSONObject(s);
                    movieJSONList = page.getJSONArray(MovieJsonUtils.PARAM_RESULT);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (page != null){
                    for(int i = 0; i < movieJSONList.length(); i++){
                        PopcornMovie movie= null;
                        try {
                            movie = new PopcornMovie(movieJSONList.getJSONObject(i));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e(JSON_2_POPCORN_MOVIE, "Popcorn Object not able to be made");
                        }
                        addMovieToList(movie);
                    }
                    int test;
                    test = 1;
                }
                else
                    Log.e(LOG_INTERNET_ERROR,"JSONObject page is null");
            }


        }
    }
}
