package com.example.finalapp.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.finalapp.model.User;
import com.example.finalapp.remoterepository.AccountCheckPojo;
import com.example.finalapp.remoterepository.AccountService;
import com.example.finalapp.remoterepository.RetrofitClient;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterViewModel extends ViewModel {
    private static final String TAG = "RegisterViewModel";
    private User user;
    private HashMap<String, String> validation;
    private MutableLiveData<AccountCheckPojo> accountCheckModel;
    private AccountService accountService;
    private MutableLiveData<Boolean> registerSuccess;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public HashMap<String, String> getValidation() {
        return validation;
    }

    public void setValidation(HashMap<String, String> validation) {
        this.validation = validation;
    }

    public MutableLiveData<AccountCheckPojo> getAccountCheckModel() {
        return accountCheckModel;
    }

    public void setAccountCheckModel(MutableLiveData<AccountCheckPojo> accountCheckModel) {
        this.accountCheckModel = accountCheckModel;
    }

    public MutableLiveData<Boolean> getRegisterSuccess() {
        return registerSuccess;
    }

    public void setRegisterSuccess(MutableLiveData<Boolean> registerSuccess) {
        this.registerSuccess = registerSuccess;
    }

    public RegisterViewModel(){
        Calendar birthDay = Calendar.getInstance();
        birthDay.set(2000, 0, 1);
        user = new User("", "", "", "", birthDay, true, "");
        validation = new HashMap<>();
        Retrofit retrofit = RetrofitClient.createRetrofit();
        accountService = retrofit.create(AccountService.class);
        accountCheckModel = new MutableLiveData<>();
        registerSuccess = new MutableLiveData<>();
    }

    public void validation(){
        // lastname validation
        Pattern pattern = Pattern.compile("^[\\w]{5,12}$");
        Matcher matcher = pattern.matcher(user.getLastName());
        if(!matcher.find()){
            validation.put("lastName", "T??? 5->12 k?? t???");
        }
        else{
            validation.remove("lastName");
        }

        // firstname validation
        matcher = pattern.matcher(user.getFirstName());
        if(!matcher.find()){
            validation.put("firstName", "T??? 5->12 k?? t???");
        }
        else{
            validation.remove("firstName");
        }

        // password validation
        pattern = Pattern.compile("^[a-zA-Z]*$");
        matcher = pattern.matcher(user.getPassword());
        if(matcher.find()){
            // only word
            validation.put("password", "Ph???i bao g???m s???");
        }
        else{
            pattern = Pattern.compile("^[0-9]*$");
            matcher = pattern.matcher(user.getPassword());
            if(matcher.find()){
                // only digit
                validation.put("password", "Ph???i bao g???m ch???");
            }
            else{
                if(user.getPassword().length() < 9 || user.getPassword().length() > 15){
                    validation.put("password", "T??? 9->15 k?? t??? (g???m ch??? v?? s???)");
                }
                else{
                    validation.remove("password");
                }
            }
        }

        // repeatPassword validation
        if(!user.getPassword().equals(user.getRepeatPassword()) || user.getRepeatPassword().isEmpty()){
            validation.put("repeatPassword", "M???t kh???u kh??ng kh???p");
        }
        else{
            validation.remove("repeatPassword");
        }

        // email validation
        pattern = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
        matcher = pattern.matcher(user.getEmail());
        if(matcher.find()){
            Call<AccountCheckPojo> call = accountService.check(user.getEmail(), "");
            call.enqueue(new Callback<AccountCheckPojo>() {
                @Override
                public void onResponse(Call<AccountCheckPojo> call, Response<AccountCheckPojo> response) {
                    Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()));
                    if(response.body().emailIsValid){
                        validation.put("email", "Email ???? t???n t???i");
                    }
                    else{
                        validation.remove("email");
                    }
                    accountCheckModel.setValue(response.body());
                }

                @Override
                public void onFailure(Call<AccountCheckPojo> call, Throwable t) {
                    accountCheckModel.setValue(null);
                }
            });
        }
        else{
            validation.put("email", "Email kh??ng h???p l???");
            accountCheckModel.setValue(new AccountCheckPojo());
        }
    }

    public void register(){
        if(validation.size() == 0){
            Calendar calendar = user.getBirthDay();
            String dateString = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DATE);
            Call<Void> call = accountService.register(user.getEmail(), user.getPassword(), user.getLastName(),
                    user.getFirstName(), dateString, user.isGender());
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    registerSuccess.setValue(true);
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    registerSuccess.setValue(false);
                }
            });
        }
    }
}
