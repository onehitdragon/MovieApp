package com.example.finalapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalapp.R;
import com.example.finalapp.model.Movie;
import com.example.finalapp.view.HomeFragment;

import java.util.ArrayList;

public class RecycleViewSearchResultListAdapter extends RecyclerView.Adapter<RecycleViewSearchResultListAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Movie> listMovie;
    private Movie.OnMovieClick onMovieClick;

    public RecycleViewSearchResultListAdapter(Context context, ArrayList<Movie> listMovie, Movie.OnMovieClick onMovieClick) {
        this.context = context;
        this.listMovie = listMovie;
        this.onMovieClick = onMovieClick;
    }

    @NonNull
    @Override
    public RecycleViewSearchResultListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.search_result_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewSearchResultListAdapter.MyViewHolder holder, int position) {
        holder.bind(listMovie.get(position));
    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewNameMovie;
        private TextView textViewEngName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNameMovie = itemView.findViewById(R.id.textViewNameMovie);
            textViewEngName = itemView.findViewById(R.id.textViewEngName);
        }

        public void bind(Movie movie){
            textViewNameMovie.setText(movie.getTitle());
            textViewEngName.setText(movie.getEngTitle());
            itemView.setOnClickListener((View view) -> {
                onMovieClick.click(movie);
            });
        }
    }
}
