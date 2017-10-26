package com.example.android.popcornmovies_a1.Utilities;

import android.os.AsyncTask;
import android.util.Log;

import com.example.android.popcornmovies_a1.Data.Globals;
import com.example.android.popcornmovies_a1.Data.PopcornMovie;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by jason on 10/26/2017.
 */

public class PopcornMovieUtils {


    public static void InitializeApp(){
        SetPosterSize(Globals.DEFAULT_POSTER_SIZE);
        ResetMovieList();
        SetSort(NetworkUtils.SORT_BY_POPULAR);
        SetPage(1);
        AddMovies();
        SetPage(2);
        AddMovies();
    }

    public static void SwitchSort(String sort)
    {
        SetSort(sort);
        ResetMovieList();
        SetPage(1);
        AddMovies();
    }

    public static void TurnPage(){
        IncrementPage();
        AddMovies();
    }


    public boolean NeedBuffer(int position){return Globals.movies.size() - Globals.pageSize > position;}

    private static void AddMovies(){new RetrieveMovies().execute(Globals.pageNo);}

    private static void SetSort(String sort){Globals.SelectedSort = sort;}

    private static void IncrementPage(){Globals.pageNo++;}

    private static void SetPage(int pageNo){Globals.pageNo = pageNo;}

    private static void ResetMovieList(){Globals.movies = new ArrayList<PopcornMovie>();}

    private static void SetPosterSize(String size){Globals.posterSize = size;}

    public static class RetrieveMovies extends AsyncTask<Integer, Void, String> {

        @Override
        protected String doInBackground(Integer... integers) {
            if(integers.length == 0)
                return null;
            else{
                URL Url  =  NetworkUtils.getMovieURL(integers[0], Globals.SelectedSort);
                return NetworkUtils.getDataFromHTTP(Url);
            }
        }

        @Override
        protected void onPostExecute(String s) {
            if (Globals.movies == null)
                Log.e(Globals.LOG_INTERNET_ERROR,"Globals.movies is null");
            else{
                JSONObject page = null;
                try {
                    page = new JSONObject(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (page != null)
                    MovieJsonUtils.GetMoviesFromJSONPage(page);
                else
                    Log.e(Globals.LOG_INTERNET_ERROR,"JSONObject page is null");
            }
        }
    }
}
