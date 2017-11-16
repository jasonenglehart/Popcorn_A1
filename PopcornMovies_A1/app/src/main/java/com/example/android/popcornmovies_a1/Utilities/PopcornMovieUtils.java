package com.example.android.popcornmovies_a1.Utilities;

/**
 * Created by jason on 10/26/2017.
 * Some control varables stored here to be used in later versions
 *
 */

public class PopcornMovieUtils {



    public static final String DEFAULT_POSTER_SIZE = NetworkUtils.SIZE_w342;
    public static final String DEFAULT_SELECTED_SORT = NetworkUtils.SORT_BY_POPULAR;


    public String posterSize;
    public String selectedSort;

    private int pageNo;

    public PopcornMovieUtils(){InitializeApp();}

    private void InitializeApp(){
        SetPosterSize(DEFAULT_POSTER_SIZE);
        SetPage(1);
    }

    public void SetSort(String sort){selectedSort = sort;}
    private void SetPosterSize(String size){posterSize = size;}

    private void SetPage(int pageNo){
        this.pageNo = pageNo;}



}
