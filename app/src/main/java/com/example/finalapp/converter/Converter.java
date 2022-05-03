package com.example.finalapp.converter;

import androidx.databinding.InverseMethod;

public class Converter {
    public static String boolToString(boolean gender){
        if(gender) return "Nam";
        return "Nữ";
    }
    @InverseMethod("boolToString")
    public static boolean stringToBool(String gender){
        return gender.equals("Nam");
    }
}
