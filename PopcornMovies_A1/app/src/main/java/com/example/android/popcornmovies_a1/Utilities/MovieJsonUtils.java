package com.example.android.popcornmovies_a1.Utilities;

import android.util.Log;

import com.example.android.popcornmovies_a1.Data.PopcornMovie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jason on 10/26/2017.
 * Class that facilitates JSON data function
 * It will write data to PopCorn Movie Object
 */

public class MovieJsonUtils {


    public static final String PARAM_RESULT = "results";

    private static final String ID = "id";
    private static final String VOTE_COUNT = "vote_count";
    private static final String VOTE_AVERAGE = "vote_average";
    private static final String TITLE = "title";
    private static final String POPULARITY = "popularity";
    private static final String LANGUAGE = "original_language";
    private static final String POSTER_PATH= "poster_path";
    private static final String GENRES = "genre_ids";
    private static final String IS_MATURE = "adult";
    private static final String OVERVIEW = "overview";
    private static final String RELEASE_DATE = "release_date";

    private static final String GENRE_LIST = "genres";
    private static final String GENRE_LIST_ID = "id";
    private static final String GENRE_LIST_NAME = "name";


    private static boolean ValidateJsonObj(JSONObject obj){
        return (obj.has(ID) &&
                obj.has(VOTE_COUNT) &&
                obj.has(VOTE_AVERAGE) &&
                obj.has(TITLE) &&
                obj.has(POPULARITY) &&
                obj.has(LANGUAGE) &&
                obj.has(POSTER_PATH) &&
                obj.has(GENRES) &&
                obj.has(IS_MATURE) &&
                obj.has(OVERVIEW) &&
                obj.has(RELEASE_DATE));}

    public static void PopulateMovie(PopcornMovie mov, JSONObject movieJson)  {
        try{
            if (ValidateJsonObj(movieJson)){
                mov.setId(movieJson.getInt(ID));
                mov.setVoteCount(movieJson.getInt(VOTE_COUNT));
                mov.setVoteAvg(movieJson.getDouble(VOTE_AVERAGE));
                mov.setTitle(movieJson.getString(TITLE));
                mov.setPopularity(movieJson.getDouble(POPULARITY));
                mov.setLanguage(movieJson.getString(LANGUAGE));
                mov.setPosterPath(movieJson.getString(POSTER_PATH));
                JSONArray genresJS = movieJson.getJSONArray(GENRES);
                int[] genres = new int[genresJS.length()];
                for (int i = 0; i < genresJS.length(); i++) {
                    genres[i] = genresJS.getInt(i);
                }
                mov.setGenre(genres);
                mov.setMature(movieJson.getBoolean(IS_MATURE));
                mov.setOverView(movieJson.getString(OVERVIEW));
                mov.setRelease_Date(Date.valueOf(movieJson.getString(RELEASE_DATE)));
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }

    public static String getGenresFormJSONObj(String list, int[] movieGenres){

        JSONObject JSONList = null;
        try {
            JSONList = new JSONObject(list);
        } catch (JSONException e) {
            Log.e(NetworkUtils.JSON_GET,"getGenreFromJSONObj List is not a JSON obj");
            e.printStackTrace();
        }
        JSONArray genreList = null;

        try {
            genreList = JSONList.getJSONArray(GENRE_LIST);
        } catch (JSONException e) {
            Log.e(NetworkUtils.JSON_GET,"getGenreFromJSONObj JSON OBJ does not have genres array");
            e.printStackTrace();
        }

        Map<Integer, String> ID2Genre = new HashMap<Integer, String>();

        for (int i = 0; i <genreList.length();i++){
            JSONObject singleGenreFromList = null;
            try {
                singleGenreFromList = genreList.getJSONObject(i);
            } catch (JSONException e) {
                Log.e(NetworkUtils.JSON_GET,"getGenreFromJSONObj genreList IsCorrupted");
                e.printStackTrace();
            }
            Integer genreID =null;
            try {
                genreID = singleGenreFromList.getInt(GENRE_LIST_ID);
            } catch (JSONException e) {
                Log.e(NetworkUtils.JSON_GET,"getGenreFromJSONObj genreList.id is not an integer");
                e.printStackTrace();
            }
            String genreName = null;
            try {
                genreName = singleGenreFromList.getString(GENRE_LIST_NAME);
            } catch (JSONException e) {
                Log.e(NetworkUtils.JSON_GET,"getGenreFromJSONObj genreList.name is not an string");
                e.printStackTrace();
            }
            ID2Genre.put(genreID,genreName);
        }
        String returnString = "";
        for (int j = 0; j < movieGenres.length; j++)
        {
            if (j != 0){
                returnString += ", ";
            }
            returnString += ID2Genre.get(movieGenres[j]);
        }
        return returnString;
    }



}
