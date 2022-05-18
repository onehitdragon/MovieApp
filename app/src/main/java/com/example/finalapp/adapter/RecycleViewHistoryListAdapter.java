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
import com.example.finalapp.model.HistoryMovie;
import com.example.finalapp.model.Movie;

import java.util.ArrayList;
import java.util.Calendar;

public class RecycleViewHistoryListAdapter extends RecyclerView.Adapter<RecycleViewHistoryListAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<HistoryMovie> listMovie;
    private Movie.OnMovieClick onMovieClick;

    public RecycleViewHistoryListAdapter(Context context, ArrayList<HistoryMovie> listMovie, Movie.OnMovieClick onMovieClick) {
        this.context = context;
        this.listMovie = listMovie;
        this.onMovieClick = onMovieClick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.history_movie_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(listMovie.get(position));
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
        public void bind(HistoryMovie historyMovie){
            Glide.with(context).load(historyMovie.getAvatarUrl()).into(imageViewAvatar);
            textViewNameMovie.setText(historyMovie.getTitle());
            textViewRating.setText(String.valueOf(historyMovie.getRating()));
            textViewGenreName.setText(historyMovie.calcPreviousTime());
            textViewEngName.setText(historyMovie.getEngTitle());
            itemView.setOnClickListener((View view) -> {
                onMovieClick.click(historyMovie);
            });
        }
    }
}
