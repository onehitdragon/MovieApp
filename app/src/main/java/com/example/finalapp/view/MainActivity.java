package com.example.finalapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.finalapp.R;

public class MainActivity extends AppCompatActivity {
    private RelativeLayout wrapBtnHome, wrapBtnHistory, wrapBtnDownload, wrapBtnUser;
    private RelativeLayout currentWrapBtn;
    private View.OnClickListener wrapBtnOnClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // find view
        wrapBtnHome = findViewById(R.id.wrapBtnHome);
        wrapBtnHistory = findViewById(R.id.wrapBtnHistory);
        wrapBtnDownload = findViewById(R.id.wrapBtnDownload);
        wrapBtnUser = findViewById(R.id.wrapBtnUser);

        // init
        currentWrapBtn = wrapBtnHome;
        openFragmentExisted(new HomeFragment(), false, "HomeFragment");

        // event
        wrapBtnOnClickListener = (View view) -> {
            if(currentWrapBtn == view) return;
            activeWrapBtn((RelativeLayout) view);
            currentWrapBtn = (RelativeLayout) view;
        };
        wrapBtnHome.setOnClickListener((View v) -> {
            wrapBtnOnClickListener.onClick(v);
            openFragmentExisted(new HomeFragment(), true, "HomeFragment");
        });
        wrapBtnHistory.setOnClickListener((View v) -> {
            wrapBtnOnClickListener.onClick(v);
            openFragmentExisted(new HistoryLaterFragment(), true, "HistoryFragment");
        });
        wrapBtnDownload.setOnClickListener((View v) -> {
            wrapBtnOnClickListener.onClick(v);
            openFragmentExisted(new DownloadFragment(), true, "DownloadFragment");
        });
        wrapBtnUser.setOnClickListener((View v) -> {
            wrapBtnOnClickListener.onClick(v);
            openFragmentExisted(new SettingFragment(), true, "SettingFragment");
        });
    }

    private void activeWrapBtn(RelativeLayout wrapBtn){
        wrapBtn.setAlpha(1);
        wrapBtn.getChildAt(1).setVisibility(ViewGroup.VISIBLE);
        wrapBtn.setBackgroundResource(R.drawable.shadow);
        currentWrapBtn.setAlpha(0.4f);
        currentWrapBtn.getChildAt(1).setVisibility(ViewGroup.INVISIBLE);
        currentWrapBtn.setBackgroundResource(0);
    }

    private void openFragment(Fragment fragment, boolean addToBackStack, String name){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment, name);
        if(addToBackStack) fragmentTransaction.addToBackStack(name);
        fragmentTransaction.commit();
    }

    public void openFragmentExisted(Fragment fragment, boolean addToBackStack, String name){
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragmentExisted = fragmentManager.findFragmentByTag(name);
        if(fragmentExisted != null){
            closeFragment(name);
        }
        openFragment(fragment, addToBackStack, name);
    }

    public void closeFragment(String name){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack(name, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public void openMovieIntroFragment(){
        MovieIntroFragment movieIntroFragment = new MovieIntroFragment();
        openFragment(movieIntroFragment, true, "MovieIntroFragment");
    }

    public void openMovieWatchingFragment(){
        MovieWatchingFragment movieWatchingFragment = new MovieWatchingFragment();
        openFragment(movieWatchingFragment, true, "MovieWatchingFragment");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FragmentManager fragmentManager = getSupportFragmentManager();
        if(fragmentManager.getBackStackEntryCount() == 0) {
            wrapBtnOnClickListener.onClick(wrapBtnHome);
            return;
        }
        FragmentManager.BackStackEntry backStackEntry = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1);
        String name = backStackEntry.getName();
        Log.e("TAG", "onBackPressed: " + name );
        if(name == null) return;
        switch (name){
            case "HomeFragment" : {
                wrapBtnOnClickListener.onClick(wrapBtnHome);
                break;
            }
            case "HistoryFragment" : {
                wrapBtnOnClickListener.onClick(wrapBtnHistory);
                break;
            }
            case "DownloadFragment" : {
                wrapBtnOnClickListener.onClick(wrapBtnDownload);
                break;
            }
            case "SettingFragment" : {
                wrapBtnOnClickListener.onClick(wrapBtnUser);
                break;
            }
        }
    }
}