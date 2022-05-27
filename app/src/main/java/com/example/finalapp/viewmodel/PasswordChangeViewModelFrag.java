package com.example.finalapp.viewmodel;

import android.app.Application;
import android.text.BoringLayout;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.finalapp.localrepository.AccountRepository;
import com.example.finalapp.model.Account;
import com.example.finalapp.model.PasswordChangeAccount;
import com.example.finalapp.myanimation.MyButton2Animation;
import com.example.finalapp.remoterepository.AccountCheckPojo;
import com.example.finalapp.remoterepository.AccountService;
import com.example.finalapp.remoterepository.RetrofitClient;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PasswordChangeViewModelFrag extends AndroidViewModel {
    private PasswordChangeAccount passwordChangeAccount;
    private AccountService accountService;
    private HashMap<String, String> validation;
    private MutableLiveData<Boolean> changePasswordSuccess;

    public PasswordChangeAccount getPasswordChangeAccount() {
        return passwordChangeAccount;
    }

    public void setPasswordChangeAccount(PasswordChangeAccount passwordChangeAccount) {
        this.passwordChangeAccount = passwordChangeAccount;
    }

    public HashMap<String, String> getValidation() {
        return validation;
    }

    public void setValidation(HashMap<String, String> validation) {
        this.validation = validation;
    }

    public MutableLiveData<Boolean> getChangePasswordSuccess() {
        return changePasswordSuccess;
    }

    public PasswordChangeViewModelFrag(@NonNull Application application){
        super(application);
        passwordChangeAccount = new PasswordChangeAccount("", "", "");
        validation = new HashMap<>();
        Retrofit retrofit = RetrofitClient.createRetrofit();
        accountService = retrofit.create(AccountService.class);
        changePasswordSuccess = new MutableLiveData<>();
    }

    public void validation(){
        // old password validation
        Account account = Account.getInstance();
        if(!passwordChangeAccount.getOldPassword().equals(account.getPassword())){
            validation.put("oldPassword", "Sai mật khẩu");
        }
        else{
            validation.remove("oldPassword");
        }

        // new password validation
        Pattern pattern = Pattern.compile("^[a-zA-Z]*$");
        Matcher matcher = pattern.matcher(passwordChangeAccount.getNewPassword());
        if(matcher.find()){
            // only word
            validation.put("newPassword", "Phải bao gồm số");
        }
        else{
            pattern = Pattern.compile("^[0-9]*$");
            matcher = pattern.matcher(passwordChangeAccount.getNewPassword());
            if(matcher.find()){
                // only digit
                validation.put("newPassword", "Phải bao gồm chữ");
            }
            else{
                if(passwordChangeAccount.getNewPassword().length() < 9 || passwordChangeAccount.getNewPassword().length() > 15){
                    validation.put("newPassword", "Từ 9->15 kí tự (gồm chữ và số)");
                }
                else{
                    validation.remove("newPassword");
                }
            }
        }

        // repeat new password validation
        if(!passwordChangeAccount.getNewPassword().equals(passwordChangeAccount.getRepeatNewPassword()) || passwordChangeAccount.getRepeatNewPassword().isEmpty()){
            validation.put("repeatNewPassword", "Mật khẩu không khớp");
        }
        else{
            validation.remove("repeatNewPassword");
        }
    }

    public void changePassword(){
        if(validation.isEmpty()){
            Account account = Account.getInstance();
            Call<Void> call = accountService.changePassword(account.getEmail(), passwordChangeAccount.getNewPassword());
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    changePasswordSuccess.setValue(true);
                    account.setPassword(passwordChangeAccount.getNewPassword());
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    changePasswordSuccess.setValue(false);
                }
            });
        }
    }
}
