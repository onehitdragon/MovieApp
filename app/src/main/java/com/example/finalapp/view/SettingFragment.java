package com.example.finalapp.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.finalapp.R;

public class SettingFragment extends Fragment {
    private Context context;
    private Button btnYoutube, btnDiscord, btnGithub;
    private String youtubeUrl = "https://www.youtube.com/channel/UClN-6RYy1Dvr1eAUTqn8HgQ";
    private String discordUrl = "https://discord.gg/H3rgYFVk";
    private String githubUrl = "https://github.com/onehitdragon";

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // find view
        btnYoutube = view.findViewById(R.id.btnYoutube);
        btnDiscord = view.findViewById(R.id.btnDiscord);
        btnGithub = view.findViewById(R.id.btnGithub);

        // init
        Intent intent = new Intent(Intent.ACTION_VIEW);

        // event
        btnYoutube.setOnClickListener((View v) -> {
            intent.setData(Uri.parse(youtubeUrl));
            startActivity(intent);
        });
        btnDiscord.setOnClickListener((View v) -> {
            intent.setData(Uri.parse(discordUrl));
            startActivity(intent);
        });
        btnGithub.setOnClickListener((View v) -> {
            intent.setData(Uri.parse(githubUrl));
            startActivity(intent);
        });
    }
}