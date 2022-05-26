package com.example.finalapp.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finalapp.R;
import com.example.finalapp.databinding.FragmentPasswordChangeBinding;
import com.example.finalapp.myanimation.MyButton2Animation;
import com.example.finalapp.mydialog.MyDialog;
import com.example.finalapp.mydialog.MyDialogFactory;
import com.example.finalapp.myvalidation.MyValidation;
import com.example.finalapp.viewmodel.PasswordChangeViewModelFrag;

import java.util.HashMap;

public class PasswordChangeFragment extends Fragment {
    private Context context;
    private Button btnChangePassword;
    private ImageView imgLoad;
    private MyButton2Animation myButton2Animation;
    private PasswordChangeViewModelFrag passwordChangeViewModelFrag;
    private FragmentPasswordChangeBinding binding;
    private final Handler handler = new Handler();
    private EditText editTextOldPassword, editTextNewPassword, editTextRepeatNewPassword;
    private TextView textViewOldPasswordValidation, textViewNewPasswordValidation, textViewRepeatNewPasswordValidation;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_password_change, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // find view
        imgLoad = view.findViewById(R.id.imgLoad);
        btnChangePassword = view.findViewById(R.id.btnChangePassword);
        editTextOldPassword = view.findViewById(R.id.editTextOldPassword);
        editTextNewPassword = view.findViewById(R.id.editTextNewPassword);
        editTextRepeatNewPassword = view.findViewById(R.id.editTextRepeatNewPassword);
        editTextOldPassword.addTextChangedListener(new MyTextWatcher(editTextOldPassword));
        editTextNewPassword.addTextChangedListener(new MyTextWatcher(editTextNewPassword));
        editTextRepeatNewPassword.addTextChangedListener(new MyTextWatcher(editTextRepeatNewPassword));
        textViewOldPasswordValidation = view.findViewById(R.id.textViewOldPasswordValidation);
        textViewNewPasswordValidation = view.findViewById(R.id.textViewNewPasswordValidation);
        textViewRepeatNewPasswordValidation = view.findViewById(R.id.textViewRepeatNewPasswordValidation);

        // init animation
        myButton2Animation = new MyButton2Animation(context, btnChangePassword, imgLoad);

        // init
        passwordChangeViewModelFrag = new ViewModelProvider(this).get(PasswordChangeViewModelFrag.class);
        binding.setPasswordChangeAccount(passwordChangeViewModelFrag.getPasswordChangeAccount());
        passwordChangeViewModelFrag.getChangePasswordSuccess().observe(getViewLifecycleOwner(), (Boolean changePasswordSuccess) -> {
            MyDialog myDialog;
            if(changePasswordSuccess){
                myDialog = new MyDialog(context);
                myDialog.setImageSrc(R.drawable.exchange);
                myDialog.setTitle("Thành công");
                myDialog.setContent("Đổi mật khẩu thành công");
            }
            else{
                myDialog = MyDialogFactory.createErrorServerDialog(context);
            }
            myDialog.show();
            myButton2Animation.toggleButtonAnimation();
        });

        // event
        btnChangePassword.setOnClickListener((View v) -> {
            myButton2Animation.toggleButtonAnimation();
            passwordChangeViewModelFrag.validation();
            new Thread(() -> {
                try{
                    HashMap<String, String> validation = passwordChangeViewModelFrag.getValidation();
                    Thread.sleep(1000);
                    if(!validation.containsKey("oldPassword")){
                        changeBackgroundEditTextHandler(editTextOldPassword, 2);
                        changeTextViewValidationHandler(textViewOldPasswordValidation, false, "");
                    }
                    else{
                        changeBackgroundEditTextHandler(editTextOldPassword, 1);
                        changeTextViewValidationHandler(textViewOldPasswordValidation, true, validation.get("oldPassword"));
                    }
                    Thread.sleep(1000);
                    if(!validation.containsKey("newPassword")){
                        changeBackgroundEditTextHandler(editTextNewPassword, 2);
                        changeTextViewValidationHandler(textViewNewPasswordValidation, false, "");
                    }
                    else{
                        changeBackgroundEditTextHandler(editTextNewPassword, 1);
                        changeTextViewValidationHandler(textViewNewPasswordValidation, true, validation.get("newPassword"));
                    }
                    Thread.sleep(1000);
                    if(!validation.containsKey("repeatNewPassword")){
                        changeBackgroundEditTextHandler(editTextRepeatNewPassword, 2);
                        changeTextViewValidationHandler(textViewRepeatNewPasswordValidation, false, "");
                    }
                    else{
                        changeBackgroundEditTextHandler(editTextRepeatNewPassword, 1);
                        changeTextViewValidationHandler(textViewRepeatNewPasswordValidation, true, validation.get("repeatNewPassword"));
                    }
                    if(passwordChangeViewModelFrag.getValidation().isEmpty()){
                        passwordChangeViewModelFrag.changePassword();
                    }
                    else{
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

    private class MyTextWatcher implements TextWatcher {
        private EditText editText;

        public MyTextWatcher(EditText editText) {
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