package com.example.finalapp.myvalidation;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.finalapp.R;

public class MyValidation {
    public static void changeBackgroundEditText(Context context, EditText editText, int status){
        ImageView imgView = null;
        ViewGroup viewGroup = (ViewGroup) editText.getParent();
        if(viewGroup.getChildAt(1) != null){
            imgView = (ImageView) viewGroup.getChildAt(1);
        }
        if(status == 0){
            editText.setBackgroundResource(R.drawable.edit_text_design_0);
            if(imgView != null){
                Glide.with(context).clear(imgView);
            }
        }
        if(status == 1){
            editText.setBackgroundResource(R.drawable.edit_text_design_0_fail);
            if(imgView != null){
                Glide.with(context).load(R.drawable.close_red).into(imgView);
            }
        }
        if(status == 2){
            editText.setBackgroundResource(R.drawable.edit_text_design_0_success);
            if(imgView != null){
                Glide.with(context).load(R.drawable.tick_green_light).into(imgView);
            }
        }
    }

    public static void changeTextViewValidation(TextView textView, boolean show, String error){
        textView.setText(error);
        if(show){
            textView.setVisibility(View.VISIBLE);
        }
        else{
            textView.setVisibility(View.GONE);
        }
    }
}
