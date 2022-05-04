package com.example.finalapp.adapter;

import android.content.Context;
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
    private Context context;
    private ArrayList<Genre> listGenre;
    private HomeFragment.OnGenreMenuItemClickListener onGenreMenuItemClickListener;

    public RecycleViewGenreMenuAdapter(Context context, ArrayList<Genre> listGenre, HomeFragment.OnGenreMenuItemClickListener onGenreMenuItemClickListener) {
        this.context = context;
        this.listGenre = listGenre;
        this.onGenreMenuItemClickListener = onGenreMenuItemClickListener;
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
        holder.bind(position, listGenre.get(position), onGenreMenuItemClickListener);
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
        public void bind(int position, Genre genre, HomeFragment.OnGenreMenuItemClickListener onGenreMenuItemClickListener){
            textViewNameGenre.setText(genre.getName());
            itemView.setOnClickListener((View view) -> {
                onGenreMenuItemClickListener.click((CardView) view , genre);
            });
        }
    }
}
