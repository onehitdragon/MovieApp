package com.example.finalapp.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finalapp.R;
import com.example.finalapp.databinding.FragmentFeedBackBinding;
import com.example.finalapp.model.FeedBack;
import com.example.finalapp.myanimation.MyButton2Animation;
import com.example.finalapp.mydialog.MyDialog;
import com.example.finalapp.mydialog.MyDialogFactory;
import com.example.finalapp.myvalidation.MyValidation;
import com.example.finalapp.viewmodel.FeedBackViewModelFrag;
import com.example.finalapp.viewmodel.MovieWatchingViewModelFrag;

public class FeedBackFragment extends Fragment {
    private Context context;
    private FragmentFeedBackBinding binding;
    private FeedBackViewModelFrag feedBackViewModelFrag;
    private Button btnSendFeedBack;
    private MyButton2Animation myButton2Animation;
    private ImageView imgLoad;
    private EditText editTextContent;
    private TextView textViewContentValidation;
    private final Handler handler = new Handler();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_feed_back, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // find view
        btnSendFeedBack = view.findViewById(R.id.btnSendFeedBack);
        imgLoad = view.findViewById(R.id.imgLoad);
        editTextContent = view.findViewById(R.id.editTextContent);
        editTextContent.addTextChangedListener(new MyValidation.MyTextWatcher(context, editTextContent));
        textViewContentValidation = view.findViewById(R.id.textViewContentValidation);

        // init animation
        myButton2Animation = new MyButton2Animation(context, btnSendFeedBack, imgLoad);

        // init
        feedBackViewModelFrag = new ViewModelProvider(this).get(FeedBackViewModelFrag.class);
        MovieWatchingViewModelFrag movieWatchingViewModelFrag = new ViewModelProvider((MainActivity) context).get(MovieWatchingViewModelFrag.class);
        if(!movieWatchingViewModelFrag.getCurrentContentFeedBack().isEmpty()){
            String content = movieWatchingViewModelFrag.getCurrentContentFeedBack();
            feedBackViewModelFrag.setFeedBack(new FeedBack(content));
            movieWatchingViewModelFrag.setCurrentContentFeedBack("");
        }
        binding.setFeedBack(feedBackViewModelFrag.getFeedBack());
        feedBackViewModelFrag.getSendFeedBackSuccess().observe(getViewLifecycleOwner(), (Boolean sendFeedBackSuccess) -> {
            MyDialog myDialog;
            if(sendFeedBackSuccess){
                editTextContent.setText("");
                myDialog = new MyDialog(context);
                myDialog.setImageSrc(R.drawable.feedback2);
                myDialog.setTitle("Thành công");
                myDialog.setContent("Gửi phản hồi thành công");
            }
            else{
                myDialog = MyDialogFactory.createErrorServerDialog(context);
            }
            myDialog.show();
            myButton2Animation.toggleButtonAnimation();
        });

        // event
        btnSendFeedBack.setOnClickListener((View v) -> {
            myButton2Animation.toggleButtonAnimation();
            // validation
            new Thread(() -> {
                try{
                    Thread.sleep(1000);
                    FeedBack feedBack = feedBackViewModelFrag.getFeedBack();
                    if(!feedBack.getContent().isEmpty()){
                        changeBackgroundEditTextHandler(editTextContent, 2);
                        changeTextViewValidationHandler(textViewContentValidation, false, "");
                        feedBackViewModelFrag.sendFeedBack();
                    }
                    else{
                        changeBackgroundEditTextHandler(editTextContent, 1);
                        changeTextViewValidationHandler(textViewContentValidation, true, "Hãy nhập nội dung");
                        handler.post(() -> {
                            myButton2Animation.toggleButtonAnimation();
                        });
                    }
                }
                catch (Exception e){
                    Log.d("", e.toString());
                }
            }).start();
        });
    }

    private void changeBackgroundEditTextHandler(EditText editText, int status){
        handler.post(() -> {
            MyValidation.changeBackgroundEditText(context, editText, status);
        });
    }

    private void changeTextViewValidationHandler(TextView textView, boolean show, String error){
        handler.post(() -> {
            MyValidation.changeTextViewValidation(textView, show, error);
        });
    }
}