package com.example.finalapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalapp.R;
import com.example.finalapp.model.Genre;
import com.example.finalapp.model.Movie;

import java.util.ArrayList;

public class RecycleViewGenreMovieListAdapter extends RecyclerView.Adapter<RecycleViewGenreMovieListAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Movie> listMovie;
    private Genre currentGenre;

    public RecycleViewGenreMovieListAdapter(Context context, ArrayList<Movie> listMovie, Genre currentGenre) {
        this.context = context;
        this.listMovie = listMovie;
        this.currentGenre = currentGenre;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.genre_movie_item, parent, false);
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
        private TextView textViewNameMovie, textViewRating, textViewGenreName, textViewEngName;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewAvatar = itemView.findViewById(R.id.imageViewAvatar);
            textViewNameMovie = itemView.findViewById(R.id.textViewNameMovie);
            textViewRating = itemView.findViewById(R.id.textViewRating);
            textViewGenreName = itemView.findViewById(R.id.textViewGenreName);
            textViewEngName = itemView.findViewById(R.id.textViewEngName);
        }
        public void bind(Movie movie){
            Glide.with(context).load(movie.getAvatarUrl()).into(imageViewAvatar);
            textViewNameMovie.setText(movie.getTitle());
            textViewRating.setText(String.valueOf(movie.getRating()));
            textViewGenreName.setText(currentGenre.getName());
            textViewEngName.setText(movie.getEngTitle());
        }
    }
}
