package com.example.finalapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalapp.R;
import com.example.finalapp.model.Episode;
import com.example.finalapp.view.MovieWatchingFragment;

import java.util.ArrayList;

public class RecycleViewEpisodeListAdapter extends RecyclerView.Adapter<RecycleViewEpisodeListAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Episode> listEpisode;
    private Episode currentEpisode;
    private View currentEpisodeView;
    private MovieWatchingFragment.OnEpisodeClick onEpisodeClick;

    public RecycleViewEpisodeListAdapter(Context context, ArrayList<Episode> listEpisode, Episode currentEpisode, MovieWatchingFragment.OnEpisodeClick onEpisodeClick) {
        this.context = context;
        this.listEpisode = listEpisode;
        this.currentEpisode = currentEpisode;
        this.onEpisodeClick = onEpisodeClick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.episode_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(listEpisode.get(position));
    }

    @Override
    public int getItemCount() {
        return listEpisode.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private Button btnChoiceEpisode;
        private TextView textViewNumber;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            btnChoiceEpisode = itemView.findViewById(R.id.btnChoiceEpisode);
            textViewNumber = itemView.findViewById(R.id.textViewNumber);
        }

        public void bind(Episode episode){
            if(episode == currentEpisode){
                currentEpisodeView = btnChoiceEpisode;
                currentEpisodeView.setBackgroundColor(Color.parseColor("#FFC107"));
            }
            textViewNumber.setText(String.valueOf(episode.getNumber()));
            btnChoiceEpisode.setOnClickListener((View view) -> {
                if(episode != currentEpisode){
                    currentEpisodeView.setBackgroundResource(R.drawable.button_design_2_reverse);
                    currentEpisodeView = btnChoiceEpisode;
                    currentEpisodeView.setBackgroundColor(Color.parseColor("#FFC107"));
                    currentEpisode = episode;
                    onEpisodeClick.click(episode);
                }
            });
        }
    }
}
