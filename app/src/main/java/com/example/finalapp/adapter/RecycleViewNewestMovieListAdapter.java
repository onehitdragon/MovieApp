package com.example.finalapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalapp.R;
import com.example.finalapp.model.Movie;
import com.example.finalapp.mymenu.MyMenu;
import com.example.finalapp.view.HomeFragment;

import java.util.ArrayList;

public class RecycleViewNewestMovieListAdapter extends RecyclerView.Adapter<RecycleViewNewestMovieListAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Movie> listMovie;
    private Movie.OnMovieClick onMovieClick;

    public ArrayList<Movie> getListMovie() {
        return listMovie;
    }

    public void setListMovie(ArrayList<Movie> listMovie) {
        this.listMovie = listMovie;
    }

    public RecycleViewNewestMovieListAdapter(Context context, ArrayList<Movie> listMovie, Movie.OnMovieClick onMovieClick) {
        this.context = context;
        this.listMovie = listMovie;
        this.onMovieClick = onMovieClick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.newest_movie_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Movie movie = listMovie.get(position);
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewAvatar;
        private TextView textViewRating;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewAvatar = itemView.findViewById(R.id.imageViewAvatar);
            textViewRating = itemView.findViewById(R.id.textViewRating);
        }
        public void bind(Movie movie){
            Glide.with(context).load(movie.getAvatarUrl()).into(imageViewAvatar);
            textViewRating.setText(String.valueOf(movie.getRating()));
            itemView.setOnClickListener((View view) -> {
                onMovieClick.click(movie);
            });
            itemView.setOnLongClickListener((View v) -> {
                MyMenu myMenu = new MyMenu(context);
                PopupMenu popupMenu = myMenu.createMovieMenu(itemView, movie, onMovieClick);
                popupMenu.show();

                return true;
            });
        }
    }
}
