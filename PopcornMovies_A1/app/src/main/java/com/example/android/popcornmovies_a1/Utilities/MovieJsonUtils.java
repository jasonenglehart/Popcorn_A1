package com.example.android.popcornmovies_a1.Utilities;

import com.example.android.popcornmovies_a1.Data.PopcornMovie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;

/**
 * Created by jason on 10/26/2017.
 */

public class MovieJsonUtils {
    static final String TRACE = "Trace";

    public static final String PARAM_RESULT = "results";

    public static final String ID = "id";
    public static final String VOTE_COUNT = "vote_count";
    public static final String VOTE_AVERAGE = "vote_average";
    public static final String TITLE = "title";
    public static final String POPULARITY = "popularity";
    public static final String LANGUAGE = "original_language";
    public static final String POSTER_PATH= "poster_path";
    public static final String GENRES = "genre_ids";
    public static final String IS_MATURE = "adult";
    public static final String OVERVIEW = "overview";
    public static final String RELEASE_DATE = "release_date";


    public static boolean ValidateJsonObj(JSONObject obj){
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
                mov.setVoteCount(movieJson.getDouble(VOTE_COUNT));
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

}
