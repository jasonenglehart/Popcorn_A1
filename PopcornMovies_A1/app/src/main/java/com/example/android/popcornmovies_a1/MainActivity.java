package com.example.android.popcornmovies_a1;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.android.popcornmovies_a1.Utilities.PopcornMovieUtils;

public class MainActivity extends AppCompatActivity {
    MovieListAdapter mAdapeter;
    RecyclerView mRecyclerview;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PopcornMovieUtils.InitializeApp();

        mAdapeter = new MovieListAdapter();
        mRecyclerview = (RecyclerView) findViewById(R.id.rv_main);
        mRecyclerview.setAdapter(mAdapeter);

        context = getApplicationContext();

        GridLayoutManager mGridManager = new GridLayoutManager(context,2);

        mRecyclerview.setLayoutManager(mGridManager);

        mRecyclerview.setHasFixedSize(true);
    }
}