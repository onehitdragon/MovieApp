package com.example.finalapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalapp.R;
import com.example.finalapp.model.HistoryMovie;
import com.example.finalapp.model.Movie;
import com.example.finalapp.viewmodel.LaterMovieViewModelFrag;

import java.util.ArrayList;

public class RecycleViewLaterMovieListAdapter extends RecyclerView.Adapter<RecycleViewLaterMovieListAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Movie> listMovie;
    private Movie.OnMovieClick onMovieClick;
    private LaterMovieViewModelFrag laterMovieViewModelFrag;

    public RecycleViewLaterMovieListAdapter(Context context, ArrayList<Movie> listMovie, Movie.OnMovieClick onMovieClick, LaterMovieViewModelFrag laterMovieViewModelFrag) {
        this.context = context;
        this.listMovie = listMovie;
        this.onMovieClick = onMovieClick;
        this.laterMovieViewModelFrag = laterMovieViewModelFrag;
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
        public void bind(Movie movie){
            Glide.with(context).load(movie.getAvatarUrl()).into(imageViewAvatar);
            textViewNameMovie.setText(movie.getTitle());
            textViewRating.setText(String.valueOf(movie.getRating()));
            textViewGenreName.setText("Xem sau");
            textViewEngName.setText(movie.getEngTitle());
            itemView.setOnClickListener((View view) -> {
                onMovieClick.click(movie);
            });
            itemView.setOnLongClickListener((View v) -> {
                PopupMenu popupMenu = new PopupMenu(context, itemView);
                popupMenu.inflate(R.menu.later_movie_menu);
                popupMenu.setOnMenuItemClickListener((MenuItem item) -> {
                    int id = item.getItemId();
                    if(id == R.id.deleteFromLater){
                        laterMovieViewModelFrag.deleteMovieFromLater(movie);
                    }
                    return false;
                });
                popupMenu.show();
                return true;
            });
        }
    }
}
