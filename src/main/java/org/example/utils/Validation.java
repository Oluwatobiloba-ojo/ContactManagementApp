package org.example.utils;

public class Validation {
    public static boolean validateEmail(String email){
        return email.matches("[a-zA-Z\\d+/_]+@[a-z]+[/.][a-z]{2,3}");
    }


    public static boolean validatePhoneNumber(String phoneNumber) {
        return phoneNumber.matches("[+1-9][0-9]{6,13}");
    }
    public static boolean validatePassword(String password) {
        return password.matches("[A-Z][a-zA-Z0-9/@ _-|?}{#$^><:()+*&%]{8,20}");
    }

}
