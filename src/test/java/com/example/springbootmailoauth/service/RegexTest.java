package com.example.springbootmailoauth.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class RegexTest {
    public static void main(String[] args) {
        String regex = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,16}$";
        String[] testPasswords = {"password1!", "pass!", "password1234567890!", "ValidPass123!"};
        for (String password : testPasswords) {
        if (password.matches(regex)) {
                System.out.println(password + " is valid");
            } else {
                System.out.println(password + " is invalid");
            }
        }
    }
}
