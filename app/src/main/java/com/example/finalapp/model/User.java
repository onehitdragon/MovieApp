package com.example.finalapp.model;

import com.example.finalapp.remoterepository.UserPojo;

import java.util.Calendar;

public class User extends Account{
    private String lastName;
    private String firstName;
    private Calendar birthDay;
    private boolean gender;
    private String repeatPassword;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Calendar getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Calendar birthDay) {
        this.birthDay = birthDay;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public User(){}

    public User(String email, String password, String lastName, String firstName, Calendar birthDay, boolean gender, String repeatPassword) {
        super(email, password);
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthDay = birthDay;
        this.gender = gender;
        this.repeatPassword = repeatPassword;
    }

    public static User convertPojo(UserPojo userPojo){
        Calendar calendar = Calendar.getInstance();
        String[] birthDay = userPojo.birthDay.split("T")[0].split("-");
        int y = Integer.parseInt(birthDay[0]);
        int m = Integer.parseInt(birthDay[1]);
        int d = Integer.parseInt(birthDay[2]);
        calendar.set(y, m, d);
        return new User(userPojo.email, userPojo.password, userPojo.lastName, userPojo.firstName, calendar, userPojo.gender, "");
    }
}
