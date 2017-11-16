package com.example.android.popcornmovies_a1;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popcornmovies_a1.Data.PopcornMovie;
import com.example.android.popcornmovies_a1.Utilities.MovieJsonUtils;
import com.example.android.popcornmovies_a1.Utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.Locale;

public class MovieDetails extends AppCompatActivity {

    private TextView mTitle;
    private ImageView mPoster;
    private TextView mGenre;
    private TextView mLanguage;
    private TextView mReleaseDate;
    private TextView mVotes;
    private TextView mPopularity;
    private TextView mMature;
    private TextView mOverView;




    private final String defaultDetailPosterSize = NetworkUtils.SIZE_w780;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        mTitle = (TextView) findViewById(R.id.movie_details_title);
        mPoster = (ImageView)findViewById(R.id.movie_details_poster);
        mGenre = (TextView)findViewById(R.id.movie_details_genre);
        mLanguage = (TextView)findViewById(R.id.movie_details_language);
        mReleaseDate = (TextView)findViewById(R.id.movie_details_release_date);
        mVotes = (TextView)findViewById(R.id.movie_details_votes);
        mPopularity=(TextView)findViewById(R.id.movie_details_popularity);
        mMature = (TextView)findViewById(R.id.movie_details_mature);
        mOverView = (TextView)findViewById(R.id.movie_details_overview);




        Context context = getApplicationContext();

        Intent inBound = getIntent();

        if (inBound != null){
            if (VerifyIntent(inBound)){
                PopulateActivity(context,inBound);
            }
        }
    }

    private boolean VerifyIntent(Intent intent){
        return (intent.hasExtra(PopcornMovie.MOVIE_ID)
        && intent.hasExtra(PopcornMovie.MOVIE_TITLE)
        && intent.hasExtra(PopcornMovie.MOVIE_POSTER)
        && intent.hasExtra(PopcornMovie.MOVIE_GENRE)
        && intent.hasExtra(PopcornMovie.MOVIE_VOTE_AVG)
        && intent.hasExtra(PopcornMovie.MOVIE_VOTE_COUNT)
        && intent.hasExtra(PopcornMovie.MOVIE_POPULARITY)
        && intent.hasExtra(PopcornMovie.MOVIE_MATURE)
        && intent.hasExtra(PopcornMovie.MOVIE_LANGUAGE)
        && intent.hasExtra(PopcornMovie.MOVIE_RELEASE_DATE)
        && intent.hasExtra(PopcornMovie.MOVIE_OVERVIEW));
    }

    private void PopulateActivity(Context context, Intent intent){
        mTitle.setText(intent.getStringExtra(PopcornMovie.MOVIE_TITLE));

        String imageID = intent.getStringExtra(PopcornMovie.MOVIE_POSTER);
        URL url = NetworkUtils.getMoviePosterURL(imageID,defaultDetailPosterSize);
        Picasso.with(context).load(String.valueOf(url)).into(mPoster);

        String allMoviesGenreList = intent.getStringExtra(PopcornMovie.GENRE_LIST);
        int[] thisMoviesGenreList = intent.getIntArrayExtra(PopcornMovie.MOVIE_GENRE);

        String genreText = getText(R.string.genreField )+ MovieJsonUtils.getGenresFormJSONObj(allMoviesGenreList,thisMoviesGenreList);
        mGenre.setText(genreText);

        String languageCode = intent.getStringExtra(PopcornMovie.MOVIE_LANGUAGE);
        Locale loc = new Locale(languageCode);
        String languageName = loc.getDisplayLanguage(loc);
        String languageText = getText(R.string.languageField) + languageName;
        mLanguage.setText(languageText);

        String releaseDateText = getText(R.string.releaseDateField) + intent.getSerializableExtra(PopcornMovie.MOVIE_RELEASE_DATE).toString();
        mReleaseDate.setText(releaseDateText);

        String voteText = getText(R.string.voteAvgField) + String.valueOf(intent.getDoubleExtra(PopcornMovie.MOVIE_VOTE_AVG,0))+ getText(R.string.voteCountField) + String.valueOf(intent.getIntExtra(PopcornMovie.MOVIE_VOTE_COUNT,0));
        mVotes.setText(voteText);

        String popularityText = getText(R.string.popularityField) + String.valueOf(intent.getDoubleExtra(PopcornMovie.MOVIE_POPULARITY,0));
        mPopularity.setText(popularityText);

        if (intent.getBooleanExtra(PopcornMovie.MOVIE_MATURE,false))
        {
            mMature.setVisibility(View.INVISIBLE);
        }

        mOverView.setText(intent.getStringExtra(PopcornMovie.MOVIE_OVERVIEW));
    }




}
