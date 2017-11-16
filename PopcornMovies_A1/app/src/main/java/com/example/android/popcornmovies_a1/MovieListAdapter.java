package com.example.android.popcornmovies_a1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popcornmovies_a1.Data.PopcornMovie;
import com.example.android.popcornmovies_a1.Utilities.NetworkUtils;
import com.example.android.popcornmovies_a1.Utilities.PopcornMovieUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by jason on 10/26/2017.
 * Adapter Class to retrieve data from source and place it into the recylcerView
 */

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieListItem>{


    PopcornMovieUtils controller;
    private ArrayList<PopcornMovie> movies;
    private MovieViewOnClickHandler mClickHandler;
    String JSON_Genres;


    MovieListAdapter(MovieViewOnClickHandler clickHandler){
        mClickHandler = clickHandler;
        movies = new ArrayList<>();
        controller = new PopcornMovieUtils();
        controller.SetSort(PopcornMovieUtils.DEFAULT_SELECTED_SORT);
        controller.posterSize = PopcornMovieUtils.DEFAULT_POSTER_SIZE;
    }

    public interface MovieViewOnClickHandler{
        void onClick(PopcornMovie movie);
    }

    @Override
    public MovieListAdapter.MovieListItem onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.content_list_item,parent,false);
        return  new MovieListItem(view);
    }

    void updateMovieList(ArrayList<PopcornMovie> list){
        movies.addAll(list);
        notifyDataSetChanged();
    }
    void resetMovieList(){
        movies = new ArrayList<>();
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(MovieListAdapter.MovieListItem holder, int position) {holder.bind(position);}

    @Override
    public int getItemCount() {return movies.size();}

    public class MovieListItem extends RecyclerView.ViewHolder  implements View.OnClickListener{

        private final ImageView poster;
        private final TextView title;
        private final Context context;
        private int position;

        private MovieListItem(View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.img_poster);
            title = itemView.findViewById(R.id.movie_title);
            context = itemView.getContext();
            itemView.setOnClickListener(this);
        }


        private void bind(int position){
            bindPoster(NetworkUtils.getMoviePosterURL(movies.get(position).getPosterPath(), controller.posterSize));
            title.setText(movies.get(position).getTitle());
            this.position = position;
        }

        private void bindPoster(URL url){
            Picasso.with(context).load(String.valueOf(url)).into(poster);
        }

        @Override
        public void onClick(View v) {
            PopcornMovie movie = movies.get(position);
            mClickHandler.onClick(movie);
        }
    }
}
