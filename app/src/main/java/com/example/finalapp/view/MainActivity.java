package com.example.finalapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.example.finalapp.R;

public class MainActivity extends AppCompatActivity {
    private RelativeLayout wrapBtnHome, wrapBtnHistory, wrapBtnDownload, wrapBtnUser;
    private RelativeLayout currentWrapBtn;
    private FragmentManager fragmentManager;
    private HomeFragment homeFragment = new HomeFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // find view
        wrapBtnHome = findViewById(R.id.wrapBtnHome);
        wrapBtnHistory = findViewById(R.id.wrapBtnHistory);
        wrapBtnDownload = findViewById(R.id.wrapBtnDownload);
        wrapBtnUser = findViewById(R.id.wrapBtnUser);
        currentWrapBtn = wrapBtnHome;

        // init
//        fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.frameLayout, homeFragment);
//        fragmentTransaction.commit();

        // event
        View.OnClickListener wrapBtnOnClickListener = (View view) -> {
            activeWrapBtn((RelativeLayout) view);
        };
        wrapBtnHome.setOnClickListener(wrapBtnOnClickListener);
        wrapBtnHistory.setOnClickListener(wrapBtnOnClickListener);
        wrapBtnDownload.setOnClickListener(wrapBtnOnClickListener);
        wrapBtnUser.setOnClickListener(wrapBtnOnClickListener);
    }
    private void activeWrapBtn(RelativeLayout wrapBtn){
        if(currentWrapBtn == wrapBtn) return;
        wrapBtn.setAlpha(1);
        wrapBtn.getChildAt(1).setVisibility(ViewGroup.VISIBLE);
        wrapBtn.setBackgroundResource(R.drawable.shadow);
        currentWrapBtn.setAlpha(0.4f);
        currentWrapBtn.getChildAt(1).setVisibility(ViewGroup.INVISIBLE);
        currentWrapBtn.setBackgroundResource(0);
        currentWrapBtn = wrapBtn;
    }
}