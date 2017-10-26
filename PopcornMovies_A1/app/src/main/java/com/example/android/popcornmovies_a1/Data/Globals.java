package com.example.android.popcornmovies_a1.Data;

import com.example.android.popcornmovies_a1.Utilities.NetworkUtils;

import java.util.ArrayList;

/**
 * Created by jason on 10/26/2017.
 */

public class Globals {

    public static final String DEFAULT_POSTER_SIZE = NetworkUtils.SIZE_w185;

    public  static String posterSize;
    public static ArrayList<PopcornMovie> movies;
    public static String SelectedSort;
    public static int pageNo;
    public static int pageSize = 20;


    public static final String LOG_INTERNET_ERROR = "INTERNET_ERROR";
}
