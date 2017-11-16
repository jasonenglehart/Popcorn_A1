package com.example.android.popcornmovies_a1.Data;

import com.example.android.popcornmovies_a1.Utilities.MovieJsonUtils;

import org.json.JSONObject;

import java.util.Date;

/**
 * Created by jason on 10/26/2017.
 * Class that stores all data from MovieDB into movies
 */

public class PopcornMovie {
    public static final String MOVIE_ID = "MOVIE_ID";
    public static final String MOVIE_TITLE = "Movie Title";
    public static final String MOVIE_POSTER = "Poster Path";
    public static final String MOVIE_GENRE = "Movie Genre";
    public static final String MOVIE_LANGUAGE = "Movie Language";
    public static final String MOVIE_RELEASE_DATE = "Movie release date";
    public static final String MOVIE_POPULARITY = "Movie Popularity";
    public static final String MOVIE_VOTE_AVG = "Movie VoteAVG";
    public static final String MOVIE_VOTE_COUNT= "Movie Count";
    public static final String MOVIE_MATURE= "Movie Mature";
    public static final String MOVIE_OVERVIEW= "Movie Overview";

    public static final String GENRE_LIST = "Genre List";




    private int id;
    private double voteAvg;
    private int voteCount;
    private String title;
    private double popularity;
    private String language;
    private String posterPath;
    private int[] genre;
    private Boolean isMature;
    private String overView;
    private Date release_Date;

    public PopcornMovie(JSONObject obj)  {MovieJsonUtils.PopulateMovie(this,obj);}

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public double getVoteAvg() {
        return voteAvg;
    }
    public void setVoteAvg(double voteAvg) {
        this.voteAvg = voteAvg;
    }
    public int getVoteCount() {
        return voteCount;
    }
    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public double getPopularity() {
        return popularity;
    }
    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }
    public String getLanguage() {
        return language;
    }
    public void setLanguage(String language) {
        this.language = language;
    }
    public String getPosterPath() {
        return posterPath;
    }
    //PosterPath excludes '\/'
    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath.substring(1);
    }
    public int[] getGenre() {
        return genre;
    }
    public void setGenre(int[] genre) {
        this.genre = genre;
    }
    public Boolean getMature() {
        return isMature;
    }
    public void setMature(Boolean mature) {
        isMature = mature;
    }
    public String getOverView() {
        return overView;
    }
    public void setOverView(String overView) {
        this.overView = overView;
    }
    public Date getRelease_Date() {
        return release_Date;
    }
    public void setRelease_Date(Date release_Date) {
        this.release_Date = release_Date;
    }
}
