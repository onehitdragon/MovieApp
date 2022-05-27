package com.example.finalapp.myvalidation;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
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

    public static class MyTextWatcher implements TextWatcher {
        private Context context;
        private EditText editText;

        public MyTextWatcher(Context context, EditText editText) {
            this.context = context;
            this.editText = editText;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            MyValidation.changeBackgroundEditText(context, editText, 0);
        }
    }
}
