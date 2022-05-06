package com.example.finalapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalapp.R;
import com.example.finalapp.model.Genre;

import java.util.ArrayList;

public class RecycleViewGenreOfMovieAdapter extends RecyclerView.Adapter<RecycleViewGenreOfMovieAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Genre> listGenre;

    public RecycleViewGenreOfMovieAdapter(Context context, ArrayList<Genre> listGenre) {
        this.context = context;
        this.listGenre = listGenre;
    }

    @NonNull
    @Override
    public RecycleViewGenreOfMovieAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.genre_of_movie_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewGenreOfMovieAdapter.MyViewHolder holder, int position) {
        holder.bind(listGenre.get(position));
    }

    @Override
    public int getItemCount() {
        return listGenre.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewNameGenre;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNameGenre = itemView.findViewById(R.id.textViewNameGenre);
        }
        public void bind(Genre genre){
            textViewNameGenre.setText(genre.getName());
        }
    }
}
