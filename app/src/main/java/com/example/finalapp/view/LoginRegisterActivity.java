package com.example.finalapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.finalapp.R;
import com.example.finalapp.databinding.ActivityLoginRegisterBinding;
import com.example.finalapp.model.Account;
import com.example.finalapp.model.User;
import com.example.finalapp.myanimation.MyAnimation;
import com.example.finalapp.myanimation.MyButton2Animation;
import com.example.finalapp.myanimation.MyButtonAnimation;
import com.example.finalapp.mydialog.MyDialog;
import com.example.finalapp.mydialog.MyDialogFactory;
import com.example.finalapp.remoterepository.AccountCheckModel;
import com.example.finalapp.viewmodel.LoginViewModel;
import com.example.finalapp.viewmodel.RegisterViewModel;

import java.util.Calendar;
import java.util.HashMap;

public class LoginRegisterActivity extends AppCompatActivity {
    LinearLayout backgroundLayout, loginArea, registerArea;
    Button btnLogin, btnCloseLoginArea, btnLogin2, btnRegister, btnRegister2, btnCloseRegisterArea;
    EditText editTextUserName, editTextPassword, editTextBirthDayRegister, editTextGenderRegister,
            editTextEmailRegister, editTextLastNameRegister, editTextFirstNameRegister, editTextPasswordRegister,
            editTextRepeatPasswordRegister;
    TextView textViewEmailRegisterValidation, textViewLastNameRegisterValidation,
            textViewFirstNameRegisterValidation, textViewPasswordRegisterValidation, textViewRepeatPasswordRegisterValidation;
    ImageView imgLoad, imgLoadRegister;
    MyAnimation myButtonAnimation, myButton2Animation, myButtonRegisterAnimation, myButton2RegisterAnimation;
    DatePickerDialog datePickerDialogBirthDay;
    private LoginViewModel loginViewModel;
    private RegisterViewModel registerViewModel;
    private ActivityLoginRegisterBinding binding;
    private final Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_register);
        binding.setLifecycleOwner(this);

        // find view
        backgroundLayout = findViewById(R.id.backgroundLayout);
        loginArea = findViewById(R.id.loginArea);
        btnLogin = findViewById(R.id.btnLogin);
        btnCloseLoginArea = findViewById(R.id.btnCloseLoginArea);
        editTextUserName = findViewById(R.id.editTextUserName);
        editTextPassword = findViewById(R.id.editTextPassword);
        imgLoad = findViewById(R.id.imgLoad);
        btnLogin2 = findViewById(R.id.btnLogin2);
        editTextBirthDayRegister = findViewById(R.id.editTextBirthDayRegister);
        editTextGenderRegister = findViewById(R.id.editTextGenderRegister);
        btnRegister = findViewById(R.id.btnRegister);
        btnRegister2 = findViewById(R.id.btnRegister2);
        btnCloseRegisterArea = findViewById(R.id.btnCloseRegisterArea);
        registerArea = findViewById(R.id.registerArea);
        imgLoadRegister = findViewById(R.id.imgLoadRegister);
        editTextEmailRegister = findViewById(R.id.editTextEmailRegister);
        editTextLastNameRegister = findViewById(R.id.editTextLastNameRegister);
        editTextFirstNameRegister = findViewById(R.id.editTextFirstNameRegister);
        editTextPasswordRegister = findViewById(R.id.editTextPasswordRegister);
        editTextRepeatPasswordRegister = findViewById(R.id.editTextRepeatPasswordRegister);
        textViewEmailRegisterValidation = findViewById(R.id.textViewEmailRegisterValidation);
        textViewLastNameRegisterValidation = findViewById(R.id.textViewLastNameRegisterValidation);
        textViewFirstNameRegisterValidation = findViewById(R.id.textViewFirstNameRegisterValidation);
        textViewPasswordRegisterValidation = findViewById(R.id.textViewPasswordRegisterValidation);
        textViewRepeatPasswordRegisterValidation = findViewById(R.id.textViewRepeatPasswordRegisterValidation);

        // init animation
        myButtonAnimation = new MyButtonAnimation(this, btnLogin, loginArea);
        myButton2Animation = new MyButton2Animation(this, btnLogin2, imgLoad);
        datePickerDialogBirthDay = new DatePickerDialog(LoginRegisterActivity.this, (DatePicker datePicker, int y, int m, int d) -> {
            Calendar calendar = Calendar.getInstance();
            calendar.set(y, m, d);
            String dateString = calendar.get(Calendar.DATE) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR);
            editTextBirthDayRegister.setText(dateString);
            registerViewModel.getUser().setBirthDay(calendar);
        }, 2000, 0, 1);
        myButtonRegisterAnimation = new MyButtonAnimation(this, btnRegister, registerArea);
        myButton2RegisterAnimation = new MyButton2Animation(this, btnRegister2, imgLoadRegister);

        // init view model
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        binding.setLoginViewModel(loginViewModel);
        loginViewModel.getAccountCheckModel().observe(this, (AccountCheckModel accountCheckModel) -> {
            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                    if(accountCheckModel == null){
                        handler.post(() -> {
                            myButton2Animation.toggleButtonAnimation();
                            MyDialog myDialog = MyDialogFactory.createErrorServerDialog(this);
                            myDialog.show();
                        });
                        return;
                    }
                    if(accountCheckModel.emailIsValid){
                        changeBackgroundEditTextHandler(editTextUserName, 2);
                    }
                    else{
                        changeBackgroundEditTextHandler(editTextUserName, 1);
                    }
                    Thread.sleep(500);
                    if(accountCheckModel.passwordIsValid){
                        changeBackgroundEditTextHandler(editTextPassword, 2);
                    }
                    else{
                        changeBackgroundEditTextHandler(editTextPassword, 1);
                    }
                    handler.post(() -> {
                        myButton2Animation.toggleButtonAnimation();
                        if(accountCheckModel.emailIsValid && accountCheckModel.passwordIsValid){
                            MyDialog myDialog = new MyDialog(this);
                            myDialog.setImageSrc(R.drawable.popcorn);
                            myDialog.setTitle("Thành công");
                            myDialog.setContent("Đăng nhập thành công");
                            myDialog.show();
                        }
                    });
                }
                catch (Exception e){
                    Log.d("", e.toString());
                }
            }).start();
        });
        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        binding.setRegisterViewModel(registerViewModel);
        registerViewModel.getAccountCheckModel().observe(this, (AccountCheckModel accountCheckModel) -> {
            HashMap<String, String> validation = registerViewModel.getValidation();
            new Thread(() -> {
                try{
                    Thread.sleep(1000);
                    if(accountCheckModel == null){
                        handler.post(() -> {
                            myButton2RegisterAnimation.toggleButtonAnimation();
                            MyDialog myDialog = MyDialogFactory.createErrorServerDialog(this);
                            myDialog.show();
                        });
                        return;
                    }
                    if(!validation.containsKey("email")){
                        changeBackgroundEditTextHandler(editTextEmailRegister, 2);
                        changeTextViewValidationHandler(textViewEmailRegisterValidation, false, "");
                    }
                    else{
                        changeBackgroundEditTextHandler(editTextEmailRegister, 1);
                        changeTextViewValidationHandler(textViewEmailRegisterValidation, true, validation.get("email"));
                    }
                    Thread.sleep(1000);
                    if(!validation.containsKey("lastName")){
                        changeBackgroundEditTextHandler(editTextLastNameRegister, 2);
                        changeTextViewValidationHandler(textViewLastNameRegisterValidation, false, "");
                    }
                    else{
                        changeBackgroundEditTextHandler(editTextLastNameRegister, 1);
                        changeTextViewValidationHandler(textViewLastNameRegisterValidation, true, validation.get("lastName"));
                    }
                    Thread.sleep(1000);
                    if(!validation.containsKey("firstName")){
                        changeBackgroundEditTextHandler(editTextFirstNameRegister, 2);
                        changeTextViewValidationHandler(textViewFirstNameRegisterValidation, false, "");
                    }
                    else{
                        changeBackgroundEditTextHandler(editTextFirstNameRegister, 1);
                        changeTextViewValidationHandler(textViewFirstNameRegisterValidation, true, validation.get("firstName"));
                    }
                    Thread.sleep(500);
                    changeBackgroundEditTextHandler(editTextBirthDayRegister, 2);
                    Thread.sleep(500);
                    changeBackgroundEditTextHandler(editTextGenderRegister, 2);
                    Thread.sleep(1000);
                    if(!validation.containsKey("password")){
                        changeBackgroundEditTextHandler(editTextPasswordRegister, 2);
                        changeTextViewValidationHandler(textViewPasswordRegisterValidation, false, "");
                    }
                    else{
                        changeBackgroundEditTextHandler(editTextPasswordRegister, 1);
                        changeTextViewValidationHandler(textViewPasswordRegisterValidation, true, validation.get("password"));
                    }
                    Thread.sleep(1000);
                    if(!validation.containsKey("repeatPassword")){
                        changeBackgroundEditTextHandler(editTextRepeatPasswordRegister, 2);
                        changeTextViewValidationHandler(textViewRepeatPasswordRegisterValidation, false, "");
                    }
                    else{
                        changeBackgroundEditTextHandler(editTextRepeatPasswordRegister, 1);
                        changeTextViewValidationHandler(textViewRepeatPasswordRegisterValidation, true, validation.get("repeatPassword"));
                    }
                    registerViewModel.register();
                    handler.post(() -> {
                        myButton2RegisterAnimation.toggleButtonAnimation();
                    });
                }
                catch (Exception e){
                    Log.d("", e.toString());
                }
            }).start();
        });
        registerViewModel.getRegisterSuccess().observe(this, (Boolean registerSuccess) -> {
            MyDialog myDialog = new MyDialog(this);
            if(registerSuccess){
                myDialog.setImageSrc(R.drawable.registered);
                myDialog.setTitle("Thành công");
                myDialog.setContent("Đăng ký thành công");
            }
            else{
                myDialog = MyDialogFactory.createErrorServerDialog(this);
            }
            myDialog.show();
        });

        // login
        btnLogin.setOnClickListener((View view) -> {
            myButtonAnimation.toggleButtonAnimation();
        });
        btnCloseLoginArea.setOnClickListener((View view) -> {
            myButtonAnimation.toggleButtonAnimation();
        });
        editTextUserName.addTextChangedListener(new MyTextWatcher(editTextUserName));
        editTextPassword.addTextChangedListener(new MyTextWatcher(editTextPassword));
        btnLogin2.setOnClickListener((View view) -> {
            myButton2Animation.toggleButtonAnimation();
            loginViewModel.checkAccount();
        });

        // register
        editTextBirthDayRegister.setOnClickListener((View view) -> {
            datePickerDialogBirthDay.show();
        });
        editTextGenderRegister.setOnClickListener((View view) -> {
            if(editTextGenderRegister.getText().toString().equals("Nam")){
                editTextGenderRegister.setText("Nữ");
            }
            else{
                editTextGenderRegister.setText("Nam");
            }
        });
        btnRegister.setOnClickListener((View view) -> {
            myButtonRegisterAnimation.toggleButtonAnimation();
        });
        btnCloseRegisterArea.setOnClickListener((View view) -> {
            myButtonRegisterAnimation.toggleButtonAnimation();
        });
        btnRegister2.setOnClickListener((View view) -> {
            myButton2RegisterAnimation.toggleButtonAnimation();
            registerViewModel.validation();
        });
        editTextEmailRegister.addTextChangedListener(new MyTextWatcher(editTextEmailRegister));
        editTextLastNameRegister.addTextChangedListener(new MyTextWatcher(editTextLastNameRegister));
        editTextFirstNameRegister.addTextChangedListener(new MyTextWatcher(editTextFirstNameRegister));
        editTextPasswordRegister.addTextChangedListener(new MyTextWatcher(editTextPasswordRegister));
        editTextRepeatPasswordRegister.addTextChangedListener(new MyTextWatcher(editTextRepeatPasswordRegister));
        editTextGenderRegister.addTextChangedListener(new MyTextWatcher(editTextGenderRegister));
    }
//    private void createBackgroundAnimation(){
//        AnimationDrawable backgroundAnimation = (AnimationDrawable) backgroundLayout.getBackground();
//        backgroundAnimation.setEnterFadeDuration(1000);
//        backgroundAnimation.setExitFadeDuration(1000);
//        backgroundAnimation.start();
//    }
    private void changeBackgroundEditTextHandler(EditText editText, int status){
        handler.post(() -> {
            changeBackgroundEditText(editText, status);
        });
    }

    private void changeBackgroundEditText(EditText editText, int status){
        ImageView imgView = null;
        ViewGroup viewGroup = (ViewGroup) editText.getParent();
        if(viewGroup.getChildAt(1) != null){
            imgView = (ImageView) viewGroup.getChildAt(1);
        }
        if(status == 0){
            editText.setBackgroundResource(R.drawable.edit_text_design_0);
            if(imgView != null){
                Glide.with(this).clear(imgView);
            }
        }
        if(status == 1){
            editText.setBackgroundResource(R.drawable.edit_text_design_0_fail);
            if(imgView != null){
                Glide.with(this).load(R.drawable.close_red).into(imgView);
            }
        }
        if(status == 2){
            editText.setBackgroundResource(R.drawable.edit_text_design_0_success);
            if(imgView != null){
                Glide.with(this).load(R.drawable.tick_green_light).into(imgView);
            }
        }
    }

    private void changeTextViewValidationHandler(TextView textView, boolean show, String error){
        handler.post(() -> {
            changeTextViewValidation(textView, show, error);
        });
    }

    private void changeTextViewValidation(TextView textView, boolean show, String error){
        textView.setText(error);
        if(show){
            textView.setVisibility(View.VISIBLE);
        }
        else{
            textView.setVisibility(View.GONE);
        }
    }

    private class MyTextWatcher implements TextWatcher{
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
            changeBackgroundEditText(editText, 0);
        }
    }
}