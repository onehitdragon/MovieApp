package com.example.finalapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalapp.R;
import com.example.finalapp.model.Genre;
import com.example.finalapp.view.HomeFragment;

import java.util.ArrayList;

public class RecycleViewGenreMenuAdapter extends RecyclerView.Adapter<RecycleViewGenreMenuAdapter.MyViewHolder> {
    private final Context context;
    private final ArrayList<Genre> listGenre;
    private Genre currentGenre;
    private View currentGenreView;
    private final HomeFragment.OnGenreMenuItemClickListener onGenreMenuItemClickListener;

    public RecycleViewGenreMenuAdapter(Context context, ArrayList<Genre> listGenre,
                                       HomeFragment.OnGenreMenuItemClickListener onGenreMenuItemClickListener,
                                       Genre currentGenre) {
        this.context = context;
        this.listGenre = listGenre;
        this.onGenreMenuItemClickListener = onGenreMenuItemClickListener;
        this.currentGenre = currentGenre;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.genre_menu_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(listGenre.get(position));
    }

    @Override
    public int getItemCount() {
        return listGenre.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewNameGenre;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNameGenre = itemView.findViewById(R.id.textViewNameGenre);
        }
        public void bind(Genre genre){
            if(currentGenre == genre){
                currentGenreView = itemView;
                ((CardView)currentGenreView).setCardBackgroundColor(Color.parseColor("#414a4c"));
            }
            textViewNameGenre.setText(genre.getName());
            itemView.setOnClickListener((View view) -> {
                if(genre != currentGenre){
                    ((CardView)currentGenreView).setCardBackgroundColor(Color.TRANSPARENT);
                    currentGenreView = itemView;
                    ((CardView)currentGenreView).setCardBackgroundColor(Color.parseColor("#414a4c"));
                    currentGenre = genre;
                    onGenreMenuItemClickListener.click(genre);
                }
            });
        }
    }
}
