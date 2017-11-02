package com.example.android.popcornmovies_a1.Utilities;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.PublicKey;
import java.util.Scanner;

/**
 * Created by jason on 10/26/2017.
 */

public class NetworkUtils {


    static final String TRACE_TAG = "TRACE";
    static final String URL_TAG = "URL";


    public static final String IMAGE_URL_PATH = "http://image.tmdb.org/t/p";
    public static final String SIZE_w92  = "/w92//";
    public static final String SIZE_w154 = "/w154//";
    public static final String SIZE_w185 = "/w185//";
    public static final String SIZE_w342 = "/w342//";
    public static final String SIZE_w500 = "/w500//";
    public static final String SIZE_w780 = "/w780//";


    public static final String MOVIE_URL_PATH = "http://api.themoviedb.org/3";
    public static final String API_KEY = "your code here";
    public static final String PARAM_API = "api_key";
    public static final String PARAM_PAGE = "page";
    public static final String SORT_BY_POPULAR = "movie/popular";
    public static final String SORT_BY_TOP_RATED = "movie/top_rated";




    public static String getDataFromHTTP(URL url) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            InputStream in = connection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            boolean hasInput = scanner.hasNext();
            if(hasInput)
                return scanner.next();
            else
                return null;
        }
        catch (IOException e){
            e.printStackTrace();
            return null;
        }
        finally {
            connection.disconnect();
        }
    }




    public static URL getMoviePosterURL(String picture, String size){

        Uri buildUri = null;
        buildUri= Uri.parse(IMAGE_URL_PATH).buildUpon()
                .appendEncodedPath(size)
                .appendEncodedPath(picture).build();
        URL url = null;
        try{
            url = new URL(buildUri.toString());
        }
        catch (MalformedURLException e){
            e.printStackTrace();
        }
        Log.v(URL_TAG, "PICTURE URL CREATED: " + url);
        return url;
    }

    public static URL getMovieURL(int pageNo, String mode){
        Uri buildUri = null;
        if (mode == SORT_BY_POPULAR) {
            if (pageNo > 1) {
                buildUri = Uri.parse(MOVIE_URL_PATH).buildUpon()
                        .appendEncodedPath(SORT_BY_POPULAR)
                        .appendQueryParameter(PARAM_API,API_KEY)
                        .appendQueryParameter(PARAM_PAGE,String.valueOf(pageNo))
                        .build();
            } else {
                buildUri = Uri.parse(MOVIE_URL_PATH).buildUpon()
                        .appendEncodedPath(SORT_BY_POPULAR)
                        .appendQueryParameter(PARAM_API,API_KEY)
                        .build();
            }
        }
        else if(mode == SORT_BY_TOP_RATED){
            if (pageNo > 1) {
                buildUri = Uri.parse(MOVIE_URL_PATH).buildUpon()
                        .appendEncodedPath(SORT_BY_TOP_RATED)
                        .appendQueryParameter(PARAM_API,API_KEY)
                        .appendQueryParameter(PARAM_PAGE,String.valueOf(pageNo))
                        .build();
            } else {
                buildUri = Uri.parse(MOVIE_URL_PATH).buildUpon()
                        .appendEncodedPath(SORT_BY_TOP_RATED)
                        .appendQueryParameter(PARAM_API,API_KEY)
                        .build();
            }
        }
        else
            throw new IllegalArgumentException("'"+mode+ "'" + " is not a valid search mode.");
        URL url = null;
        try{
            url = new URL(buildUri.toString());
        }
        catch (MalformedURLException e){
            e.printStackTrace();
        }
        return url;
    }

}
