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

import javax.crypto.AEADBadTagException;

/**
 * Created by jason on 10/26/2017.
 */

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieListItem>{


    private PopcornMovieUtils controller;



    public MovieListAdapter(String imageSize, String sort){
        controller = new PopcornMovieUtils();
        controller.posterSize = imageSize;
        controller.SetSort(sort);
    }

    public MovieListAdapter(){
        controller = new PopcornMovieUtils();
        controller.SetSort(PopcornMovieUtils.DEFAULT_SELECTED_SORT);
        controller.posterSize = PopcornMovieUtils.DEFAULT_POSTER_SIZE;
        notifyDataSetChanged();
    }

    @Override
    public MovieListAdapter.MovieListItem onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.content_list_item,parent,false);
        return  new MovieListItem(view);
    }

    public void updateMovieList(ArrayList<PopcornMovie> list){
        controller.getMovies().addAll(list);
    }



    @Override
    public void onBindViewHolder(MovieListAdapter.MovieListItem holder, int position) {holder.bind(position);}

    @Override
    public int getItemCount() {return controller.getMovies().size();}

    public class MovieListItem extends RecyclerView.ViewHolder {

        public final ImageView poster;
        public final TextView title;
        public final Context context;

        public MovieListItem(View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.img_poster);
            title = itemView.findViewById(R.id.movie_title);
            context = itemView.getContext();
        }

        public void bind(int position){
            bindPoster(NetworkUtils.getMoviePosterURL(controller.getMovies().get(position).getPosterPath(), controller.posterSize));
            title.setText(controller.getMovies().get(position).getTitle());
            if(controller.NeedBuffer(position)){
                controller.TurnPage();
            }
        }

        private void bindPoster(URL url){
            Picasso.with(context).load(String.valueOf(url)).into(poster);
        }

    }
}
